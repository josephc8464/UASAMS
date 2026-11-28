package org.team2.UASAMS.Services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.team2.UASAMS.Entities.Application;
import org.team2.UASAMS.Entities.ApplicationDocument;
import org.team2.UASAMS.Entities.Enums.ApplicationStatus;
import org.team2.UASAMS.Repos.ApplicationDocumentRepository;

import jakarta.transaction.Transactional;

/**
 * Service layer for recording and managing uploaded {@link ApplicationDocument} attachments
 * within the UASAMS system.
 *
 * @author Team 2
 * @since 11/25/26
 * @see ApplicationDocument
 * @see ApplicationDocumentRepository
 */
@Service
public class ApplicationDocumentService {

    private final ApplicationDocumentRepository documentRepository;

    /**
     * Constructs an {@code ApplicationDocumentService} with the required document repository.
     *
     * @param documentRepository repository for persisting {@link ApplicationDocument} metadata
     */
    public ApplicationDocumentService(ApplicationDocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    /**
     * Validates and saves metadata for a document uploaded to a draft {@link Application}.
     *
     * @param fileName    original display name of the uploaded file
     * @param filePath    storage path or object key where the file resides
     * @param contentType MIME type of the file (e.g., {@code "application/pdf"})
     * @param application the target {@link Application} this document belongs to
     * @return the persisted {@link ApplicationDocument} entity
     * @throws IllegalArgumentException if required metadata fields or application are null/blank
     * @throws IllegalStateException    if the application has already been submitted and locked
     */
    @Transactional
    public ApplicationDocument createApplicationDocument(
            String fileName,
            String filePath,
            String contentType,
            Application application) {

        if (application == null) {
            throw new IllegalArgumentException("Target application must not be null");
        }
        if (application.getStatus() == ApplicationStatus.SUBMITTED) {
            throw new IllegalStateException("Cannot upload documents to an already-submitted application");
        }
        if (fileName == null || fileName.isBlank() || filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File name and storage path are required");
        }

        ApplicationDocument doc = new ApplicationDocument();
        doc.setFileName(fileName.trim());
        doc.setFilePath(filePath.trim());
        doc.setContentType(contentType);
        doc.setUploadTime(LocalDateTime.now());
        doc.setApplication(application);

        return documentRepository.save(doc);
    }
}