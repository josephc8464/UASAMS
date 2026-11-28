package org.team2.UASAMS.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/guest/home")
    public String home() {
        return "home-pages/guest-home"; // optional fallback
    }
}