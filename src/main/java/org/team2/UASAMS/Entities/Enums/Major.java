package org.team2.UASAMS.Entities.Enums;

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

    private final String displayName;

    Major(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
