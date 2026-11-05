error id: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Repos/UserRepository.java:java/lang/String#
file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Repos/UserRepository.java
empty definition using pc, found symbol in pc: java/lang/String#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 330
uri: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Repos/UserRepository.java
text:
```scala
package org.team2.UASAMS.Repos;

import org.team2.UASAMS.Entities.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(St@@ring username);
    Optional<User> findByEmail(String email);
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: java/lang/String#