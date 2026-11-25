package org.team2.UASAMS.Repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.team2.UASAMS.Entities.ScholarshipQuestion;


// Using JpaRepo because it already provides save(question), findById(id), deleteById(id), and findalAll() to create,
// update, retrieve, or delete questions
public interface ScholarshipQuestionRepository 
    extends JpaRepository <ScholarshipQuestion, Long> {

        // Creating a new method for use in project called find by scholarshipId (springdata generates database)
    List <ScholarshipQuestion> findByScholarship_Id(Long ScholarshipId);
}
