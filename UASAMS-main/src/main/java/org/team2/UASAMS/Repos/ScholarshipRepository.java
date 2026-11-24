package org.team2.UASAMS.Repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.team2.UASAMS.Entities.Scholarship;

// Spring Data provides methods such as findAll(), findById(), save(), and deleteById()
public interface ScholarshipRepository 
    extends JpaRepository<Scholarship, Long> {
        List<Scholarship> findByStatus(String status);
    }

