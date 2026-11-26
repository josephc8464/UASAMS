package org.team2.UASAMS.Entities.Enums;

/**
 * Represents the decision and payout status of a scholarship award selection within the UASAMS system.
 *
 * @author Team 2
 * @since 11/25/26
 */
public enum AwardSelectionStatus {

    /**
     * Applicant has been chosen by the committee to receive the scholarship award.
     */
    SELECTED("Selected"),

    /**
     * Student has officially accepted the offered scholarship award.
     */
    ACCEPTED("Accepted"),

    /**
     * Student has turned down the offered scholarship award.
     */
    DECLINED("Declined"),

    /**
     * Scholarship funds have been distributed to the recipient.
     */
    DISBURSED("Disbursed");

    /**
     * Formatted award status label displayed in the UI.
     */
    private final String displayName;

    /**
     * Constructs an {@code AwardSelectionStatus} enum constant with its user-friendly label.
     *
     * @param displayName the formatted name of the award status
     */
    AwardSelectionStatus(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Parses a string into an {@link AwardSelectionStatus}, defaulting to {@link #SELECTED} if invalid or blank.
     *
     * @param statusStr the raw award status string
     * @return the matching {@link AwardSelectionStatus}, or {@link #SELECTED} if unrecognized
     */
    public static AwardSelectionStatus fromString(String statusStr) {
        return fromString(statusStr, SELECTED);
    }

    /**
     * Parses a string into an {@link AwardSelectionStatus}, matching either the enum constant name
     * or its formatted display name.
     *
     * @param statusStr     the raw award status string to parse
     * @param defaultStatus the fallback {@link AwardSelectionStatus} returned if {@code statusStr} is null, blank, or invalid
     * @return the matching {@link AwardSelectionStatus}, or {@code defaultStatus} if unrecognized
     */
    public static AwardSelectionStatus fromString(String statusStr, AwardSelectionStatus defaultStatus) {
        if (statusStr == null || statusStr.isBlank()) {
            return defaultStatus;
        }

        String normalized = statusStr.trim();

        for (AwardSelectionStatus status : values()) {
            if (status.name().equalsIgnoreCase(normalized) || status.displayName.equalsIgnoreCase(normalized)) {
                return status;
            }
        }

        return defaultStatus;
    }

    /**
     * Returns the formatted display name of the award selection status.
     *
     * @return the human-readable award status label
     */
    @Override
    public String toString() {
        return displayName;
    }
}