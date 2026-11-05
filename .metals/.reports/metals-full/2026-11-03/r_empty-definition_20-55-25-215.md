error id: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/Student.java:java/util/List#
file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/Student.java
empty definition using pc, found symbol in pc: java/util/List#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 229
uri: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/Student.java
text:
```scala
package org.team2.UASAMS.Entities;

import jakarta.persistence.*;
import java.awt.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Lis@@t;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int studentIdNum;

    private String firstName;
    private String lastName;

    private String major;
    private List<String> minors;
    

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: java/util/List#