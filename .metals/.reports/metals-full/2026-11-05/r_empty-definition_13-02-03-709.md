error id: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/Staff.java:org/team2/UASAMS/Entities/Staff#`<init>`().
file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/Staff.java
empty definition using pc, found symbol in pc: 
found definition using semanticdb; symbol org/team2/UASAMS/Entities/Staff#`<init>`().
empty definition using fallback
non-local guesses:

offset: 272
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
@NoArgsConstructor
public class Staff@@ extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 