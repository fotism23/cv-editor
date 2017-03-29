package app.java.utils.exporters;

public interface Exporter {
    void exportName();
    void exportAddress();
    void exportHomePhone();
    void exportMobilePhone();
    void exportEmail();
    void exportWebsite();

    void exportProfessionalProfile();
    void exportSkillsAndExperience();
    void exportCareerSummary();
    void exportEducationAndTraining();
    void exportFurtherCourses();
    void exportAdditionalInfo();
    void exportInterests();

    /**
     * Chronological Only.
     */
    void exportCoreStrengths();
    void exportProfessionalExperience();
}
