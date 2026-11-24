package org.team2.UASAMS.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scholarship_id", nullable = false)
    private Scholarship scholarship;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<ApplicationAnswer> answers = new ArrayList<>();
}
