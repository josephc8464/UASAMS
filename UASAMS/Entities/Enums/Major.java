package org.team2.UASAMS.Entities.Enums;

/**
 * Represents the academic engineering and applied science majors supported in the UASAMS system.
 * <p>
 * Each constant stores a human-readable {@code displayName} used for rendering dropdowns and
 * profile labels in the user interface.
 * </p>
 *
 * @author Team 2
 * @since 11/25/26
 */
public enum Major {

    AEROSPACE_ENGINEERING("Aerospace Engineering"),
    ARCHITECTURAL_ENGINEERING("Architectural Engineering"),
    BIOMEDICAL_ENGINEERING("Biomedical Engineering"),
    BIOSYSTEMS_ENGINEERING("Biosystems Engineering"),
    CHEMICAL_ENGINEERING("Chemical Engineering"),
    CIVIL_ENGINEERING("Civil Engineering"),
    COMPUTER_SCIENCE_AND_ENGINEERING("Computer Science & Engineering"),
    ELECTRICAL_AND_COMPUTER_ENGINEERING("Electrical & Computer Engineering"),
    ENVIRONMENTAL_ENGINEERING("Environmental Engineering"),
    INDUSTRIAL_ENGINEERING("Industrial Engineering"),
    INTEGRATED_BUSINESS_AND_ENGINEERING("Integrated Business & Engineering"),
    INTELLIGENCE_AND_INFORMATION_OPERATIONS("Intelligence & Information Operations"),
    MATERIALS_SCIENCE_AND_ENGINEERING("Materials Science & Engineering"),
    MECHANICAL_ENGINEERING("Mechanical Engineering"),
    MINING_ENGINEERING("Mining Engineering"),
    OPTICAL_SCIENCES_AND_ENGINEERING("Optical Sciences & Engineering"),
    SOFTWARE_ENGINEERING("Software Engineering"),
    SYSTEMS_ENGINEERING("Systems Engineering");

    /**
     * Formatted major title displayed in the UI.
     */
    private final String displayName;

    /**
     * Constructs a {@code Major} enum constant with its user-friendly label.
     *
     * @param displayName the formatted name of the major
     */
    Major(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Parses a string into a {@link Major}, returning {@code null} if invalid or blank.
     *
     * @param majorStr the raw major name or display title
     * @return the matching {@link Major}, or {@code null} if unrecognized
     */
    public static Major fromString(String majorStr) {
        return fromString(majorStr, null);
    }

    /**
     * Parses a string into a {@link Major}, matching either the enum constant name
     * (e.g., {@code "SOFTWARE_ENGINEERING"}) or its formatted display name
     * (e.g., {@code "Software Engineering"}).
     *
     * @param majorStr     the raw major string to parse
     * @param defaultMajor the fallback {@link Major} returned if {@code majorStr} is null, blank, or invalid
     * @return the matching {@link Major}, or {@code defaultMajor} if unrecognized
     */
    public static Major fromString(String majorStr, Major defaultMajor) {
        if (majorStr == null || majorStr.isBlank()) {
            return defaultMajor;
        }

        String normalized = majorStr.trim();

        for (Major major : values()) {
            if (major.name().equalsIgnoreCase(normalized) || major.displayName.equalsIgnoreCase(normalized)) {
                return major;
            }
        }

        return defaultMajor;
    }

    /**
     * Returns the formatted display name of the major.
     *
     * @return the human-readable major title
     */
    @Override
    public String toString() {
        return displayName;
    }
}