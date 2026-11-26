package org.team2.UASAMS.Services;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.team2.UASAMS.Entities.StudentUser;
import org.team2.UASAMS.Entities.User;
import org.team2.UASAMS.Entities.Enums.Major;
import org.team2.UASAMS.Entities.Enums.UserRole;
import org.team2.UASAMS.Repos.StudentUserRepository;
import org.team2.UASAMS.Repos.UserRepository;

import jakarta.transaction.Transactional;

/**
 * Service layer for creating and managing {@link StudentUser} profiles within the UASAMS system.
 *
 * @author Team 2
 * @since 11/25/26
 * @see StudentUser
 * @see StudentUserRepository
 */
@Service
public class StudentUserService {

    private final StudentUserRepository studentUserRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a {@code StudentUserService} with required repositories and password encoder.
     *
     * @param studentUserRepository repository for persisting {@link StudentUser} records
     * @param userRepository        repository for checking base {@link User} uniqueness
     * @param passwordEncoder       encoder used to hash raw passwords
     */
    public StudentUserService(
            StudentUserRepository studentUserRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.studentUserRepository = studentUserRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Validates and registers a new {@link StudentUser} account with student academic details.
     *
     * @param username       unique display username
     * @param email          unique email address
     * @param password       raw plaintext password to be encoded
     * @param firstName      student's first name
     * @param lastName       student's last name
     * @param studentIdNum   university student identification number
     * @param gpa            cumulative GPA (0.0 to 4.0)
     * @param birthday       student's date of birth
     * @param graduationDate expected graduation date
     * @param major          declared {@link Major}
     * @return the persisted {@link StudentUser} entity
     * @throws IllegalArgumentException if uniqueness checks fail or academic fields are out of valid bounds
     */
    @Transactional
    public StudentUser createStudentUser(
            String username,
            String email,
            String password,
            String firstName,
            String lastName,
            int studentIdNum,
            float gpa,
            LocalDate birthday,
            LocalDate graduationDate,
            Major major) {

        if (userRepository.findByEmail(email).isPresent() || userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username or email is already in use");
        }
        if (!studentUserRepository.findByStudentIdNum(studentIdNum).isEmpty()) {
            throw new IllegalArgumentException("Student ID number is already registered");
        }
        if (gpa < 0.0f || gpa > 4.0f) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
        }
        if (major == null) {
            throw new IllegalArgumentException("A valid academic major is required");
        }

        StudentUser student = new StudentUser();
        student.setUsername(username);
        student.setEmail(email);
        student.setPassword(passwordEncoder.encode(password));
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEnabled(true);
        student.addRole(UserRole.ROLE_STUDENT);

        student.setStudentIdNum(studentIdNum);
        student.setGpa(gpa);
        student.setBirthday(birthday);
        student.setGraduationDate(graduationDate);
        student.setMajor(major);

        return studentUserRepository.save(student);
    }

    /**
     * Promotes an existing base {@link User} record into a {@link StudentUser} profile.
     *
     * @param user           the existing base user
     * @param studentIdNum   university student identification number
     * @param gpa            cumulative GPA (0.0 to 4.0)
     * @param birthday       student's date of birth
     * @param graduationDate expected graduation date
     * @param major          declared {@link Major}
     * @return the newly promoted {@link StudentUser} entity
     * @throws IllegalArgumentException if the user is null, already a student, or academic inputs are invalid
     */
    @Transactional
    public StudentUser createStudentFromExistingUser(
            User user,
            int studentIdNum,
            float gpa,
            LocalDate birthday,
            LocalDate graduationDate,
            Major major) {

        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("Existing user must be saved before promotion");
        }
        if (studentUserRepository.existsById(user.getId())) {
            throw new IllegalStateException("User already has a student profile");
        }
        if (gpa < 0.0f || gpa > 4.0f) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
        }

        user.addRole(UserRole.ROLE_STUDENT);
        userRepository.save(user);
        userRepository.promoteToStudentTable(user.getId(), studentIdNum, gpa, birthday, graduationDate, major);

        return studentUserRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalStateException("Failed to load promoted StudentUser"));
    }
}