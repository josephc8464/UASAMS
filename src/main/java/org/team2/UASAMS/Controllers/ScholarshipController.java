package org.team2.UASAMS.Controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.team2.UASAMS.Entities.Enums.Major;
import org.team2.UASAMS.Entities.Enums.ScholarshipStatus;
import org.team2.UASAMS.Services.ScholarshipService;

@Controller
@RequestMapping("/scholarships")
public class ScholarshipController {

    private final ScholarshipService scholarshipService;

    public ScholarshipController(ScholarshipService scholarshipService) {
        this.scholarshipService = scholarshipService;
    }

    @GetMapping
    public String listScholarships(Model model) {
        model.addAttribute("scholarships", scholarshipService.getOpenScholarships());
        return "scholarships";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("majors", Major.values());
        return "scholarships/create";
    }

    @PostMapping("/create")
    public String createScholarship(
            @RequestParam("name") String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("awardAmount") float awardAmount,
            @RequestParam(value = "providerContact", required = false) String providerContact,
            @RequestParam(value = "eligibleMajors", required = false) List<Major> eligibleMajors,
            @RequestParam("deadline") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadline,
            @RequestParam(value = "disbursementInstructions", required = false) String disbursementInstructions,
            RedirectAttributes redirectAttributes,
            Model model) {

        try {
            scholarshipService.createScholarship(
                    name,
                    description,
                    awardAmount,
                    deadline,
                    ScholarshipStatus.DRAFT
            );

            redirectAttributes.addFlashAttribute("successMessage", "Scholarship created successfully!");
            return "redirect:/scholarships";

        } catch (IllegalArgumentException e) {
            model.addAttribute("majors", Major.values());
            model.addAttribute("errorMessage", e.getMessage());
            return "scholarships/create";
        }
    }
}