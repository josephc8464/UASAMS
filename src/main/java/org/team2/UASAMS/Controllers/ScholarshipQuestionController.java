package org.team2.UASAMS.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.team2.UASAMS.Services.ScholarshipQuestionService;


@Controller 
@RequestMapping ("/scholarships/{scholarshipId}/questions")
public class ScholarshipQuestionController {
    
    private final ScholarshipQuestionService questionService;

    public ScholarshipQuestionController(
        ScholarshipQuestionService questionService) {
            this.questionService = questionService;
        }

    @GetMapping
    // viewQuestions handles HTTP request on UI, model is from Thymeleaf
    public String viewQuestions(
        @PathVariable Long scholarshipId, Model model) {

            model.addAttribute(
                "questions",
                questionService.getQuestions(scholarshipId)
            );

            model.addAttribute("scholarshipId", scholarshipId);

            return "scholarship-questions";
        }
    
   @PostMapping
   public String createQuestion(
    @PathVariable Long scholarshipId,
    @RequestParam String questionText,
    @RequestParam(defaultValue = "false") boolean required,
    @RequestParam String inputType) {

        questionService.addQuestion(scholarshipId, questionText, required, inputType);

        return "redirect:/scholarships/" + scholarshipId + "/questions";
    }
}
