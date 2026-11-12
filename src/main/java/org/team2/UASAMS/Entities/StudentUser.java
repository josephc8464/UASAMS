package org.team2.UASAMS.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student_users")
public class StudentUser extends User{

    private int studentIdNum;
    private int academic_year;
    
    @Column(precision = 3, scale = 2) //Limits to 3 digits, two after decimal point
    private float gpa;

    private LocalDateTime birthday;
    private LocalDateTime graduationDate;

    private List<String> majors = new ArrayList<>();
    private List<String> minors = new ArrayList<>();
    //TODO: FIX ME private List<String> demographics = new ArrayList<>();

    private String school;
}
