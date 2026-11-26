package org.team2.UASAMS.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.team2.UASAMS.Entities.Scholarship;
import org.team2.UASAMS.Entities.Enums.Major;
import org.team2.UASAMS.Entities.Enums.ScholarshipStatus;
import org.team2.UASAMS.Repos.ScholarshipRepository;

import jakarta.transaction.Transactional;



@Service // Tells Spring that this class provides application functionality
public class ScholarshipService {
    

        final ScholarshipRepository scholarshipRepository; // Constructor receives a ScholarshipRepository object (see ScholarshipRepository under Repos)

    public ScholarshipService(
        ScholarshipRepository scholarshipRepository) {
            this.scholarshipRepository = scholarshipRepository;
        }

        // Receives scholarships whose status are "published"
    public List<Scholarship> getOpenScholarships() {
        return scholarshipRepository.findByStatus(ScholarshipStatus.OPEN);
    }

    /**
     * Creates a scholarship draft. Name and deadline is all that is required.
     * 
     * @param name
     * @param description
     * @param awardAmount
     * @param providerContact
     * @param eligibleMajors
     * @param deadline
     * @param disbursementInstructions
     * @return none
    */
    @Transactional
    public Scholarship createScholarship(
            String name,
            String description,
            float awardAmount,
            String providerContact,
            List<Major> eligibleMajors,
            LocalDateTime deadline,
            String disbursementInstructions
            ) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Scholarship name cannot be null or empty.");
        }
        if (awardAmount <= 0) {
            throw new IllegalArgumentException("Award amount must be greater than zero.");
        }
        if (deadline == null || deadline.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Deadline must be a valid future date and time.");
        }

        Scholarship scholarship = new Scholarship();
        scholarship.setName(name.trim());
        scholarship.setDescription(description);
        scholarship.setAwardAmount(awardAmount);
        scholarship.setProviderContact(providerContact);
        scholarship.setEligibleMajors(eligibleMajors != null ? new ArrayList<>(eligibleMajors) : new ArrayList<>());
        scholarship.setDeadline(deadline);
        scholarship.setDisbursementInstructions(disbursementInstructions);
        
        scholarship.setStatus(ScholarshipStatus.DRAFT);

        return scholarshipRepository.save(scholarship);
    }
}
