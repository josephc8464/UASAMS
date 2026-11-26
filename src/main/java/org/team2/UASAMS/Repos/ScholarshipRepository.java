package org.team2.UASAMS.Repos;

import org.team2.UASAMS.Entities.Scholarship;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team2.UASAMS.Entities.Application;
import org.team2.UASAMS.Entities.AwardSelection;
import java.time.LocalDateTime;




public interface ScholarshipRepository extends JpaRepository<Scholarship, Long> {
    List<Scholarship> findByStatus(String status);
    List<Scholarship> findByApplications(List<Application> applications);
    List<Scholarship> findByAwardAmount(float awardAmount);
    List<Scholarship> findByAwardAmountGreaterThanEqual(float awardAmount);
    List<Scholarship> findByAwardSelections(List<AwardSelection> awardSelections);
    List<Scholarship> findByDeadline(LocalDateTime deadline);
    List<Scholarship> findByDescription(String description);
    List<Scholarship> findByName(String name);
}