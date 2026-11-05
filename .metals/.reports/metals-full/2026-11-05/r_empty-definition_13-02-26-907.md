error id: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/Staff.java:_empty_/GeneratedValue#strategy#
file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/Staff.java
empty definition using pc, found symbol in pc: _empty_/GeneratedValue#strategy#
found definition using semanticdb; symbol org/team2/UASAMS/Entities/Staff#lastName.
empty definition using fallback
non-local guesses:

offset: 309
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
public class Staff {

    @Id
    @GeneratedValue(st@@rategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/GeneratedValue#strategy#