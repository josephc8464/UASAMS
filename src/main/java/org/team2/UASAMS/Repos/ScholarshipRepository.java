package org.team2.UASAMS.Repos;

import org.team2.UASAMS.Entities.Scholarship;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScholarshipRepository extends JpaRepository<Scholarship, Long> {
    List<Scholarship> findByStatus(String status);
}