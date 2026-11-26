package org.team2.UASAMS.Entities.Enums;

/**
 * Defines the Spring Security authorization roles available to user accounts in the UASAMS system.
 * <p>
 * All role names follow Spring Security's {@code ROLE_} prefix convention so they map directly
 * to {@code GrantedAuthority} checks.
 * </p>
 *
 * @author Team 2
 * @since 11/25/26
 */
public enum UserRole {

    /**
     * Unauthenticated or limited-access visitor role.
     */
    ROLE_GUEST("Guest"),

    /**
     * Student applicant able to browse scholarships and submit applications.
     */
    ROLE_STUDENT("Student"),

    /**
     * System administrator with full management and configuration access.
     */
    ROLE_ADMIN("Admin"),

    /**
     * Committee member permitted to score and review submitted applications.
     */
    ROLE_REVIEWER("Reviewer"),

    /**
     * External or departmental sponsor managing scholarship funds and criteria.
     */
    ROLE_PROVIDER("Provider"),

    /**
     * Oversees fund stewardship, compliance, and award disbursement tracking.
     */
    ROLE_STEWARD("Steward"),

    /**
     * Internal university staff member assisting with scholarship operations.
     */
    ROLE_STAFF("Staff"),

    /**
     * Technical or user support role for troubleshooting account and application issues.
     */
    ROLE_SUPPORT("Support");

    /**
     * Formatted role title displayed in the UI without the {@code ROLE_} prefix.
     */
    private final String displayName;

    /**
     * Constructs a {@code UserRole} enum constant with its user-friendly label.
     *
     * @param displayName the formatted name of the role
     */
    UserRole(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Parses a role string into a {@link UserRole}, defaulting to {@link #ROLE_GUEST} if invalid or blank.
     *
     * @param roleStr the raw role name (with or without the {@code ROLE_} prefix)
     * @return the matching {@link UserRole}, or {@link #ROLE_GUEST} if unrecognized
     */
    public static UserRole fromString(String roleStr) {
        return fromString(roleStr, ROLE_GUEST);
    }

    /**
     * Parses a role string into a {@link UserRole}, normalizing case and automatically prepending
     * {@code ROLE_} if omitted.
     *
     * @param roleStr     the raw role name to parse
     * @param defaultRole the fallback {@link UserRole} returned if {@code roleStr} is null, blank, or invalid
     * @return the matching {@link UserRole}, or {@code defaultRole} if unrecognized
     */
    public static UserRole fromString(String roleStr, UserRole defaultRole) {
        if (roleStr == null || roleStr.isBlank()) {
            return defaultRole;
        }

        String normalized = roleStr.trim().toUpperCase();

        if (!normalized.startsWith("ROLE_")) {
            normalized = "ROLE_" + normalized;
        }

        try {
            return UserRole.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            return defaultRole;
        }
    }

    /**
     * Returns the formatted display name of the role (e.g., {@code "Student"} instead of {@code "ROLE_STUDENT"}).
     *
     * @return the human-readable role label
     */
    @Override
    public String toString() {
        return displayName;
    }
}