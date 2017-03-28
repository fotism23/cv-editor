package app.java.utils.parsers;


import app.java.data.Node;

public interface Parser {
    int parseTemplate();

    String parseName();
    String parseAddress();
    String parseHomePhone();
    String parseMobilePhone();
    String parseEmail();
    String parseWebsite();

    Node parseProfessionalProfile();
    Node parseSkillsAndExperience();
    Node parseCareerSummary();
    Node parseEducationAndTraining();
    Node parseFurtherCourses();
    Node parseAdditionalInfo();
    Node parseInterests();

    /**
     * Chronological Only.
     */
    Node parseCoreStrengths();
    Node parseProfessionalExperience();
}
