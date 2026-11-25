package org.team2.UASAMS.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team2.UASAMS.Entities.Application;
import org.team2.UASAMS.Entities.StudentUser;

import java.util.List;
import java.time.LocalDateTime;
import org.team2.UASAMS.Entities.*;


public interface ApplicationRepository extends JpaRepository<Application, Long>{
    List<Application> findBySubmissionDate(LocalDateTime submissionDate);
    List<Application> findByStudentUser(StudentUser studentUser);
    List<Application> findByDocuments(List<ApplicationDocument> documents);
    List<Application> findByAnswers(List<ApplicationAnswer> answers);
    List<Application> findByScholarship(Scholarship scholarship);
    List<Application> findByReviews(List<ApplicationReview> reviews);
    List<Application> findByStatus(String status);
}
