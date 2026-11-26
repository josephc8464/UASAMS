package org.team2.UASAMS.Repos;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team2.UASAMS.Entities.StudentUser;
import org.team2.UASAMS.Entities.Enums.Major;

/**
 * Data access repository for managing {@link StudentUser} accounts.
 * <p>
 * Provides lookup and filtering queries for student profiles by email, student ID number,
 * declared {@link Major}, minimum GPA, and graduation date.
 * </p>
 *
 * @author Team 2
 * @since 11/25/26
 * @see StudentUser
 */
public interface StudentUserRepository extends JpaRepository<StudentUser, Long> {
    Optional<StudentUser> findByEmail(String email);
    List<StudentUser> findByStudentIdNum(int studentIdNum);
    List<StudentUser> findByBirthdayGreaterThanEqual(LocalDate birthday);
    List<StudentUser> findByMajor(Major major);
    List<StudentUser> findByGpaGreaterThanEqual(float gpa);
    List<StudentUser> findByFirstName(String firstName);
    List<StudentUser> findByLastName(String lastName);
    List<StudentUser> findByGraduationDateGreaterThan(LocalDate graduationDate);
}