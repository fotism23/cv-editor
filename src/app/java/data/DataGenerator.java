package app.java.data;

import app.java.utils.ApplicationUtils;
import app.java.utils.exporters.Exporter;
import app.java.utils.exporters.LatexExporter;
import app.java.utils.exporters.XmlExporter;
import app.java.utils.parsers.Parser;
import app.java.utils.parsers.XmlParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import javax.activation.UnsupportedDataTypeException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataGenerator {
    private ArrayList<Node> data;
    private HashMap<String, String> personalInfo;
    private int template;
    private Image profImage;

    public DataGenerator() {
        personalInfo = new HashMap<>();
        data = new ArrayList<>();
    }

    public void importData(String path, int parseType) throws UnsupportedDataTypeException {
        if (parseType == ApplicationUtils.XML_TYPE_ID){
            generateData(new XmlParser(path));
        }
        else
            throw new UnsupportedDataTypeException(parseType + " data type is not supported.");
    }

    public void exportData(String path, int exportType) throws UnsupportedDataTypeException {
        if (exportType == ApplicationUtils.XML_TYPE_ID) {
            exportXml(path);
        } else if (exportType == ApplicationUtils.LATEX_TYPE_ID) {
            exportLatex(path);
        } else
            throw new UnsupportedDataTypeException(exportType + " data type is not supported.");
    }

    public void generateData(Parser parser) {
        retrieveTemplate(parser);
        generatePersonalInfoNode(parser);
        generateAdditionalData(parser);
    }

    public void retrieveTemplate(Parser parser) {
        template = parser.parseTemplate();
    }

    private void generatePersonalInfoNode(Parser parser) {
        personalInfo.put(ApplicationUtils.PERSONAL_INFO_NAME, parser.parseName());
        personalInfo.put(ApplicationUtils.PERSONAL_INFO_ADDRESS, parser.parseAddress());
        personalInfo.put(ApplicationUtils.PERSONAL_INFO_HOME, parser.parseHomePhone());
        personalInfo.put(ApplicationUtils.PERSONAL_INFO_MOBILE, parser.parseMobilePhone());
        personalInfo.put(ApplicationUtils.PERSONAL_INFO_EMAIL, parser.parseEmail());
        personalInfo.put(ApplicationUtils.PERSONAL_INFO_WEBSITE, parser.parseWebsite());
    }

    private void generateAdditionalData(Parser parser) {
        if (template == ApplicationUtils.FUNCTIONAL_TEMPLATE_ID)
            generateFunctional(parser);
        else if (template == ApplicationUtils.CHRONOLOGICAL_TEMPLATE_ID)
            generateChronological(parser);
        else if (template == ApplicationUtils.COMBINED_TEMPLATE_ID)
            generateCombined(parser);
        else
            throw new UnsupportedOperationException("Invalid template ID.");
    }

    public String queryPersonalInfo(String key) throws InvalidKeyException {
        if (personalInfo.containsKey(key))
            return personalInfo.get(key);
        throw new InvalidKeyException(key + " key not found.");
    }

    private void generateCombined(Parser parser) {
        data.add(parser.parseProfessionalProfile());
        data.add(parser.parseSkillsAndExperience());
        data.add(parser.parseProfessionalExperience());
        data.add(parser.parseEducationAndTraining());
        data.add(parser.parseFurtherCourses());
        data.add(parser.parseAdditionalInfo());
        data.add(parser.parseInterests());
    }

    private void generateChronological(Parser parser) {
        data.add(parser.parseProfessionalProfile());
        data.add(parser.parseCoreStrengths());
        data.add(parser.parseProfessionalExperience());
        data.add(parser.parseEducationAndTraining());
        data.add(parser.parseFurtherCourses());
        data.add(parser.parseAdditionalInfo());
        data.add(parser.parseInterests());
    }

    private void generateFunctional(Parser parser) {
        setProfImage(parser.parseImage());
        data.add(parser.parseProfessionalProfile());
        data.add(parser.parseSkillsAndExperience());
        data.add(parser.parseCareerSummary());
        data.add(parser.parseEducationAndTraining());
        data.add(parser.parseFurtherCourses());
        data.add(parser.parseAdditionalInfo());
        data.add(parser.parseInterests());
    }

    private void exportXml(String path) {
        if (template == ApplicationUtils.FUNCTIONAL_TEMPLATE_ID)
            exportFunctional(new XmlExporter(path, template));
        else if (template == ApplicationUtils.CHRONOLOGICAL_TEMPLATE_ID)
            exportChronological(new XmlExporter(path, template));
        else if (template == ApplicationUtils.COMBINED_TEMPLATE_ID)
            exportCombined(new XmlExporter(path, template));
        else
            throw new UnsupportedOperationException("Invalid template ID.");
    }

    private void exportLatex(String exportTarget) {
        if (template == ApplicationUtils.FUNCTIONAL_TEMPLATE_ID)
            exportFunctional(new LatexExporter(exportTarget));
        else if (template == ApplicationUtils.CHRONOLOGICAL_TEMPLATE_ID)
            exportChronological(new LatexExporter(exportTarget));
        else if (template == ApplicationUtils.COMBINED_TEMPLATE_ID)
            exportCombined(new LatexExporter(exportTarget));
        else
            throw new UnsupportedOperationException("Invalid template ID.");
    }

    private void exportFunctional(Exporter exporter) {
        exporter.exportImage(profImage);
        exporter.exportPersonalInfo(personalInfo);
        exporter.exportProfessionalProfile(null);
        exporter.exportSkillsAndExperience(null);
        exporter.exportCareerSummary(null);
        exporter.exportEducationAndTraining(null);
        exporter.exportFurtherCourses(null);
        exporter.exportAdditionalInfo(null);
        exporter.exportInterests(null);
        exporter.commit();
    }

    private void exportChronological(Exporter exporter) {
        exporter.exportImage(profImage);
        exporter.exportPersonalInfo(personalInfo);
        exporter.exportProfessionalProfile(null);
        exporter.exportCoreStrengths(null);
        exporter.exportProfessionalExperience(null);
        exporter.exportEducationAndTraining(null);
        exporter.exportFurtherCourses(null);
        exporter.exportAdditionalInfo(null);
        exporter.exportInterests(null);
        exporter.commit();
    }

    private void exportCombined(Exporter exporter) {
        exporter.exportImage(profImage);
        exporter.exportPersonalInfo(personalInfo);
        exporter.exportProfessionalProfile(null);
        exporter.exportSkillsAndExperience(null);
        exporter.exportProfessionalExperience(null);
        exporter.exportEducationAndTraining(null);
        exporter.exportFurtherCourses(null);
        exporter.exportAdditionalInfo(null);
        exporter.exportInterests(null);
        exporter.commit();
    }

     private ObservableList<Node> getDataAsObservableList() {
        ObservableList<Node> list = FXCollections.observableArrayList();
        list.addAll(data);
        return list;
    }

    public void setProfImage(Image profImage) {
        this.profImage = profImage;
    }

    public Image getProfImage() {
        return profImage;
    }
}
