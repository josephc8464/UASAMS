package org.team2.UASAMS.Repos;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team2.UASAMS.Entities.*;
import org.team2.UASAMS.Entities.Enums.ApplicationStatus;

/**
 * Data access repository for managing {@link Application} records.
 * <p>
 * Provides lookup queries for filtering student applications by applicant, scholarship,
 * submission timestamp, and {@link ApplicationStatus}.
 * </p>
 *
 * @author Team 2
 * @since 11/25/26
 * @see Application
 */
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findBySubmissionDate(LocalDateTime submissionDate);
    List<Application> findByStudentUser(StudentUser studentUser);
    List<Application> findByDocuments(List<ApplicationDocument> documents);
    List<Application> findByAnswers(List<ApplicationAnswer> answers);
    List<Application> findByScholarship(Scholarship scholarship);
    List<Application> findByReviews(List<ApplicationReview> reviews);
    List<Application> findByStatus(ApplicationStatus status);
}