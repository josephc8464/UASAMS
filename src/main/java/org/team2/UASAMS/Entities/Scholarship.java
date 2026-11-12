package org.team2.UASAMS.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Scholarship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private List<String> eligibilityCriteria = new ArrayList<>();
    private float awardAmount;
    private String providerContact;

    private LocalDateTime deadline;
    
    private String disbursementInstructions;
    private String status;
}
