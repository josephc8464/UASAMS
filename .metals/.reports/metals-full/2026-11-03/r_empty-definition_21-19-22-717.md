error id: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/User.java:_empty_/UserDetails#
file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/User.java
empty definition using pc, found symbol in pc: _empty_/UserDetails#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 355
uri: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/User.java
text:
```scala
package org.team2.UASAMS.Entities;

import org.springframework.security.core.userdetails.UserDetails;

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
public class User implements Use@@rDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;
    
    private String role; // "STUDENT", "ADMIN", "REVIEWER", "PROVIDER", "STEWARD", "STAFF", "SUPPORT"
    private String password;
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/UserDetails#