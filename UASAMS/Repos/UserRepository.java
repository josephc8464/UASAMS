package org.team2.UASAMS.Repos;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.team2.UASAMS.Entities.User;
import org.team2.UASAMS.Entities.Enums.Major;

/**
 * Data access repository for managing base {@link User} accounts.
 * <p>
 * Supports authentication lookups by username or email and includes a native query
 * to promote an existing base user into the {@code student_users} joined subclass table.
 * </p>
 *
 * @author Team 2
 * @since 11/25/26
 * @see User
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    /**
     * Inserts a corresponding row into the {@code student_users} joined inheritance table
     * to promote an existing {@link User} record into a {@code StudentUser}.
     *
     * @param id             the existing user's primary key ID
     * @param studentIdNum university student ID number
     * @param gpa            student's cumulative GPA
     * @param birthday       student's date of birth
     * @param graduationDate expected graduation date
     * @param major          student's declared {@link Major}
     */
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