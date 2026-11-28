package org.team2.UASAMS.Entities.Enums;

/**
 * Represents the submission lifecycle state of a student's scholarship application within the UASAMS system.
 *
 * @author Team 2
 * @since 11/25/26
 */
public enum ApplicationStatus {

    /**
     * Application has been started and saved by the student, but not yet finalized for review.
     */
    DRAFT("Draft"),

    /**
     * Application has been completed and officially submitted for committee evaluation.
     */
    SUBMITTED("Submitted");

    /**
     * Formatted status label displayed in the UI.
     */
    private final String displayName;

    /**
     * Constructs an {@code ApplicationStatus} enum constant with its user-friendly label.
     *
     * @param displayName the formatted name of the status
     */
    ApplicationStatus(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Parses a string into an {@link ApplicationStatus}, defaulting to {@link #DRAFT} if invalid or blank.
     *
     * @param statusStr the raw status string
     * @return the matching {@link ApplicationStatus}, or {@link #DRAFT} if unrecognized
     */
    public static ApplicationStatus fromString(String statusStr) {
        return fromString(statusStr, DRAFT);
    }

    /**
     * Parses a string into an {@link ApplicationStatus}, matching either the enum constant name
     * or its formatted display name.
     *
     * @param statusStr     the raw status string to parse
     * @param defaultStatus the fallback {@link ApplicationStatus} returned if {@code statusStr} is null, blank, or invalid
     * @return the matching {@link ApplicationStatus}, or {@code defaultStatus} if unrecognized
     */
    public static ApplicationStatus fromString(String statusStr, ApplicationStatus defaultStatus) {
        if (statusStr == null || statusStr.isBlank()) {
            return defaultStatus;
        }

        String normalized = statusStr.trim();

        for (ApplicationStatus status : values()) {
            if (status.name().equalsIgnoreCase(normalized) || status.displayName.equalsIgnoreCase(normalized)) {
                return status;
            }
        }

        return defaultStatus;
    }

    /**
     * Returns the formatted display name of the application status.
     *
     * @return the human-readable status label
     */
    @Override
    public String toString() {
        return displayName;
    }
}