package org.team2.UASAMS.Repos;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team2.UASAMS.Entities.*;

/**
 * Data access repository for managing {@link ApplicationReview} evaluations.
 * <p>
 * Supports querying reviews by assigned reviewer, target application, score rating, or review date.
 * </p>
 *
 * @author Team 2
 * @since 11/25/26
 * @see ApplicationReview
 */
public interface ApplicationReviewRepository extends JpaRepository<ApplicationReview, Long> {
    List<ApplicationReview> findByReviewer(User reviewer);
    List<ApplicationReview> findByRating(int rating);
    List<ApplicationReview> findByReviewDateTime(LocalDateTime reviewDateTime);
    List<ApplicationReview> findByComments(String comments);
    List<ApplicationReview> findByApplication(Application application);
}