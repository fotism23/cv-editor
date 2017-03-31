package app.java.utils.exporters;

import app.java.data.Node;
import javafx.scene.image.Image;

import java.util.HashMap;

public interface Exporter {
    void exportImage(Image image);

    void exportPersonalInfo(HashMap<String, String> hashMap);

    void exportProfessionalProfile(Node node);
    void exportSkillsAndExperience(Node node);
    void exportCareerSummary(Node node);
    void exportEducationAndTraining(Node node);
    void exportFurtherCourses(Node node);
    void exportAdditionalInfo(Node node);
    void exportInterests(Node node);

    /**
     * Chronological Only.
     */
    void exportCoreStrengths(Node node);
    void exportProfessionalExperience(Node node);

    void commit();
}
