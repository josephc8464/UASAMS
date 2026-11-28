package org.team2.UASAMS.Controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.team2.UASAMS.Entities.StudentUser;
import org.team2.UASAMS.Entities.User;

@Controller
public class HomeController {

    @GetMapping("/guest/home")
    public String home() {
        return "home-pages/guest-home";
    }

    @GetMapping("/faculty/home")
    public String facultyHome(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("user", currentUser);
        return "home-pages/faculty-home";
    }

    @GetMapping("/student/home")
    public String studentHome(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("user", currentUser);
        if (currentUser instanceof StudentUser studentUser) {
            model.addAttribute("student", studentUser);
        }
        return "home-pages/student-home";
    }
}