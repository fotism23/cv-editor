package app.java.utils.exporters;

import app.java.data.Node;
import app.java.utils.ApplicationUtils;
import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;
import de.nixosoft.jlr.JLROpener;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class LatexExporter implements Exporter {

    private File outFile;
    private File template;
    private File workingDirectory;
    private JLRGenerator generator;
    private JLRConverter converter;
    private HashMap<String, String> personalInfo;

    public LatexExporter(String path) {
        this.outFile = new File(path);
        this.template = new File(ApplicationUtils.TEX_TEMPLATE_PATH);
        this.personalInfo = new HashMap<>();
        this.workingDirectory = new File(ApplicationUtils.TEMPLATE_DIRECTORY_PATH);
        converter = new JLRConverter(workingDirectory);
    }

    @Override
    public void exportImage(Image image) {
        converter.replace("Image", image);
    }

    @Override
    public void exportPersonalInfo(HashMap<String, String> hashMap) {
        converter.replace("Name", hashMap.get(ApplicationUtils.PERSONAL_INFO_NAME));
        converter.replace("Address", hashMap.get(ApplicationUtils.PERSONAL_INFO_ADDRESS));
        converter.replace("Phone", hashMap.get(ApplicationUtils.PERSONAL_INFO_HOME));
        converter.replace("Mobile", hashMap.get(ApplicationUtils.PERSONAL_INFO_MOBILE));
        converter.replace("Email", hashMap.get(ApplicationUtils.PERSONAL_INFO_EMAIL));
        converter.replace("Website", hashMap.get(ApplicationUtils.PERSONAL_INFO_WEBSITE));
    }

    @Override
    public void exportProfessionalProfile(Node node) {

    }

    @Override
    public void exportSkillsAndExperience(Node node) {

    }

    @Override
    public void exportCareerSummary(Node node) {

    }

    @Override
    public void exportEducationAndTraining(Node node) {

    }

    @Override
    public void exportFurtherCourses(Node node) {

    }

    @Override
    public void exportAdditionalInfo(Node node) {

    }

    @Override
    public void exportInterests(Node node) {

    }

    @Override
    public void exportCoreStrengths(Node node) {

    }

    @Override
    public void exportProfessionalExperience(Node node) {

    }

    @Override
    public void commit() {
        try {
            converter.parse(template, outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}