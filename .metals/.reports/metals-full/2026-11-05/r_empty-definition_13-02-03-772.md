error id: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/Staff.java:_empty_/NoArgsConstructor#
file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/Staff.java
empty definition using pc, found symbol in pc: _empty_/NoArgsConstructor#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 250
uri: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/Staff.java
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
@NoArgsConstruct@@or
public class Staff extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/NoArgsConstructor#