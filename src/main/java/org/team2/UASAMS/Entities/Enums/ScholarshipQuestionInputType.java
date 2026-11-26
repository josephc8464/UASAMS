package org.team2.UASAMS.Entities.Enums;

/**
 * Defines the expected response format for a custom scholarship application question.
 *
 * @author Team 2
 * @since 11/25/26
 */
public enum ScholarshipQuestionInputType {

    /**
     * Free-form short or long text response.
     */
    TEXT("Text"),

    /**
     * Numeric value response (e.g., GPA, credit hours, or income).
     */
    NUMBER("Number"),

    /**
     * Uploaded file attachment (e.g., PDF transcript or essay).
     */
    FILE("File"),

    /**
     * Predefined choice selected from a fixed set of options.
     */
    ENUM("Multiple Choice");

    /**
     * Formatted input type label displayed in the UI.
     */
    private final String displayName;

    /**
     * Constructs a {@code ScholarshipQuestionInputType} enum constant with its user-friendly label.
     *
     * @param displayName the formatted name of the input type
     */
    ScholarshipQuestionInputType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Parses a string into a {@link ScholarshipQuestionInputType}, defaulting to {@link #TEXT} if invalid or blank.
     *
     * @param typeStr the raw input type string
     * @return the matching {@link ScholarshipQuestionInputType}, or {@link #TEXT} if unrecognized
     */
    public static ScholarshipQuestionInputType fromString(String typeStr) {
        return fromString(typeStr, TEXT);
    }

    /**
     * Parses a string into a {@link ScholarshipQuestionInputType}, matching either the enum constant name
     * or its formatted display name.
     *
     * @param typeStr     the raw input type string to parse
     * @param defaultType the fallback {@link ScholarshipQuestionInputType} returned if {@code typeStr} is null, blank, or invalid
     * @return the matching {@link ScholarshipQuestionInputType}, or {@code defaultType} if unrecognized
     */
    public static ScholarshipQuestionInputType fromString(String typeStr, ScholarshipQuestionInputType defaultType) {
        if (typeStr == null || typeStr.isBlank()) {
            return defaultType;
        }

        String normalized = typeStr.trim();

        for (ScholarshipQuestionInputType type : values()) {
            if (type.name().equalsIgnoreCase(normalized) || type.displayName.equalsIgnoreCase(normalized)) {
                return type;
            }
        }

        return defaultType;
    }

    /**
     * Returns the formatted display name of the input type.
     *
     * @return the human-readable input type label
     */
    @Override
    public String toString() {
        return displayName;
    }
}