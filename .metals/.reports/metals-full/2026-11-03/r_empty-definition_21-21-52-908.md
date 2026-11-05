error id: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/User.java:java/lang/String#
file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/User.java
empty definition using pc, found symbol in pc: java/lang/String#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1073
uri: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Entities/User.java
text:
```scala
package org.team2.UASAMS.Entities;

import java.util.Collection;
import java.util.Set;
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

    private Set<UserRole> roles;
    
    @Override public String getUsername() { return this.email; }
    @Override public Strin@@g getPassword() { return this.password; }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: java/lang/String#