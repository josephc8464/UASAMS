package org.team2.UASAMS.Repos;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team2.UASAMS.Entities.Application;
import org.team2.UASAMS.Entities.AwardSelection;
import org.team2.UASAMS.Entities.Scholarship;
import org.team2.UASAMS.Entities.Enums.ScholarshipStatus;

/**
 * Data access repository for managing {@link Scholarship} opportunities.
 * <p>
 * Supports filtering scholarships by {@link ScholarshipStatus}, award amount thresholds,
 * deadlines, and title.
 * </p>
 *
 * @author Team 2
 * @since 11/25/26
 * @see Scholarship
 */
public interface ScholarshipRepository extends JpaRepository<Scholarship, Long> {
    List<Scholarship> findByStatus(ScholarshipStatus status);
    List<Scholarship> findByApplications(List<Application> applications);
    List<Scholarship> findByAwardAmount(float awardAmount);
    List<Scholarship> findByAwardAmountGreaterThanEqual(float awardAmount);
    List<Scholarship> findByAwardSelections(List<AwardSelection> awardSelections);
    List<Scholarship> findByDeadline(LocalDateTime deadline);
    List<Scholarship> findByDescription(String description);
    List<Scholarship> findByName(String name);
}