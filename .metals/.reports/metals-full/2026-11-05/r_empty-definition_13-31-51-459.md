error id: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Repos/UserRepository.java:_empty_/Profile#
file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Repos/UserRepository.java
empty definition using pc, found symbol in pc: _empty_/Profile#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 390
uri: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Repos/UserRepository.java
text:
```scala
package org.team2.UASAMS.Repos;

import org.team2.UASAMS.Entities.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<Profile> findByUsername(String username);
    Optional<Pr@@ofile> findByEmail(String email);
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/Profile#