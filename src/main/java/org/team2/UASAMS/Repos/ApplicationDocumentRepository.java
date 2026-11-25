package org.team2.UASAMS.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team2.UASAMS.Entities.ApplicationDocument;
import java.util.List;
import java.time.LocalDateTime;
import org.team2.UASAMS.Entities.Application;



public interface ApplicationDocumentRepository extends JpaRepository<ApplicationDocument, Long>{
    List<ApplicationDocument> findByUploadTime(LocalDateTime uploadTime);
    List<ApplicationDocument> findByApplication(Application application);
}
