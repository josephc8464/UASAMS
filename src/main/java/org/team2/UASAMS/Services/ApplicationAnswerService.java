package org.team2.UASAMS.Services;

import org.springframework.stereotype.Service;
import org.team2.UASAMS.Entities.Application;
import org.team2.UASAMS.Entities.ApplicationAnswer;
import org.team2.UASAMS.Entities.ScholarshipQuestion;
import org.team2.UASAMS.Entities.Enums.ApplicationStatus;
import org.team2.UASAMS.Entities.Enums.ScholarshipQuestionInputType;
import org.team2.UASAMS.Repos.ApplicationAnswerRepository;

import jakarta.transaction.Transactional;

/**
 * Service layer for creating and updating student {@link ApplicationAnswer} responses
 * to custom scholarship application questions within the UASAMS system.
 *
 * @author Team 2
 * @since 11/25/26
 * @see ApplicationAnswer
 * @see ApplicationAnswerRepository
 */
@Service 
public class ApplicationAnswerService {

    private final ApplicationAnswerRepository appAnsRepo;

    /**
     * Constructs an {@code ApplicationAnswerService} with the required answer repository.
     *
     * @param applicationAnswerRepository repository for persisting {@link ApplicationAnswer} records
     */
    public ApplicationAnswerService(ApplicationAnswerRepository applicationAnswerRepository) {
        this.appAnsRepo = applicationAnswerRepository;
    }

    /**
     * Validates and saves (or updates) a student's answer to a specific {@link ScholarshipQuestion}
     * on a draft {@link Application}.
     * <p>
     * Supports partial draft saving by allowing blank values and updating an existing answer record
     * if one already exists for the given application and question.
     * </p>
     *
     * @param answerValue         the student's submitted response value (may be blank while in draft)
     * @param app                 the target {@link Application} being answered
     * @param scholarshipQuestion the {@link ScholarshipQuestion} being responded to
     * @return the persisted or updated {@link ApplicationAnswer} entity
     * @throws IllegalArgumentException if required references are null, the question belongs to a different
     *                                  scholarship, or a non-blank value fails input-type validation
     * @throws IllegalStateException    if the application has already been submitted and locked
     */
    @Transactional 
    public ApplicationAnswer createApplicationAnswer(String answerValue, Application app, ScholarshipQuestion scholarshipQuestion) {
        if(answerValue == null || answerValue.isBlank()){
            throw new IllegalArgumentException("Answer value cannot be saved as blank");
        }
        
        if (app == null || scholarshipQuestion == null) {
            throw new IllegalArgumentException("Application and ScholarshipQuestion must not be null");
        }

        // Prevent editing an already-submitted application
        if (app.getStatus() == ApplicationStatus.SUBMITTED) {
            throw new IllegalStateException("Cannot add or modify answers on a submitted application");
        }

        // Ensure the question actually belongs to the scholarship being applied for
        if (!scholarshipQuestion.getScholarship().getId().equals(app.getScholarship().getId())) {
            throw new IllegalArgumentException("Question does not belong to the application's scholarship");
        }

        // Validate answer format against ScholarshipQuestionInputType when a value is provided
        boolean isBlank = (answerValue == null || answerValue.isBlank());
        if (!isBlank && scholarshipQuestion.getInputType() == ScholarshipQuestionInputType.NUMBER) {
            try {
                Double.parseDouble(answerValue.trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Answer must be a valid number");
            }
        }

        // Reuse existing answer if the student is updating a draft response; otherwise create a new one
        ApplicationAnswer appAns = appAnsRepo.findByApplicationAndQuestion(app, scholarshipQuestion)
                .orElseGet(() -> {
                    ApplicationAnswer newAnswer = new ApplicationAnswer();
                    newAnswer.setQuestion(scholarshipQuestion);
                    newAnswer.setApplication(app);
                    return newAnswer;
                });

        appAns.setAnswerValue(!isBlank ? answerValue.trim() : null);

        return appAnsRepo.save(appAns);
    }
}