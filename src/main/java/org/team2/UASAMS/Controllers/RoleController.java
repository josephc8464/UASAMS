package org.team2.UASAMS.Controllers;

import java.security.Principal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.team2.UASAMS.Entities.StudentUser;
import org.team2.UASAMS.Entities.Enums.Major;
import org.team2.UASAMS.Services.StudentService;

@RequestMapping("/student/signup")
@Controller
public class RoleController {

    private final StudentService studentService;

    public RoleController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String studentSignUp(Model model) {
        return "student-signup";
    }

    @PostMapping
    public String studentSignUp(
            @RequestParam int studentIdNum,
            @RequestParam Major major,
            @RequestParam String email,
            @RequestParam float gpa,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthday,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate gradDate,
            @RequestParam(defaultValue = "ROLE_STUDENT") String role,
            Principal principal,
            Model model) {

        try {
            StudentUser student = studentService.upgradeGuestToStudent(
                    studentIdNum,
                    email,
                    major,
                    gpa,
                    birthday,
                    gradDate,
                    role
            );

            // Refresh the active Spring Security session with the new StudentUser & authorities
            Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
            Authentication updatedAuth = new UsernamePasswordAuthenticationToken(
                    student,
                    currentAuth.getCredentials(),
                    student.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(updatedAuth);

            return "redirect:/home";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "student-signup";
        }
    }
}
