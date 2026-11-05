error id: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/Student.java:_empty_/AllArgsConstructor#
file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/Student.java
empty definition using pc, found symbol in pc: _empty_/AllArgsConstructor#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 278
uri: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/Student.java
text:
```scala
package org.team2.UASAMS.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsC@@onstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int studentIdNum;

    private String firstName;
    private String lastName;
    
    private LocalDateTime birthday;

    private List<String> majors = new ArrayList<>();
    private List<String> minors = new ArrayList<>();
    private String school;
    private int year;
    private LocalDateTime graduationDate;
    private float gpa;
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/AllArgsConstructor#