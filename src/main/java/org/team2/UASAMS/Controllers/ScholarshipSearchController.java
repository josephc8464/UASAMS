package org.team2.UASAMS.Controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team2.UASAMS.Entities.Scholarship;
import org.team2.UASAMS.Entities.Enums.Major;
import org.team2.UASAMS.Entities.Enums.ScholarshipStatus;
import org.team2.UASAMS.Services.ScholarshipService;

/**
 * Spring MVC controller handling browsing and searching of open {@link Scholarship}
 * listings via {@link ScholarshipService}.
 *
 * @author Team 2
 * @since 11/26/26
 */
@Controller
@RequestMapping("/scholarships")
public class ScholarshipSearchController {

    private final ScholarshipService scholarshipService;

    /**
     * Constructs a {@code ScholarshipSearchController} with the required scholarship service.
     *
     * @param scholarshipService service providing scholarship search and query operations
     */
    public ScholarshipSearchController(ScholarshipService scholarshipService) {
        this.scholarshipService = scholarshipService;
    }

    /**
     * Displays all currently open scholarships.
     *
     * @param model MVC model populated with open scholarships, available majors, and statuses
     * @return view name rendering the open scholarships list
     */
    @GetMapping
    public String listOpenScholarships(Model model) {
        List<Scholarship> openScholarships = scholarshipService.getOpenScholarships();
        model.addAttribute("scholarships", openScholarships);
        model.addAttribute("majors", Major.values());
        model.addAttribute("statuses", ScholarshipStatus.values());
        return "student/scholarship-list";
    }
}