package app.java.data;

import app.java.utils.ApplicationUtils;
import app.java.utils.exporters.Exporter;
import app.java.utils.exporters.LatexExporter;
import app.java.utils.exporters.TextExporter;
import app.java.utils.exporters.XmlExporter;
import app.java.utils.parsers.Parser;
import app.java.utils.parsers.XmlParser;
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
        } else if (exportType == ApplicationUtils.TEXT_TYPE_ID){
            exportText(path);
        } else
            throw new UnsupportedDataTypeException(exportType + " data type is not supported.");
    }

    private void generateData(Parser parser) {
        retrieveTemplate(parser);
        generatePersonalInfoNode(parser);
        generateAdditionalData(parser);
    }

    private void retrieveTemplate(Parser parser) {
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

    private Node getNodeWithValue(String value) {
        for (Node node : data) {
            if (node.getValue().equals(value)) return node;
        }
        return null;
    }

    private void exportText(String exportTarget) {
        if (template == ApplicationUtils.FUNCTIONAL_TEMPLATE_ID)
            exportFunctional(new TextExporter(exportTarget));
        else if (template == ApplicationUtils.CHRONOLOGICAL_TEMPLATE_ID)
            exportChronological(new TextExporter(exportTarget));
        else if (template == ApplicationUtils.COMBINED_TEMPLATE_ID)
            exportCombined(new TextExporter(exportTarget));
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
        exporter.exportProfessionalProfile(getNodeWithValue(ApplicationUtils.PROFESSIONAL_PROFILE_VALUE));
        exporter.exportSkillsAndExperience(getNodeWithValue(ApplicationUtils.SKILLS_AND_EXPERIENCE_VALUE));
        exporter.exportCareerSummary(getNodeWithValue(ApplicationUtils.CAREER_SUMMARY_VALUE));
        exporter.exportEducationAndTraining(getNodeWithValue(ApplicationUtils.EDUCATION_AND_TRAINING_VALUE));
        exporter.exportFurtherCourses(getNodeWithValue(ApplicationUtils.FURTHER_COURSES_VALUE));
        exporter.exportAdditionalInfo(getNodeWithValue(ApplicationUtils.ADDITIONAL_INFORMATION));
        exporter.exportInterests(getNodeWithValue(ApplicationUtils.INTERESTS_VALUE));
        exporter.commit();
    }

    private void exportChronological(Exporter exporter) {
        exporter.exportImage(profImage);
        exporter.exportPersonalInfo(personalInfo);
        exporter.exportProfessionalProfile(getNodeWithValue(ApplicationUtils.PROFESSIONAL_PROFILE_VALUE));
        exporter.exportCoreStrengths(getNodeWithValue(ApplicationUtils.CORE_STRENGTHS_VALUE));
        exporter.exportProfessionalExperience(getNodeWithValue(ApplicationUtils.PROFESSIONAL_EXPERIENCE_VALUE));
        exporter.exportEducationAndTraining(getNodeWithValue(ApplicationUtils.EDUCATION_AND_TRAINING_VALUE));
        exporter.exportFurtherCourses(getNodeWithValue(ApplicationUtils.FURTHER_COURSES_VALUE));
        exporter.exportAdditionalInfo(getNodeWithValue(ApplicationUtils.ADDITIONAL_INFORMATION));
        exporter.exportInterests(getNodeWithValue(ApplicationUtils.INTERESTS_VALUE));
        exporter.commit();
    }

    private void exportCombined(Exporter exporter) {
        exporter.exportImage(profImage);
        exporter.exportPersonalInfo(personalInfo);
        exporter.exportProfessionalProfile(getNodeWithValue(ApplicationUtils.PROFESSIONAL_PROFILE_VALUE));
        exporter.exportSkillsAndExperience(getNodeWithValue(ApplicationUtils.SKILLS_AND_EXPERIENCE_VALUE));
        exporter.exportProfessionalExperience(getNodeWithValue(ApplicationUtils.PROFESSIONAL_EXPERIENCE_VALUE));
        exporter.exportEducationAndTraining(getNodeWithValue(ApplicationUtils.EDUCATION_AND_TRAINING_VALUE));
        exporter.exportFurtherCourses(getNodeWithValue(ApplicationUtils.FURTHER_COURSES_VALUE));
        exporter.exportAdditionalInfo(getNodeWithValue(ApplicationUtils.ADDITIONAL_INFORMATION));
        exporter.exportInterests(getNodeWithValue(ApplicationUtils.INTERESTS_VALUE));
        exporter.commit();
    }

    public void setProfImage(Image profImage) {
        this.profImage = profImage;
    }

    public Image getProfImage() {
        return profImage;
    }

    public ArrayList<Node> getData() {
        return data;
    }

    public void addElement(String itemSelected) {
        //String parentKey = itemSelected.substring(0, itemSelected.length() - 2);
        Node parentNode = queryNode(itemSelected);
        ExpandableNode node = NodeFactory.createNewExpandableNode("","");
        ((ExpandableNode) parentNode).addChild(node);
    }

    public boolean removeElement(String itemSelected) {
        if (itemSelected.length() <= 2) return false;
        String parentKey = itemSelected.substring(0, itemSelected.length() - 2);
        Node parentNode = queryNode(parentKey);
        return ((ExpandableNode) parentNode).removeChild(itemSelected);
    }

    public Node queryNode(String itemSelected) {
        String[] parts = itemSelected.split("\\.");
        Node node = data.get(Integer.parseInt(parts[0]) - 2);
        System.out.println(node.getKey());
        if (node.getKey().equals(itemSelected))
            return node;
        int keyIndex = 0;
        String tempKey = parts[keyIndex];
        while (true) {
            if (((ExpandableNode) node).getChild(itemSelected) == null) {
                keyIndex++;
                tempKey += "." + parts[keyIndex];
                node = ((ExpandableNode) node).getChild(tempKey);
            } else {
                return ((ExpandableNode) node).getChild(itemSelected);
            }
        }
    }
}
