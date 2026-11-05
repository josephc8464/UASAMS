error id: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/User.java:_empty_/Collection#
file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/User.java
empty definition using pc, found symbol in pc: _empty_/Collection#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1055
uri: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/User.java
text:
```scala
package org.team2.UASAMS.Entities;

import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;
    
    private String role; // "STUDENT", "ADMIN", "REVIEWER", "PROVIDER", "STEWARD", "STAFF", "SUPPORT"
    private String password;

    @Override public String getUsername() { return this.email; }
    @Override public String getPassword() { return this.password; }
    
    @Override
    public @@Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/Collection#