package org.team2.UASAMS.Repos;

import org.team2.UASAMS.Entities.StudentUser;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;


public interface StudentUserRepository extends JpaRepository<StudentUser, Long> {
    Optional<StudentUser> findByEmail(String email); 
    
    List<StudentUser> findBySchool(String school);

    List<StudentUser> findByDemographics(List<String> demographics);
    List<StudentUser> findByBirthdayGreaterThanEqual(LocalDateTime birthday);

    List<StudentUser> findByMajors(List<String> majors);
    List<StudentUser> findByMinors(List<String> minors);

    List<StudentUser> findByGpaGreaterThanEqual(float gpa);

    List<StudentUser> findByFirstName(String firstName);
    List<StudentUser> findByLastName(String lastName);

    List<StudentUser> findByGraduationDateGreaterThan(LocalDateTime graduationDate);
}
