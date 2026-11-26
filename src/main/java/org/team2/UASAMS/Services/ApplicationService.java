package org.team2.UASAMS.Services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.team2.UASAMS.Entities.Application;
import org.team2.UASAMS.Entities.Scholarship;
import org.team2.UASAMS.Entities.StudentUser;
import org.team2.UASAMS.Entities.Enums.ApplicationStatus;
import org.team2.UASAMS.Entities.Enums.ScholarshipStatus;
import org.team2.UASAMS.Repos.ApplicationRepository;

import jakarta.transaction.Transactional;

/**
 * Service layer for initiating and managing student {@link Application} records
 * within the UASAMS system.
 *
 * @author Team 2
 * @since 11/25/26
 * @see Application
 * @see ApplicationRepository
 */
@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    /**
     * Constructs an {@code ApplicationService} with the required application repository.
     *
     * @param applicationRepository repository for persisting {@link Application} records
     */
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    /**
     * Creates a new draft {@link Application} for a student applying to an open scholarship.
     *
     * @param studentUser the {@link StudentUser} starting the application
     * @param scholarship the target {@link Scholarship} being applied to
     * @return the newly created draft {@link Application}
     * @throws IllegalArgumentException if student or scholarship is null
     * @throws IllegalStateException    if the scholarship is not open, its deadline has passed,
     *                                  or the student already has an application for this scholarship
     */
    @Transactional
    public Application createApplication(StudentUser studentUser, Scholarship scholarship) {
        if (studentUser == null || scholarship == null) {
            throw new IllegalArgumentException("StudentUser and Scholarship must not be null");
        }
        if (scholarship.getStatus() != ScholarshipStatus.OPEN) {
            throw new IllegalStateException("Cannot apply to a scholarship that is not OPEN");
        }
        if (scholarship.getDeadline() != null && scholarship.getDeadline().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("The application deadline for this scholarship has passed");
        }

        boolean alreadyApplied = applicationRepository.findByStudentUser(studentUser).stream()
                .anyMatch(existing -> existing.getScholarship().getId().equals(scholarship.getId()));
        if (alreadyApplied) {
            throw new IllegalStateException("Student has already started or submitted an application for this scholarship");
        }

        Application application = new Application();
        application.setStudentUser(studentUser);
        application.setScholarship(scholarship);
        application.setStatus(ApplicationStatus.DRAFT);

        return applicationRepository.save(application);
    }
}