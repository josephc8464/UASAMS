package org.team2.UASAMS.Repos;

import org.team2.UASAMS.Entities.User;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Modifying
    @Query(value = """
        INSERT INTO student_users (id, gpa, birthday, graduation_date, school)
        VALUES (:id, :gpa, :birthday, :graduationDate, :school)
        """, nativeQuery = true)
    void promoteToStudentTable(
            @Param("id") Long id,
            @Param("gpa") float gpa,
            @Param("birthday") LocalDate birthday,
            @Param("graduationDate") LocalDate graduationDate,
            @Param("school") String school
    );
}