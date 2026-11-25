package org.team2.UASAMS.Repos;

import org.team2.UASAMS.Entities.User;
import org.team2.UASAMS.Entities.Enums.Major;

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
        INSERT INTO student_users (id, student_id_num, gpa, birthday, graduation_date, major)
        VALUES (:id, :studentIdNum, :gpa, :birthday, :graduationDate, :major)
        """, nativeQuery = true)
    void promoteToStudentTable(
            @Param("id") Long id,
            @Param("studentIdNum") int studentIdNum,
            @Param("gpa") float gpa,
            @Param("birthday") LocalDate birthday,
            @Param("graduationDate") LocalDate graduationDate,
            @Param("major") Major major
    );
}