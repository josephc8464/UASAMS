package org.team2.UASAMS.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team2.UASAMS.Entities.*;
import java.util.List;
import java.time.LocalDateTime;


public interface AwardSelectionRepository extends JpaRepository<AwardSelection, Long> {
    List<AwardSelection> findByDecisionDate(LocalDateTime decisionDate);
    List<AwardSelection> findByStatus(String status);
    List<AwardSelection> findByScholarship(Scholarship scholarship);
    List<AwardSelection> findByApplication(Application application);
    List<AwardSelection> findByDesignatedPanelUser(User designatedPanelUser);
}
