package org.team2.UASAMS.Entities.Enums;

public enum UserRole {
    ROLE_GUEST,
    ROLE_STUDENT, 
    ROLE_ADMIN, 
    ROLE_REVIEWER, 
    ROLE_PROVIDER, 
    ROLE_STEWARD, 
    ROLE_STAFF, 
    ROLE_SUPPORT;

    public static UserRole fromString(String roleStr) {
        return fromString(roleStr, ROLE_GUEST);
    }

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
}
