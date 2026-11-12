package org.team2.UASAMS.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScholarshipQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;
    private boolean isRequired;
    private String inputType; //"TEXT, NUMBER, FILE, ENUM"
}
