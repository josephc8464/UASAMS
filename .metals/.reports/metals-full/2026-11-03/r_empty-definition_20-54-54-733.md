error id: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/Student.java:_empty_/List#
file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/Student.java
empty definition using pc, found symbol in pc: _empty_/List#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 508
uri: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/Student.java
text:
```scala
package org.team2.UASAMS.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Lis@@t<String> minors;
    

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/List#