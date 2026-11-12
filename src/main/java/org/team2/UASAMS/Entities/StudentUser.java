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
public class StudentUser extends User{

    private int studentIdNum;
    private int year; //Freshman, Sophomore, Junior, Senior
    
    private float gpa;

    private LocalDateTime birthday;
    private LocalDateTime graduationDate;

    private List<String> majors = new ArrayList<>();
    private List<String> minors = new ArrayList<>();
    private List<String> demographics = new ArrayList<>();

    private String school;
}
