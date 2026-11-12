error id: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/StudentUser.java:lombok/NoArgsConstructor#
file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/StudentUser.java
empty definition using pc, found symbol in pc: lombok/NoArgsConstructor#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 129
uri: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/StudentUser.java
text:
```scala
package org.team2.UASAMS.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.Getter;
import lombok.NoArg@@sConstructor;
import lombok.Setter;

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

```


#### Short summary: 

empty definition using pc, found symbol in pc: lombok/NoArgsConstructor#