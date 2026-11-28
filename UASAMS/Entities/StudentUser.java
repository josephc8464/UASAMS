package org.team2.UASAMS.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

import org.team2.UASAMS.Entities.Enums.Major;

/**
 * Represents a student user account within the UASAMS system.
 * <p>
 * Extends {@link User} to store student-specific academic and profile details, including their
 * student ID number, GPA, birthday, expected graduation date, and declared {@link Major}.
 * Tracks all scholarship {@link Application} records submitted by the student.
 * </p>
 *
 * <p><b>Key Relationships:</b></p>
 * <ul>
 *   <li>{@link User} – Parent entity holding shared authentication and account details</li>
 *   <li>{@link Application} – Scholarship applications submitted by this student</li>
 * </ul>
 *
 * @author Team 2
 * @since 11/25/26
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student_users")
public class StudentUser extends User {

    /**
     * University-assigned student identification number.
     */
    private int studentIdNum;

    /**
     * Student's cumulative grade point average.
     */
    private float gpa;

    /**
     * Student's date of birth.
     */
    private LocalDate birthday;

    /**
     * Expected or actual graduation date.
     */
    private LocalDate graduationDate;

    /**
     * Student's declared academic major.
     */
    @Enumerated(EnumType.STRING)
    private Major major;

    /**
     * Scholarship applications submitted by this student.
     */
    @OneToMany(mappedBy = "studentUser", cascade = CascadeType.ALL)
    private List<Application> applications = new ArrayList<>();
}