package org.team2.UASAMS.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team2.UASAMS.Entities.Application;
import org.team2.UASAMS.Entities.ApplicationAnswer;
import org.team2.UASAMS.Entities.ScholarshipQuestion;

import java.util.List;


public interface ApplicationAnswerRepository extends JpaRepository<ApplicationAnswer, Long> {
    List<ApplicationAnswer> findByApplication(Application application);
    List<ApplicationAnswer> findByQuestion(ScholarshipQuestion question);
}
