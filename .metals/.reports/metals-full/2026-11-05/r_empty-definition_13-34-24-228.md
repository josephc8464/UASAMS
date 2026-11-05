error id: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Repos/StudentUserRepository.java:_empty_/StudentUser#
file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Repos/StudentUserRepository.java
empty definition using pc, found symbol in pc: _empty_/StudentUser#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 328
uri: file:///C:/Users/famil/.vscode/projects/UASAMS/src/main/java/org/team2/UASAMS/Repos/StudentUserRepository.java
text:
```scala
package org.team2.UASAMS.Repos;

import org.team2.UASAMS.Entities.StudentUser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentUserRepository extends JpaRepository<StudentUser, Long> {
    Optional<StudentUser> findByEmail(String email); 
    
    List<Student@@User> findBySchool(String school);
    List<StudentUser> findByGpaGreaterThanEqual(float gpa);
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/StudentUser#