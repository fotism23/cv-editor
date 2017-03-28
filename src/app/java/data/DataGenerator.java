package app.java.data;

import app.java.utils.ApplicationUtils;
import app.java.utils.parsers.Parser;
import app.java.utils.parsers.XmlParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.activation.UnsupportedDataTypeException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataGenerator {
    private Parser parser;
    private ArrayList<Node> data;
    private HashMap<String, String> personalInfo;
    private int template;

    public DataGenerator(String path, int parseType) throws UnsupportedDataTypeException {
        if (parseType == ApplicationUtils.XML_TYPE_ID)
            this.parser = new XmlParser(path);
        else
            throw new UnsupportedDataTypeException(parseType + " data type is not supported.");
        personalInfo = new HashMap<>();
        data = new ArrayList<>();
    }

    public void generateData() {
        generatePersonalInfoNode();
        generateAdditionalData();
    }

    private void generateAdditionalData() {
        if (template == ApplicationUtils.FUNCTIONAL_TEMPLATE_ID)
            generateFunctional();
        else if (template == ApplicationUtils.CHRONOLOGICAL_TEMPLATE_ID)
            generateChronological();
        else if (template == ApplicationUtils.COMBINED_TEMPLATE_ID)
            generateCombined();
        else
            throw new UnsupportedOperationException("Invalid template ID.");
    }

    private String queryPersonalInfo(String key) throws InvalidKeyException {
        if (personalInfo.containsKey(key))
            return personalInfo.get(key);
        throw new InvalidKeyException(key + " key not found.");
    }

    private void generateCombined() {
        data.add(parser.parseProfessionalProfile());
        data.add(parser.parseSkillsAndExperience());
        data.add(parser.parseProfessionalExperience());
        data.add(parser.parseEducationAndTraining());
        data.add(parser.parseFurtherCourses());
        data.add(parser.parseAdditionalInfo());
        data.add(parser.parseInterests());
    }

    private void generateChronological() {
        data.add(parser.parseProfessionalProfile());
        data.add(parser.parseCoreStrengths());
        data.add(parser.parseProfessionalExperience());
        data.add(parser.parseEducationAndTraining());
        data.add(parser.parseFurtherCourses());
        data.add(parser.parseAdditionalInfo());
        data.add(parser.parseInterests());
    }

    private void generateFunctional() {
        data.add(parser.parseProfessionalProfile());
        data.add(parser.parseSkillsAndExperience());
        data.add(parser.parseCareerSummary());
        data.add(parser.parseEducationAndTraining());
        data.add(parser.parseFurtherCourses());
        data.add(parser.parseAdditionalInfo());
        data.add(parser.parseInterests());
    }

    public void retrieveTemplate() {
        template = parser.parseTemplate();
    }

    private void generatePersonalInfoNode() {

        personalInfo.put(ApplicationUtils.PERSONAL_INFO_NAME, parser.parseName());
        personalInfo.put(ApplicationUtils.PERSONAL_INFO_ADDRESS, parser.parseAddress());
        personalInfo.put(ApplicationUtils.PERSONAL_INFO_HOME, parser.parseHomePhone());
        personalInfo.put(ApplicationUtils.PERSONAL_INFO_MOBILE, parser.parseMobilePhone());
        personalInfo.put(ApplicationUtils.PERSONAL_INFO_EMAIL, parser.parseEmail());
        personalInfo.put(ApplicationUtils.PERSONAL_INFO_WEBSITE, parser.parseWebsite());
    }

    private ObservableList<Node> getDataAsObservableList() {
        ObservableList<Node> list = FXCollections.observableArrayList();
        list.addAll(data);
        return list;
    }
}
