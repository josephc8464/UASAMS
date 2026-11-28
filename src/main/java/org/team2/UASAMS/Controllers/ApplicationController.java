package org.team2.UASAMS.Controllers;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.team2.UASAMS.Entities.User;
import org.team2.UASAMS.Services.ApplicationService;


@Controller 
@RequestMapping ("/applications")
@PreAuthorize("hasRole('STUDENT')")
public class ApplicationController {
    private final ApplicationService applicationService; // Create an object of ApplicationService

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/{id}")
    public String viewApplication(
        @PathVariable Long id,
        @AuthenticationPrincipal User currentUser,
        Model model) {
            var application = applicationService.getApplicationPage(id, currentUser.getId());


            model.addAttribute("applicationId", application.id());
            model.addAttribute("scholarshipName", application.scholarshipName());
            model.addAttribute("status", application.status().name());
            model.addAttribute("submittedAt", application.submissionDateTime());

            return "applications/detail";
        }

        @PostMapping("/{id}/submit")
        public String submitApplication(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser,
            RedirectAttributes redirectAttributes) {

                try {
                    applicationService.submitApplication(id, currentUser.getId());

                    redirectAttributes.addFlashAttribute("successMessage", "Application submitted successfully!");
                } catch (IllegalStateException e) {
                    redirectAttributes.addFlashAttribute("errormessage", e.getMessage());
                }

                return "redirect:/applications/" + id;
            }


}
