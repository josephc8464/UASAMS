package org.team2.UASAMS.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team2.UASAMS.Entities.Application;
import org.team2.UASAMS.Entities.ApplicationAnswer;
import org.team2.UASAMS.Entities.ScholarshipQuestion;

/**
 * Data access repository for managing {@link ApplicationAnswer} entities.
 * <p>
 * Supports retrieving student responses grouped by application or by prompt question.
 * </p>
 *
 * @author Team 2
 * @since 11/25/26
 * @see ApplicationAnswer
 */
public interface ApplicationAnswerRepository extends JpaRepository<ApplicationAnswer, Long> {
    List<ApplicationAnswer> findByApplication(Application application);
    List<ApplicationAnswer> findByQuestion(ScholarshipQuestion question);
}