package org.team2.UASAMS.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team2.UASAMS.Entities.ScholarshipQuestion;

/**
 * Data access repository for managing {@link ScholarshipQuestion} prompts.
 * <p>
 * Inherits standard CRUD operations from {@link JpaRepository} and supports fetching
 * all questions belonging to a specific scholarship ID.
 * </p>
 *
 * @author Team 2
 * @since 11/25/26
 * @see ScholarshipQuestion
 */
public interface ScholarshipQuestionRepository extends JpaRepository<ScholarshipQuestion, Long> {

    /**
     * Retrieves all application questions attached to a given scholarship.
     *
     * @param scholarshipId the ID of the parent scholarship
     * @return list of questions for that scholarship
     */
    List<ScholarshipQuestion> findByScholarship_Id(Long scholarshipId);
}