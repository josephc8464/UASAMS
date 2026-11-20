package org.team2.UASAMS.Services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.team2.UASAMS.Entities.Scholarship;
import org.team2.UASAMS.Repos.ScholarshipRepository;



@Service // Tells Spring that this class provides application functionality
public class ScholarshipService {
    

    private final ScholarshipRepository scholarshipRepository; // Constructor receives a ScholarshipRepository object (see ScholarshipRepository under Repos)

    public ScholarshipService(
        ScholarshipRepository scholarshipRepository) {
            this.scholarshipRepository = scholarshipRepository;
        }

        // Receives scholarships whose status are "published"
    public List<Scholarship> getPublishedScholarships() {
        return scholarshipRepository.findByStatus("PUBLISHED");
    }
}
