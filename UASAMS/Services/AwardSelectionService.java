package org.team2.UASAMS.Services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.team2.UASAMS.Entities.Application;
import org.team2.UASAMS.Entities.AwardSelection;
import org.team2.UASAMS.Entities.Scholarship;
import org.team2.UASAMS.Entities.User;
import org.team2.UASAMS.Entities.Enums.ApplicationStatus;
import org.team2.UASAMS.Entities.Enums.AwardSelectionStatus;
import org.team2.UASAMS.Repos.AwardSelectionRepository;

import jakarta.transaction.Transactional;

/**
 * Service layer for recording and managing {@link AwardSelection} decisions
 * within the UASAMS system.
 *
 * @author Team 2
 * @since 11/25/26
 * @see AwardSelection
 * @see AwardSelectionRepository
 */
@Service
public class AwardSelectionService {

    private final AwardSelectionRepository awardSelectionRepository;

    /**
     * Constructs an {@code AwardSelectionService} with the required award selection repository.
     *
     * @param awardSelectionRepository repository for persisting {@link AwardSelection} records
     */
    public AwardSelectionService(AwardSelectionRepository awardSelectionRepository) {
        this.awardSelectionRepository = awardSelectionRepository;
    }

    /**
     * Validates and creates an {@link AwardSelection} granting a scholarship award to a submitted application.
     *
     * @param scholarship         the {@link Scholarship} being awarded
     * @param application         the winning {@link Application}
     * @param designatedPanelUser the committee or panel {@link User} recording the award decision
     * @param status              initial {@link AwardSelectionStatus} (defaults to {@link AwardSelectionStatus#SELECTED} if null)
     * @return the persisted {@link AwardSelection} entity
     * @throws IllegalArgumentException if required references are null or the application belongs to a different scholarship
     * @throws IllegalStateException    if the application has not been submitted or already has an award selection
     */
    @Transactional
    public AwardSelection createAwardSelection(
            Scholarship scholarship,
            Application application,
            User designatedPanelUser,
            AwardSelectionStatus status) {

        if (scholarship == null || application == null || designatedPanelUser == null) {
            throw new IllegalArgumentException("Scholarship, Application, and designated panel user must not be null");
        }
        if (application.getStatus() != ApplicationStatus.SUBMITTED) {
            throw new IllegalStateException("Cannot award an application that has not been submitted");
        }
        if (!application.getScholarship().getId().equals(scholarship.getId())) {
            throw new IllegalArgumentException("Application does not belong to the specified scholarship");
        }
        if (!awardSelectionRepository.findByApplication(application).isEmpty()) {
            throw new IllegalStateException("An award selection already exists for this application");
        }

        AwardSelection selection = new AwardSelection();
        selection.setScholarship(scholarship);
        selection.setApplication(application);
        selection.setDesignatedPanelUser(designatedPanelUser);
        selection.setDecisionDate(LocalDateTime.now());
        selection.setStatus(status != null ? status : AwardSelectionStatus.SELECTED);

        return awardSelectionRepository.save(selection);
    }
}