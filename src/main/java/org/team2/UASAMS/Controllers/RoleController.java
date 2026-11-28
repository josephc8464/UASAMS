package org.team2.UASAMS.Controllers;

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
import org.team2.UASAMS.Entities.User;
import org.team2.UASAMS.Entities.Enums.Major;
import org.team2.UASAMS.Services.AuthService;
import org.team2.UASAMS.Services.StudentUserService;

@Controller
@RequestMapping("/student/signup")
public class RoleController {

    private final StudentUserService studentUserService;
    private final AuthService authService;

    public RoleController(StudentUserService studentUserService, AuthService authService) {
        this.studentUserService = studentUserService;
        this.authService = authService;
    }

    @GetMapping
    public String studentSignUp(Model model) {
        model.addAttribute("majors", Major.values());
        return "student-signup";
    }

    @PostMapping
    public String studentSignUp(
            @RequestParam int studentIdNum,
            @RequestParam Major major,
            @RequestParam float gpa,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthday,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate gradDate,
            Model model) {

        try {
            User currentUser = authService.getLoggedInProfile();

            StudentUser student = studentUserService.createStudentFromExistingUser(
                    currentUser,
                    studentIdNum,
                    gpa,
                    birthday,
                    gradDate,
                    major
            );

            // Refresh the active Spring Security session with the new StudentUser & authorities
            Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
            Authentication updatedAuth = new UsernamePasswordAuthenticationToken(
                    student,
                    currentAuth.getCredentials(),
                    student.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(updatedAuth);

            return "redirect:/student/home";

        } catch (IllegalArgumentException | IllegalStateException e) {
            model.addAttribute("majors", Major.values());
            model.addAttribute("error", e.getMessage());
            return "student-signup";
        }
    }
}