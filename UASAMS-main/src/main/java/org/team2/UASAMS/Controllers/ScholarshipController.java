package org.team2.UASAMS.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team2.UASAMS.Services.ScholarshipService;


@Controller
@RequestMapping("/scholarships") // Gets URL associated with controller 
public class ScholarshipController {
    private final ScholarshipService scholarshipService;

    public ScholarshipController(
        ScholarshipService scholarshipService) {
            this.scholarshipService = scholarshipService;
        }

    @GetMapping // Handles a GET request to the URL from request
    public String listScholarships(Model model) {
        model.addAttribute ("scholarships", scholarshipService.getPublishedScholarships());

        return "scholarships"; // Tells Spring to render Scholarships.html
    }
}
