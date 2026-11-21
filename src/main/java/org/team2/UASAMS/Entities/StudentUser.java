package org.team2.UASAMS.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student_users")
public class StudentUser extends User{

    //private int studentIdNum;
    private float gpa;

    private LocalDate birthday;
    private LocalDate graduationDate;

    private List<String> majors = new ArrayList<>();
    //private List<String> minors = new ArrayList<>();
    //private List<String> demographics = new ArrayList<>();

    private String school;

    @OneToMany(mappedBy = "studentUser", cascade = CascadeType.ALL)
    private List<Application> applications = new ArrayList<>();

    public List<String> getMajors() {
        if (this.majors == null) {
            this.majors = new ArrayList<>();
        }
        return this.majors;
    }

    public void addMajor(String major) {
        if (major != null && !major.isBlank()) {
            getMajors().add(major.trim());
        }
    }
}
