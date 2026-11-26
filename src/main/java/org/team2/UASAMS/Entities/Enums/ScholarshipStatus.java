package org.team2.UASAMS.Entities.Enums;

/**
 * Represents the visibility and availability lifecycle state of a scholarship within the UASAMS system.
 *
 * @author Team 2
 * @since 11/25/26
 */
public enum ScholarshipStatus {

    /**
     * Published and actively accepting student applications.
     */
    OPEN("Open"),

    /**
     * No longer accepting new applications (deadline passed or filled).
     */
    CLOSED("Closed"),

    /**
     * Created by an administrator or provider, but not yet visible to students.
     */
    DRAFT("Draft"),

    /**
     * Retired scholarship retained for historical records and reporting.
     */
    ARCHIVED("Archived");

    /**
     * Formatted scholarship status label displayed in the UI.
     */
    private final String displayName;

    /**
     * Constructs a {@code ScholarshipStatus} enum constant with its user-friendly label.
     *
     * @param displayName the formatted name of the scholarship status
     */
    ScholarshipStatus(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Parses a string into a {@link ScholarshipStatus}, defaulting to {@link #DRAFT} if invalid or blank.
     *
     * @param statusStr the raw scholarship status string
     * @return the matching {@link ScholarshipStatus}, or {@link #DRAFT} if unrecognized
     */
    public static ScholarshipStatus fromString(String statusStr) {
        return fromString(statusStr, DRAFT);
    }

    /**
     * Parses a string into a {@link ScholarshipStatus}, matching either the enum constant name
     * or its formatted display name.
     *
     * @param statusStr     the raw scholarship status string to parse
     * @param defaultStatus the fallback {@link ScholarshipStatus} returned if {@code statusStr} is null, blank, or invalid
     * @return the matching {@link ScholarshipStatus}, or {@code defaultStatus} if unrecognized
     */
    public static ScholarshipStatus fromString(String statusStr, ScholarshipStatus defaultStatus) {
        if (statusStr == null || statusStr.isBlank()) {
            return defaultStatus;
        }

        String normalized = statusStr.trim();

        for (ScholarshipStatus status : values()) {
            if (status.name().equalsIgnoreCase(normalized) || status.displayName.equalsIgnoreCase(normalized)) {
                return status;
            }
        }

        return defaultStatus;
    }

    /**
     * Returns the formatted display name of the scholarship status.
     *
     * @return the human-readable status label
     */
    @Override
    public String toString() {
        return displayName;
    }
}