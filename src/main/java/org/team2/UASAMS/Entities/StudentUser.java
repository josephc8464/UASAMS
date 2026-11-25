package org.team2.UASAMS.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.team2.UASAMS.Entities.Enums.Major;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student_users")
public class StudentUser extends User{

    private int studentIdNum;
    private float gpa;

    private LocalDate birthday;
    private LocalDate graduationDate;

    private Major major;

    @OneToMany(mappedBy = "studentUser", cascade = CascadeType.ALL)
    private List<Application> applications = new ArrayList<>();
}
