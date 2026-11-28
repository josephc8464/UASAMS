package org.team2.UASAMS.Seed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.team2.UASAMS.Entities.*;
import org.team2.UASAMS.Entities.Enums.*;
import org.team2.UASAMS.Repos.*;

@Component
@Profile("seed") // Only runs when the "seed" profile is active
public class SeedData implements CommandLineRunner {

    private final UserRepository userRepo;
    private final ScholarshipRepository scholarshipRepo;
    private final PasswordEncoder passwordEncoder;

    public SeedData(
            UserRepository userRepo,
            ScholarshipRepository scholarshipRepo,
            PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.scholarshipRepo = scholarshipRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // Safety guard: avoid duplicate data if accidentally run on a non-empty DB
        if (userRepo.count() > 0 || scholarshipRepo.count() > 0) {
            System.out.println(">>> SeedData skipped: database already contains records.");
            return;
        }

        System.out.println(">>> Seeding database...");

        // 1. Seed Users (Admin / Reviewer & StudentUser)
        User admin = new User();
        admin.setFirstName("Jane");
        admin.setLastName("Steward");
        admin.setUsername("admin");
        admin.setEmail("admin@uasams.edu");
        admin.setPassword(passwordEncoder.encode("password"));
        admin.setEnabled(true);
        admin.setRoles(Set.of(UserRole.ROLE_ADMIN, UserRole.ROLE_STEWARD, UserRole.ROLE_REVIEWER));
        userRepo.save(admin);

        StudentUser student = new StudentUser();
        student.setFirstName("Alex");
        student.setLastName("Rivera");
        student.setUsername("student1");
        student.setEmail("arivera@uasams.edu");
        student.setPassword(passwordEncoder.encode("password"));
        student.setEnabled(true);
        student.setRoles(Set.of(UserRole.ROLE_STUDENT));
        student.setStudentIdNum(23456789);
        student.setGpa(3.85f);
        student.setBirthday(LocalDate.of(2004, 5, 14));
        student.setGraduationDate(LocalDate.of(2027, 5, 15));
        student.setMajor(Major.SOFTWARE_ENGINEERING);
        userRepo.save(student);

        // 2. Seed a Scholarship (start as DRAFT to attach questions, then OPEN)
        Scholarship scholarship = new Scholarship();
        scholarship.setName("UA Engineering Excellence Award");
        scholarship.setDescription("Merit scholarship for undergraduate engineering students.");
        scholarship.setAwardAmount(2500.00f);
        scholarship.setProviderContact("scholarships@engr.edu");
        scholarship.setEligibleMajors(Set.of(
                Major.SOFTWARE_ENGINEERING,
                Major.COMPUTER_SCIENCE_AND_ENGINEERING,
                Major.ELECTRICAL_AND_COMPUTER_ENGINEERING
        ));
        scholarship.setDeadline(LocalDateTime.now().plusMonths(1));
        scholarship.setDisbursementInstructions("Credited directly to student Bursar account.");
        scholarship.setStatus(ScholarshipStatus.DRAFT);

        ScholarshipQuestion q1 = new ScholarshipQuestion();
        q1.setQuestionText("Describe a technical project you built and what you learned.");
        q1.setInputType(ScholarshipQuestionInputType.TEXT);
        q1.setRequired(true);
        scholarship.addQuestion(q1);

        // Transition to OPEN after questions are attached
        scholarship.setStatus(ScholarshipStatus.OPEN);
        scholarshipRepo.save(scholarship);

        System.out.println(">>> Database seeding complete.");
    }
}