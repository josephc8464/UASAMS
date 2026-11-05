error id: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/StudentUser.java:java/util/List#
file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/StudentUser.java
empty definition using pc, found symbol in pc: java/util/List#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 572
uri: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/StudentUser.java
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
@AllArgsConstructor
@NoArgsConstructor
public class StudentUser extends User{

    private int studentIdNum;
    private int year; //Freshman, Sophomore, Junior, Senior
    
    private float gpa;

    private LocalDateTime birthday;
    private LocalDateTime graduationDate;

    private Li@@st<String> majors = new ArrayList<>();
    private List<String> minors = new ArrayList<>();

    private String school;
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: java/util/List#