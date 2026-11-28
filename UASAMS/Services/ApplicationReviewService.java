package org.team2.UASAMS.Services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.team2.UASAMS.Entities.Application;
import org.team2.UASAMS.Entities.ApplicationReview;
import org.team2.UASAMS.Entities.User;
import org.team2.UASAMS.Entities.Enums.ApplicationStatus;
import org.team2.UASAMS.Repos.ApplicationReviewRepository;

import jakarta.transaction.Transactional;

/**
 * Service layer for creating and managing committee {@link ApplicationReview} evaluations
 * within the UASAMS system.
 *
 * @author Team 2
 * @since 11/25/26
 * @see ApplicationReview
 * @see ApplicationReviewRepository
 */
@Service
public class ApplicationReviewService {

    private final ApplicationReviewRepository reviewRepository;

    /**
     * Constructs an {@code ApplicationReviewService} with the required review repository.
     *
     * @param reviewRepository repository for persisting {@link ApplicationReview} records
     */
    public ApplicationReviewService(ApplicationReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * Validates and records a committee member's evaluation for a submitted {@link Application}.
     *
     * @param rating      numeric evaluation score (e.g., 1 to 10)
     * @param comments    qualitative feedback from the reviewer
     * @param application the submitted {@link Application} being reviewed
     * @param reviewer    the {@link User} conducting the review
     * @return the persisted {@link ApplicationReview} entity
     * @throws IllegalArgumentException if application or reviewer is null, or rating is out of range
     * @throws IllegalStateException    if the application is still in {@link ApplicationStatus#DRAFT}
     *                                  or the reviewer has already reviewed this application
     */
    @Transactional
    public ApplicationReview createApplicationReview(
            int rating,
            String comments,
            Application application,
            User reviewer) {

        if (application == null || reviewer == null) {
            throw new IllegalArgumentException("Application and reviewer must not be null");
        }
        if (application.getStatus() != ApplicationStatus.SUBMITTED) {
            throw new IllegalStateException("Only submitted applications can be reviewed");
        }
        if (rating < 1 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 1 and 10");
        }

        boolean alreadyReviewed = reviewRepository.findByApplication(application).stream()
                .anyMatch(existing -> existing.getReviewer().getId().equals(reviewer.getId()));
        if (alreadyReviewed) {
            throw new IllegalStateException("Reviewer has already submitted a review for this application");
        }

        ApplicationReview review = new ApplicationReview();
        review.setRating(rating);
        review.setComments(comments != null ? comments.trim() : "");
        review.setReviewDateTime(LocalDateTime.now());
        review.setApplication(application);
        review.setReviewer(reviewer);

        return reviewRepository.save(review);
    }
}