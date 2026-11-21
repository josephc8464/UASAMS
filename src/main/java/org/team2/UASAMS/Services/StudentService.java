package org.team2.UASAMS.Services;

import java.time.LocalDate;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.team2.UASAMS.Entities.StudentUser;
import org.team2.UASAMS.Entities.User;
import org.team2.UASAMS.Entities.UserRole;
import org.team2.UASAMS.Repos.StudentUserRepository;
import org.team2.UASAMS.Repos.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class StudentService {

    private final UserRepository userRepository;
    private final StudentUserRepository studentUserRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public StudentService(UserRepository userRepository, StudentUserRepository studentUserRepository) {
        this.userRepository = userRepository;
        this.studentUserRepository = studentUserRepository;
    }

    @Transactional
    public StudentUser upgradeGuestToStudent(
            String email,
            String schoolName,
            String major,
            float gpa,
            LocalDate birthday,
            LocalDate gradDate,
            String role) {

        if (gpa < 0.0f || gpa > 4.0f) {
            throw new IllegalArgumentException("GPA must be between 0.00 and 4.00.");
        }

        if (birthday == null || !birthday.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Birthday must be before today's date.");
        }

        if (gradDate == null || gradDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Graduation date must be today or in the future.");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        if (user instanceof StudentUser existingStudent) {
            return existingStudent;
        }

        // 1. Update roles on base User (ROLE_GUEST -> ROLE_STUDENT)
        user.getRoles().remove(UserRole.ROLE_GUEST);
        user.addRole(UserRole.valueOf(role));
        userRepository.saveAndFlush(user);

        // 2. Insert child row into student_users linked by user.getId()
        userRepository.promoteToStudentTable(
                user.getId(),
                gpa,
                birthday,
                gradDate,
                schoolName
        );

        // 3. Clear Hibernate cache so it reloads the row as a StudentUser instance
        entityManager.clear();

        StudentUser studentUser = studentUserRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalStateException("Failed to load upgraded StudentUser."));

        // 4. Add the major to StudentUser's majors list
        if (major != null && !major.isBlank()) {
            studentUser.getMajors().add(major.trim());
        }

        return studentUserRepository.save(studentUser);
    }
}