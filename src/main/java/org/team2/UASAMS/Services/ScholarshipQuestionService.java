package org.team2.UASAMS.Services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.team2.UASAMS.Entities.Scholarship;
import org.team2.UASAMS.Entities.ScholarshipQuestion;
import org.team2.UASAMS.Repos.ScholarshipQuestionRepository;
import org.team2.UASAMS.Repos.ScholarshipRepository;

@Service
public class ScholarshipQuestionService {
    
    // Putting in two repositories since this service needs logic added for booth scholarship questions and scholarship 
    private final ScholarshipQuestionRepository questionRepo;
    private final ScholarshipRepository scholarshipRepo;

    public ScholarshipQuestionService(
        ScholarshipQuestionRepository questionRepo,
        ScholarshipRepository scholarshipRepo
    ) {
        this.questionRepo = questionRepo;
        this.scholarshipRepo = scholarshipRepo;
    }

    public List<ScholarshipQuestion> getQuestions(Long scholarshipId) {
        return questionRepo.findByScholarship_Id(scholarshipId);
    }

    @Transactional 
    public void addQuestion(
        Long scholarshipId,
        String questionText,
        boolean required,
        String inputType
    ) { // Retrieving an existing scholarship from database and creating an object of Scholarship
        Scholarship scholarship = scholarshipRepo.findById(scholarshipId)
            .orElseThrow(() ->
            new IllegalArgumentException("Scholarship not found") // For testing 
        );

        if (questionText == null || questionText.isBlank()) {
            throw new IllegalArgumentException("Question cannot be empty");
        }

        // Creating object question of ScholarshipQuestion
        ScholarshipQuestion question = new ScholarshipQuestion();


        // Uses setters to set info 
        question.setQuestionText(questionText);
        question.setRequired(required);
        question.setInputType(inputType);
        question.setScholarship(scholarship);

        questionRepo.save(question);
    }
}
