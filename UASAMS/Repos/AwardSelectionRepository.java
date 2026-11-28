package org.team2.UASAMS.Repos;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team2.UASAMS.Entities.*;

/**
 * Data access repository for managing {@link AwardSelection} records.
 * <p>
 * Supports querying final scholarship award decisions by scholarship, application, decision date,
 * award status, and designated panel user.
 * </p>
 *
 * @author Team 2
 * @since 11/25/26
 * @see AwardSelection
 */
public interface AwardSelectionRepository extends JpaRepository<AwardSelection, Long> {
    List<AwardSelection> findByDecisionDate(LocalDateTime decisionDate);
    List<AwardSelection> findByStatus(String status);
    List<AwardSelection> findByScholarship(Scholarship scholarship);
    List<AwardSelection> findByApplication(Application application);
    List<AwardSelection> findByDesignatedPanelUser(User designatedPanelUser);
}