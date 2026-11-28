package org.team2.UASAMS.Services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team2.UASAMS.Entities.Scholarship;
import org.team2.UASAMS.Entities.ScholarshipQuestion;
import org.team2.UASAMS.Entities.Enums.ScholarshipQuestionInputType;
import org.team2.UASAMS.Entities.Enums.ScholarshipStatus;
import org.team2.UASAMS.Repos.ScholarshipQuestionRepository;
import org.team2.UASAMS.Repos.ScholarshipRepository;

@Service
public class ScholarshipQuestionService {

    private final ScholarshipQuestionRepository questionRepo;
    private final ScholarshipRepository scholarshipRepo;

    public ScholarshipQuestionService(
            ScholarshipQuestionRepository questionRepo,
            ScholarshipRepository scholarshipRepo) {
        this.questionRepo = questionRepo;
        this.scholarshipRepo = scholarshipRepo;
    }

    public List<ScholarshipQuestion> getQuestionsForScholarship(Long scholarshipId) {
        return questionRepo.findByScholarship_Id(scholarshipId);
    }

    // Alias kept in case any existing tests or services call getQuestions(...)
    public List<ScholarshipQuestion> getQuestions(Long scholarshipId) {
        return getQuestionsForScholarship(scholarshipId);
    }

    @Transactional
    public ScholarshipQuestion addQuestion(
            Long scholarshipId,
            String questionText,
            ScholarshipQuestionInputType inputType,
            boolean required) {
        Scholarship scholarship = scholarshipRepo.findById(scholarshipId)
                .orElseThrow(() -> new IllegalArgumentException("Scholarship not found"));

        if (questionText == null || questionText.isBlank()) {
            throw new IllegalArgumentException("Question cannot be empty");
        }

        ScholarshipQuestion question = new ScholarshipQuestion();
        question.setQuestionText(questionText.trim());
        question.setRequired(required);
        question.setInputType(inputType != null ? inputType : ScholarshipQuestionInputType.TEXT);
        question.setScholarship(scholarship);

        scholarship.addQuestion(question);

        return questionRepo.save(question);
    }

    // Overload kept so existing String-based callers/tests continue compiling
    @Transactional
    public void addQuestion(
            Long scholarshipId,
            String questionText,
            boolean required,
            String inputType) {
        addQuestion(
                scholarshipId,
                questionText,
                ScholarshipQuestionInputType.fromString(inputType),
                required);
    }

    @Transactional
    public void deleteQuestion(Long scholarshipId, Long questionId) {
        Scholarship scholarship = scholarshipRepo.findById(scholarshipId)
                .orElseThrow(() -> new IllegalArgumentException("Scholarship not found"));

        ScholarshipQuestion question = questionRepo.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));

        if (scholarship.getStatus() != ScholarshipStatus.DRAFT) {
            throw new IllegalStateException("Question cannot be deleted from Scholarhip in status " + scholarship.getStatus().toString());
        }

        if (question.getScholarship() == null || !question.getScholarship().getId().equals(scholarshipId)) {
            throw new IllegalArgumentException("Question does not belong to the specified scholarship");
        }

        questionRepo.delete(question);

    }
}