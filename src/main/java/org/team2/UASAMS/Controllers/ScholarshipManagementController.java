package org.team2.UASAMS.Controllers;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.team2.UASAMS.Entities.Scholarship;
import org.team2.UASAMS.Entities.Enums.Major;
import org.team2.UASAMS.Entities.Enums.ScholarshipQuestionInputType;
import org.team2.UASAMS.Entities.Enums.ScholarshipStatus;
import org.team2.UASAMS.Services.ScholarshipQuestionService;
import org.team2.UASAMS.Services.ScholarshipService;

/**
 * Spring MVC controller exposing faculty endpoints for creating, updating,
 * publishing, closing, archiving, and configuring questions for {@link Scholarship}
 * listings via {@link ScholarshipService} and {@link ScholarshipQuestionService}.
 *
 * @author Team 2
 * @since 11/26/26
 */
@Controller
@RequestMapping("/faculty/scholarships")
public class ScholarshipManagementController {

    private final ScholarshipService scholarshipService;
    private final ScholarshipQuestionService scholarshipQuestionService;

    public ScholarshipManagementController(
            ScholarshipService scholarshipService,
            ScholarshipQuestionService scholarshipQuestionService) {
        this.scholarshipService = scholarshipService;
        this.scholarshipQuestionService = scholarshipQuestionService;
    }

    /**
     * Displays the faculty scholarship dashboard listing all scholarships.
     *
     * @param model UI model populated with all scholarships
     * @return view name for the faculty scholarships overview
     */
    @GetMapping
    public String showFacultyScholarships(Model model) {
        model.addAttribute("scholarships", scholarshipService.getAllScholarships());
        model.addAttribute("majors", Major.values());
        model.addAttribute("statuses", ScholarshipStatus.values());
        return "faculty/scholarships";
    }

    /**
     * Displays the dedicated form for creating a new scholarship.
     *
     * @param model UI model populated with available majors
     * @return view name for creating a scholarship
     */
    @GetMapping("/create")
    public String showCreateScholarshipForm(Model model) {
        model.addAttribute("majors", Major.values());
        return "faculty/create-scholarship";
    }

    /**
     * Creates a new scholarship and redirects to its unified edit workspace on success,
     * or returns to the creation form with retained inputs on validation failure.
     *
     * @param name                     scholarship title
     * @param description              optional description
     * @param providerContact          optional provider contact
     * @param eligibleMajors           optional set of eligible major strings
     * @param awardAmount              monetary award value
     * @param deadline                 application deadline
     * @param disbursementInstructions optional disbursement instructions
     * @param status                   optional initial status (defaults to {@link ScholarshipStatus#DRAFT})
     * @param model                    UI model used to preserve form values if validation fails
     * @param redirectAttributes       flash attributes for post-redirect feedback
     * @return redirect to {@code /faculty/scholarships/{id}/edit} on success, or {@code faculty/create-scholarship} on error
     */
    @PostMapping("/create")
    public String createScholarship(
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String providerContact,
            @RequestParam(required = false) Set<String> eligibleMajors,
            @RequestParam float awardAmount,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadline,
            @RequestParam(required = false) String disbursementInstructions,
            @RequestParam(required = false) ScholarshipStatus status,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            Scholarship created = scholarshipService.createScholarship(
                    name,
                    description,
                    providerContact,
                    eligibleMajors,
                    awardAmount,
                    deadline,
                    disbursementInstructions,
                    status);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Scholarship \"" + created.getName() + "\" created successfully.");
            return "redirect:/faculty/scholarships/" + created.getId() + "/edit";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("majors", Major.values());
            model.addAttribute("name", name);
            model.addAttribute("description", description);
            model.addAttribute("providerContact", providerContact);
            model.addAttribute("selectedMajors", Major.fromStrings(eligibleMajors));
            model.addAttribute("awardAmount", awardAmount);
            model.addAttribute("deadline", deadline);
            model.addAttribute("disbursementInstructions", disbursementInstructions);
            return "faculty/create-scholarship";
        }
    }

    /**
     * Displays the unified edit page for updating scholarship fields, managing lifecycle
     * transitions (publish, close, archive), and adding or removing scholarship questions.
     *
     * @param scholarshipId      ID of the scholarship to edit
     * @param model              UI model populated with scholarship details, majors, and questions
     * @param redirectAttributes flash attributes if the scholarship ID does not exist
     * @return view name for editing the scholarship, or redirect to the overview list if not found
     */
    @GetMapping("/{scholarshipId}/edit")
    public String showEditScholarshipPage(
            @PathVariable Long scholarshipId,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            Scholarship scholarship = scholarshipService.getScholarshipById(scholarshipId);
            model.addAttribute("scholarship", scholarship);
            model.addAttribute("majors", Major.values());
            model.addAttribute("statuses", ScholarshipStatus.values());
            model.addAttribute("inputTypes", ScholarshipQuestionInputType.values());
            model.addAttribute("questions", scholarshipQuestionService.getQuestions(scholarshipId));
            return "faculty/edit-scholarship";
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/faculty/scholarships";
        }
    }

    /**
     * Updates mutable details of an existing scholarship and redirects back to its edit page.
     *
     * @param scholarshipId            ID of the scholarship to update
     * @param name                     updated scholarship title
     * @param description              updated description
     * @param awardAmount              updated monetary award value
     * @param deadline                 updated submission deadline
     * @param status                   optional status override
     * @param providerContact          updated provider contact details
     * @param eligibleMajors           updated set of eligible major strings
     * @param disbursementInstructions updated disbursement instructions
     * @param redirectAttributes       flash attributes for success or validation messages
     * @return redirect target to the scholarship edit page
     */
    @PostMapping("/{scholarshipId}/update")
    public String updateScholarship(
            @PathVariable Long scholarshipId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Float awardAmount,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadline,
            @RequestParam(required = false) ScholarshipStatus status,
            @RequestParam(required = false) String providerContact,
            @RequestParam(required = false) Set<String> eligibleMajors,
            @RequestParam(required = false) String disbursementInstructions,
            RedirectAttributes redirectAttributes) {
        try {
            Scholarship updated = scholarshipService.updateScholarship(
                    scholarshipId,
                    name,
                    description,
                    awardAmount,
                    deadline,
                    providerContact,
                    eligibleMajors,
                    disbursementInstructions);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Scholarship \"" + updated.getName() + "\" updated successfully.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/faculty/scholarships/" + scholarshipId + "/edit";
    }

    /**
     * Validates completeness and publishes a draft scholarship so its status becomes {@link ScholarshipStatus#OPEN}.
     *
     * @param scholarshipId      ID of the scholarship to publish
     * @param redirectAttributes flash attributes for success or validation messages
     * @return redirect target to the scholarship edit page
     */
    @PostMapping("/{scholarshipId}/publish")
    public String publishScholarship(
            @PathVariable Long scholarshipId,
            RedirectAttributes redirectAttributes) {
        try {
            Scholarship published = scholarshipService.publishScholarship(scholarshipId);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Scholarship \"" + published.getName() + "\" published.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/faculty/scholarships/" + scholarshipId + "/edit";
    }

    /**
     * Closes an open scholarship so its status becomes {@link ScholarshipStatus#CLOSED}.
     *
     * @param scholarshipId      ID of the scholarship to close
     * @param redirectAttributes flash attributes for success or validation messages
     * @return redirect target to the scholarship edit page
     */
    @PostMapping("/{scholarshipId}/close")
    public String closeScholarship(
            @PathVariable Long scholarshipId,
            RedirectAttributes redirectAttributes) {
        try {
            Scholarship closed = scholarshipService.closeScholarship(scholarshipId);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Scholarship \"" + closed.getName() + "\" closed.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/faculty/scholarships/" + scholarshipId + "/edit";
    }

    /**
     * Archives a closed scholarship so its status becomes {@link ScholarshipStatus#ARCHIVED}.
     *
     * @param scholarshipId      ID of the scholarship to archive
     * @param redirectAttributes flash attributes for success or validation messages
     * @return redirect target to the scholarship edit page
     */
    @PostMapping("/{scholarshipId}/archive")
    public String archiveScholarship(
            @PathVariable Long scholarshipId,
            RedirectAttributes redirectAttributes) {
        try {
            Scholarship archived = scholarshipService.archiveScholarship(scholarshipId);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Scholarship \"" + archived.getName() + "\" archived.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/faculty/scholarships/" + scholarshipId + "/edit";
    }

    /**
     * Adds a new application question to the specified scholarship from the edit page.
     *
     * @param scholarshipId      ID of the scholarship receiving the question
     * @param prompt             question prompt text
     * @param inputType          expected answer input type
     * @param required           whether applicants must answer this question
     * @param redirectAttributes flash attributes for success or validation messages
     * @return redirect target to the scholarship edit page
     */
    @PostMapping("/{scholarshipId}/questions/add")
    public String addScholarshipQuestion(
            @PathVariable Long scholarshipId,
            @RequestParam String prompt,
            @RequestParam(defaultValue = "TEXT") ScholarshipQuestionInputType inputType,
            @RequestParam(defaultValue = "false") boolean required,
            RedirectAttributes redirectAttributes) {
        try {
            scholarshipQuestionService.addQuestion(scholarshipId, prompt, required, inputType.name());
            redirectAttributes.addFlashAttribute("successMessage", "Question added successfully.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/faculty/scholarships/" + scholarshipId + "/edit";
    }

    /**
     * Removes an existing application question from the specified scholarship.
     *
     * @param scholarshipId      ID of the parent scholarship
     * @param questionId         ID of the question to delete
     * @param redirectAttributes flash attributes for success or validation messages
     * @return redirect target to the scholarship edit page
     */
    @PostMapping("/{scholarshipId}/questions/{questionId}/delete")
    public String deleteScholarshipQuestion(
            @PathVariable Long scholarshipId,
            @PathVariable Long questionId,
            RedirectAttributes redirectAttributes) {
        try {
            scholarshipQuestionService.deleteQuestion(scholarshipId, questionId);
            redirectAttributes.addFlashAttribute("successMessage", "Question removed.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/faculty/scholarships/" + scholarshipId + "/edit";
    }
}