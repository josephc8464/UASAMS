package org.team2.UASAMS.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.team2.UASAMS.Entities.Scholarship;
import org.team2.UASAMS.Entities.Enums.ScholarshipStatus;
import org.team2.UASAMS.Repos.ScholarshipRepository;

import jakarta.transaction.Transactional;

/**
 * Service layer for creating and managing {@link Scholarship} opportunities within the UASAMS system.
 *
 * @author Team 2
 * @since 11/25/26
 * @see Scholarship
 * @see ScholarshipRepository
 */
@Service
public class ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;

    /**
     * Constructs a {@code ScholarshipService} with the required scholarship repository.
     *
     * @param scholarshipRepository repository for persisting {@link Scholarship} records
     */
    public ScholarshipService(ScholarshipRepository scholarshipRepository) {
        this.scholarshipRepository = scholarshipRepository;
    }

    /**
     * Returns all scholarships with status "OPEN"
     * 
     * @return List of {@link Scholarship} entities
     */

    public List <Scholarship> getAllScholarships() {
        return scholarshipRepository.findAll();
    }
    public List<Scholarship> getOpenScholarships() {
        return scholarshipRepository.findByStatus(ScholarshipStatus.OPEN);
    }

    public Scholarship getScholarshipById(Long id) {
        return scholarshipRepository.findById(id)
            .orElseThrow(() ->
                new NoSuchElementException("Scholarship not found")
    );
    }

    /**
     * Validates and creates a new {@link Scholarship} listing.
     *
     * @param name        title of the scholarship
     * @param description detailed overview and eligibility requirements
     * @param awardAmount monetary value of the award (must be positive)
     * @param deadline    submission deadline timestamp (must be in the future)
     * @param status      initial lifecycle {@link ScholarshipStatus} (defaults to {@link ScholarshipStatus#DRAFT} if null)
     * @return the persisted {@link Scholarship} entity
     * @throws IllegalArgumentException if required fields are blank, award amount is non-positive, or deadline is in the past
     */
    @Transactional
    public Scholarship createScholarship(
            String name,
            String description,
            float awardAmount,
            LocalDateTime deadline,
            ScholarshipStatus status) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Scholarship name is required");
        }
        if (awardAmount <= 0) {
            throw new IllegalArgumentException("Award amount must be greater than zero");
        }
        if (deadline == null || deadline.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Scholarship deadline must be in the future");
        }

        Scholarship scholarship = new Scholarship();
        scholarship.setName(name.trim());
        scholarship.setDescription(description != null ? description.trim() : "");
        scholarship.setAwardAmount(awardAmount);
        scholarship.setDeadline(deadline);
        scholarship.setStatus(status != null ? status : ScholarshipStatus.DRAFT);

        return scholarshipRepository.save(scholarship);
    }
}

