package org.team2.UASAMS.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team2.UASAMS.Entities.*;

import java.util.List;
import java.time.LocalDateTime;



public interface ApplicationReviewRepository extends JpaRepository<ApplicationReview, Long> {
    List<ApplicationReview> findByReviewer(User reviewer);
    List<ApplicationReview> findByRating(int rating);
    List<ApplicationReview> findByReviewDateTime(LocalDateTime reviewDateTime);
    List<ApplicationReview> findByComments(String comments);
    List<ApplicationReview> findByApplication(Application application);
}
