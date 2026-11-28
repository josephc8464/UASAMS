package org.team2.UASAMS.Repos;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team2.UASAMS.Entities.Application;
import org.team2.UASAMS.Entities.ApplicationDocument;

/**
 * Data access repository for managing {@link ApplicationDocument} file metadata.
 * <p>
 * Supports looking up uploaded files associated with a specific application or upload timestamp.
 * </p>
 *
 * @author Team 2
 * @since 11/25/26
 * @see ApplicationDocument
 */
public interface ApplicationDocumentRepository extends JpaRepository<ApplicationDocument, Long> {
    List<ApplicationDocument> findByUploadTime(LocalDateTime uploadTime);
    List<ApplicationDocument> findByApplication(Application application);
}