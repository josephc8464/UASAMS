package org.team2.UASAMS.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.team2.UASAMS.Entities.Scholarship;
import org.team2.UASAMS.Entities.Enums.Major;
import org.team2.UASAMS.Entities.Enums.ScholarshipStatus;
import org.team2.UASAMS.Repos.ScholarshipRepository;

import jakarta.transaction.Transactional;

/**
 * Service layer for creating, updating, querying, publishing, closing, and archiving
 * {@link Scholarship} opportunities within the UASAMS system.
 *
 * @author Team 2
 * @since 11/25/26
 * @see Scholarship
 * @see ScholarshipRepository
 */
@Service
public class ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;

    @Transactional
    @Scheduled(fixedRate = 60000) // Runs every 60 seconds (adjust as needed)
    public void closeExpiredScholarships() {
        LocalDateTime now = LocalDateTime.now();
        List<Scholarship> expired = scholarshipRepository.findByStatusAndDeadlineBefore(ScholarshipStatus.OPEN, now);

        for (Scholarship scholarship : expired) {
            scholarship.setStatus(ScholarshipStatus.CLOSED);
        }

        if (!expired.isEmpty()) {
            scholarshipRepository.saveAll(expired);
        }
    }
    /**
     * Constructs a {@code ScholarshipService} with the required scholarship repository.
     *
     * @param scholarshipRepository repository for querying and persisting {@link Scholarship} records
     */
    public ScholarshipService(ScholarshipRepository scholarshipRepository) {
        this.scholarshipRepository = scholarshipRepository;
    }

    public Scholarship getScholarshipById(Long id) {
        return scholarshipRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Scholarship not found with id: " + id));
    }
    /**
     * Retrieves all scholarships currently marked with {@link ScholarshipStatus#OPEN}.
     *
     * @return a {@link List} of open {@link Scholarship} entities
     */
    public List<Scholarship> getOpenScholarships() {
        return scholarshipRepository.findByStatus(ScholarshipStatus.OPEN);
    }

    /**
     * Retrieves all scholarships regardless of status.
     *
     * @return a {@link List} of all {@link Scholarship} entities
     */
    public List<Scholarship> getAllScholarships() {
        return scholarshipRepository.findAll();
    }

    /**
     * Validates input constraints, normalizes fields, and persists a new {@link Scholarship} listing.
     *
     * @param name                     unique title of the scholarship (required, non-blank)
     * @param description              detailed overview and eligibility requirements (defaults to empty string if {@code null})
     * @param providerContact          provider contact information such as email or phone (trimmed, or {@code null} if blank)
     * @param eligibleMajors           set of major names or display titles mapped via {@link Major#fromStrings}; unrecognized values are ignored
     * @param awardAmount              monetary value of the award (must be greater than zero)
     * @param deadline                 submission deadline timestamp (required, must be in the future)
     * @param disbursementInstructions instructions describing how funds are disbursed and handled (trimmed, or {@code null} if blank)
     * @param status                   initial lifecycle {@link ScholarshipStatus} (defaults to {@link ScholarshipStatus#DRAFT} if {@code null})
     * @return the persisted {@link Scholarship} entity
     * @throws IllegalArgumentException if {@code name} is {@code null}/blank or already exists,
     *                                  {@code awardAmount} is less than or equal to zero,
     *                                  or {@code deadline} is {@code null} or in the past
     */
    @Transactional
    public Scholarship createScholarship(
            String name,
            String description,
            String providerContact,
            Set<String> eligibleMajors,
            float awardAmount,
            LocalDateTime deadline,
            String disbursementInstructions,
            ScholarshipStatus status) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Scholarship name is required");
        }
        String trimmedName = name.trim();
        if (scholarshipRepository.findByName(trimmedName) != null) {
            throw new IllegalArgumentException("Scholarship name is already taken");
        }
        if (awardAmount <= 0) {
            throw new IllegalArgumentException("Award amount must be greater than zero");
        }
        if (deadline == null || deadline.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Scholarship deadline must be in the future");
        }

        Scholarship scholarship = new Scholarship();
        scholarship.setName(trimmedName);
        scholarship.setDescription(description != null ? description.trim() : "");
        scholarship.setAwardAmount(awardAmount);
        scholarship.setDeadline(deadline);
        scholarship.setStatus(status != null ? status : ScholarshipStatus.DRAFT);

        scholarship.setProviderContact(providerContact != null && !providerContact.isBlank() ? providerContact.trim() : null);
        scholarship.setEligibleMajors(Major.fromStrings(eligibleMajors));
        scholarship.setDisbursementInstructions(disbursementInstructions != null && !disbursementInstructions.isBlank() ? disbursementInstructions.trim() : null);

        return scholarshipRepository.save(scholarship);
    }

    /**
     * Updates an existing {@link Scholarship} record identified by {@code scholarshipId}.
     * <p>
     * Core fields ({@code name}, {@code awardAmount}, {@code deadline}, {@code status}) retain their
     * existing values when passed {@code null} (or blank for {@code name}). Optional/descriptive fields
     * ({@code description}, {@code providerContact}, {@code eligibleMajors}, {@code disbursementInstructions})
     * overwrite existing values with normalized empty or {@code null} defaults when omitted.
     * </p>
     *
     * @param scholarshipId            unique identifier of the scholarship to update
     * @param name                     updated scholarship title (retains existing value if {@code null} or blank)
     * @param description              updated description (trimmed, or set to {@code ""} if {@code null})
     * @param awardAmount              updated award amount (must be positive if non-null; retains existing value if {@code null})
     * @param deadline                 updated submission deadline (must be in the future if non-null; retains existing value if {@code null})
     * @param providerContact          updated provider contact details (trimmed, or set to {@code null} if {@code null}/blank)
     * @param eligibleMajors           updated set of major names or display titles mapped via {@link Major#fromStrings}; unrecognized values are ignored
     * @param disbursementInstructions updated disbursement instructions (trimmed, or set to {@code null} if {@code null}/blank)
     * @return the updated and persisted {@link Scholarship} entity
     * @throws IllegalArgumentException if no scholarship exists with {@code scholarshipId},
     *                                  {@code name} conflicts with another scholarship,
     *                                  {@code awardAmount} is less than or equal to zero,
     *                                  or {@code deadline} is in the past
     */
    @Transactional
    public Scholarship updateScholarship(
            Long scholarshipId,
            String name,
            String description,
            Float awardAmount,
            LocalDateTime deadline,
            String providerContact,
            Set<String> eligibleMajors,
            String disbursementInstructions) {

        Scholarship scholarship = scholarshipRepository.findById(scholarshipId)
                .orElseThrow(() -> new IllegalArgumentException("Scholarship not found with id: " + scholarshipId));

        if (name != null && !name.isBlank()) {
            String trimmedName = name.trim();
            Scholarship existingWithName = scholarshipRepository.findByName(trimmedName);
            if (existingWithName != null && !existingWithName.getId().equals(scholarshipId)) {
                throw new IllegalArgumentException("Scholarship name is already taken");
            }
            scholarship.setName(trimmedName);
        }
        if (awardAmount != null) {
            if (awardAmount <= 0) {
                throw new IllegalArgumentException("Award amount must be greater than zero");
            }
            scholarship.setAwardAmount(awardAmount);
        }
        if (deadline != null) {
            if (deadline.isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("Scholarship deadline must be in the future");
            }
            scholarship.setDeadline(deadline);
        }

        scholarship.setDescription(description != null ? description.trim() : "");
        scholarship.setProviderContact(providerContact != null && !providerContact.isBlank() ? providerContact.trim() : null);
        scholarship.setEligibleMajors(Major.fromStrings(eligibleMajors));
        scholarship.setDisbursementInstructions(disbursementInstructions != null && !disbursementInstructions.isBlank() ? disbursementInstructions.trim() : null);

        return scholarshipRepository.save(scholarship);
    }

    /**
     * Validates that a {@link Scholarship} in {@link ScholarshipStatus#DRAFT} status satisfies all
     * completeness requirements and transitions its status to {@link ScholarshipStatus#OPEN}.
     * <p>
     * To be published, the scholarship must have a non-blank name, description, provider contact,
     * and disbursement instructions; a positive award amount; a future deadline; and at least
     * one eligible major.
     * </p>
     *
     * @param scholarshipId unique identifier of the scholarship to publish
     * @return the published and persisted {@link Scholarship} entity with status {@link ScholarshipStatus#OPEN}
     * @throws IllegalArgumentException if no scholarship exists with {@code scholarshipId} or if any
     *                                  required publication field is missing, invalid, or expired
     * @throws IllegalStateException    if the scholarship is not currently in {@link ScholarshipStatus#DRAFT} status
     */
    @Transactional
    public Scholarship publishScholarship(Long scholarshipId) {
        Scholarship scholarship = scholarshipRepository.findById(scholarshipId)
                .orElseThrow(() -> new IllegalArgumentException("Scholarship not found with id: " + scholarshipId));

        if (scholarship.getStatus() != ScholarshipStatus.DRAFT) {
            throw new IllegalStateException("Cannot publish a scholarship that is not in draft status");
        }
        if (scholarship.getName() == null || scholarship.getName().isBlank()) {
            throw new IllegalArgumentException("Cannot publish a scholarship with no name");
        }
        if (scholarship.getDescription() == null || scholarship.getDescription().isBlank()) {
            throw new IllegalArgumentException("Cannot publish a scholarship with no description");
        }
        if (scholarship.getAwardAmount() <= 0.0f) {
            throw new IllegalArgumentException("Cannot publish a scholarship without a positive award amount");
        }
        if (scholarship.getDeadline() == null || scholarship.getDeadline().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot publish a scholarship without a future deadline");
        }
        if (scholarship.getProviderContact() == null || scholarship.getProviderContact().isBlank()) {
            throw new IllegalArgumentException("Cannot publish a scholarship with no provider contact");
        }
        if (scholarship.getEligibleMajors() == null || scholarship.getEligibleMajors().isEmpty()) {
            throw new IllegalArgumentException("Cannot publish a scholarship without at least one eligible major");
        }
        if (scholarship.getDisbursementInstructions() == null || scholarship.getDisbursementInstructions().isBlank()) {
            throw new IllegalArgumentException("Cannot publish a scholarship with no disbursement instructions");
        }
        if (scholarship.getQuestions() == null || scholarship.getQuestions().isEmpty()) {
            throw new IllegalArgumentException("Cannot publish a scholarship with no questions.");
        }

        scholarship.setStatus(ScholarshipStatus.OPEN);
        return scholarshipRepository.save(scholarship);
    }

    /**
     * Closes an open {@link Scholarship} listing by transitioning its status to
     * {@link ScholarshipStatus#CLOSED}.
     *
     * @param scholarshipId unique identifier of the scholarship to close
     * @return the updated and persisted {@link Scholarship} entity with status {@link ScholarshipStatus#CLOSED}
     * @throws IllegalArgumentException if no scholarship exists with {@code scholarshipId}
     * @throws IllegalStateException    if the scholarship is not currently {@link ScholarshipStatus#OPEN}
     */
    @Transactional
    public Scholarship closeScholarship(Long scholarshipId) {
        Scholarship scholarship = scholarshipRepository.findById(scholarshipId)
                .orElseThrow(() -> new IllegalArgumentException("Scholarship not found with id: " + scholarshipId));

        if (scholarship.getStatus() != ScholarshipStatus.OPEN) {
            throw new IllegalStateException("Cannot close a scholarship that is not open");
        }

        scholarship.setStatus(ScholarshipStatus.CLOSED);
        return scholarshipRepository.save(scholarship);
    }

    /**
     * Archives a closed {@link Scholarship} listing by transitioning its status to
     * {@link ScholarshipStatus#ARCHIVED}.
     *
     * @param scholarshipId unique identifier of the scholarship to archive
     * @return the archived and persisted {@link Scholarship} entity with status {@link ScholarshipStatus#ARCHIVED}
     * @throws IllegalArgumentException if no scholarship exists with {@code scholarshipId}
     * @throws IllegalStateException    if the scholarship is not currently {@link ScholarshipStatus#CLOSED}
     */
    @Transactional
    public Scholarship archiveScholarship(Long scholarshipId) {
        Scholarship scholarship = scholarshipRepository.findById(scholarshipId)
                .orElseThrow(() -> new IllegalArgumentException("Scholarship not found with id: " + scholarshipId));

        if (scholarship.getStatus() != ScholarshipStatus.CLOSED) {
            throw new IllegalStateException("Cannot archive a scholarship that is not closed");
        }

        scholarship.setStatus(ScholarshipStatus.ARCHIVED);
        return scholarshipRepository.save(scholarship);
    }
}