package app.java;

import app.java.data.Document;
import app.java.utils.ApplicationUtils;
import app.java.exporters.Exporter;
import app.java.exporters.LatexExporter;
import app.java.exporters.TextExporter;
import app.java.exporters.XmlExporter;
import app.java.parsers.Parser;
import app.java.parsers.XmlParser;

import javax.activation.UnsupportedDataTypeException;

public class DocumentController {
    private Document document;

    public DocumentController() {
        document = new Document();
    }

    public void importData(String path, int parseType) throws UnsupportedDataTypeException {
        if (parseType == ApplicationUtils.XML_TYPE_ID){
            parseData(new XmlParser(path));
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

    private void parseData(Parser parser) {
        parseTemplate(parser);
        parsePersonalInformation(parser);
        parseAdditionalData(parser);
    }

    private void parseTemplate(Parser parser) {
        document.setTemplate(parser.parseTemplate());
    }

    private void parsePersonalInformation(Parser parser) {
        document.setName(parser.parseName());
        document.setAddress(parser.parseAddress());
        document.setHomePhone(parser.parseHomePhone());
        document.setMobilePhone(parser.parseMobilePhone());
        document.setEmail(parser.parseEmail());
        document.setWebsite(parser.parseWebsite());
    }

    private void parseAdditionalData(Parser parser) {
        if (document.getTemplate() == ApplicationUtils.FUNCTIONAL_TEMPLATE_ID)
            parseFunctional(parser);
        else if (document.getTemplate() == ApplicationUtils.CHRONOLOGICAL_TEMPLATE_ID)
            parseChronological(parser);
        else if (document.getTemplate() == ApplicationUtils.COMBINED_TEMPLATE_ID)
            parseCombined(parser);
        else
            throw new UnsupportedOperationException("Invalid template ID.");
    }

    private void parseCombined(Parser parser) {
        document.addData(parser.parseProfessionalProfile());
        document.addData(parser.parseSkillsAndExperience());
        document.addData(parser.parseProfessionalExperience());
        document.addData(parser.parseEducationAndTraining());
        document.addData(parser.parseFurtherCourses());
        document.addData(parser.parseAdditionalInfo());
        document.addData(parser.parseInterests());
    }

    private void parseChronological(Parser parser) {
        document.addData(parser.parseProfessionalProfile());
        document.addData(parser.parseCoreStrengths());
        document.addData(parser.parseProfessionalExperience());
        document.addData(parser.parseEducationAndTraining());
        document.addData(parser.parseFurtherCourses());
        document.addData(parser.parseAdditionalInfo());
        document.addData(parser.parseInterests());
    }

    private void parseFunctional(Parser parser) {
        document.setProfImage(parser.parseImage());
        document.addData(parser.parseProfessionalProfile());
        document.addData(parser.parseSkillsAndExperience());
        document.addData(parser.parseCareerSummary());
        document.addData(parser.parseEducationAndTraining());
        document.addData(parser.parseFurtherCourses());
        document.addData(parser.parseAdditionalInfo());
        document.addData(parser.parseInterests());
    }

    private void exportXml(String path) {
        if (document.getTemplate() == ApplicationUtils.FUNCTIONAL_TEMPLATE_ID)
            exportFunctional(new XmlExporter(path, document.getTemplate()));
        else if (document.getTemplate() == ApplicationUtils.CHRONOLOGICAL_TEMPLATE_ID)
            exportChronological(new XmlExporter(path, document.getTemplate()));
        else if (document.getTemplate() == ApplicationUtils.COMBINED_TEMPLATE_ID)
            exportCombined(new XmlExporter(path, document.getTemplate()));
        else
            throw new UnsupportedOperationException("Invalid template ID.");
    }

    private void exportText(String exportTargetPath) {
        if (document.getTemplate() == ApplicationUtils.FUNCTIONAL_TEMPLATE_ID)
            exportFunctional(new TextExporter(exportTargetPath));
        else if (document.getTemplate() == ApplicationUtils.CHRONOLOGICAL_TEMPLATE_ID)
            exportChronological(new TextExporter(exportTargetPath));
        else if (document.getTemplate() == ApplicationUtils.COMBINED_TEMPLATE_ID)
            exportCombined(new TextExporter(exportTargetPath));
        else
            throw new UnsupportedOperationException("Invalid template ID.");
    }

    private void exportLatex(String exportTargetPath) {
        if (document.getTemplate() == ApplicationUtils.FUNCTIONAL_TEMPLATE_ID)
            exportFunctional(new LatexExporter(exportTargetPath));
        else if (document.getTemplate() == ApplicationUtils.CHRONOLOGICAL_TEMPLATE_ID)
            exportChronological(new LatexExporter(exportTargetPath));
        else if (document.getTemplate() == ApplicationUtils.COMBINED_TEMPLATE_ID)
            exportCombined(new LatexExporter(exportTargetPath));
        else
            throw new UnsupportedOperationException("Invalid template ID.");
    }

    private void exportFunctional(Exporter exporter) {
        exporter.exportImage(document.getProfImage());
        exporter.exportPersonalInfo(document.getPersonalInfo());
        exporter.exportProfessionalProfile(document.getSectionWithValue(ApplicationUtils.PROFESSIONAL_PROFILE_VALUE));
        exporter.exportSkillsAndExperience(document.getSectionWithValue(ApplicationUtils.SKILLS_AND_EXPERIENCE_VALUE));
        exporter.exportCareerSummary(document.getSectionWithValue(ApplicationUtils.CAREER_SUMMARY_VALUE));
        exporter.exportEducationAndTraining(document.getSectionWithValue(ApplicationUtils.EDUCATION_AND_TRAINING_VALUE));
        exporter.exportFurtherCourses(document.getSectionWithValue(ApplicationUtils.FURTHER_COURSES_VALUE));
        exporter.exportAdditionalInfo(document.getSectionWithValue(ApplicationUtils.ADDITIONAL_INFORMATION));
        exporter.exportInterests(document.getSectionWithValue(ApplicationUtils.INTERESTS_VALUE));
        exporter.commit();
    }

    private void exportChronological(Exporter exporter) {
        exporter.exportImage(document.getProfImage());
        exporter.exportPersonalInfo(document.getPersonalInfo());
        exporter.exportProfessionalProfile(document.getSectionWithValue(ApplicationUtils.PROFESSIONAL_PROFILE_VALUE));
        exporter.exportCoreStrengths(document.getSectionWithValue(ApplicationUtils.CORE_STRENGTHS_VALUE));
        exporter.exportProfessionalExperience(document.getSectionWithValue(ApplicationUtils.PROFESSIONAL_EXPERIENCE_VALUE));
        exporter.exportEducationAndTraining(document.getSectionWithValue(ApplicationUtils.EDUCATION_AND_TRAINING_VALUE));
        exporter.exportFurtherCourses(document.getSectionWithValue(ApplicationUtils.FURTHER_COURSES_VALUE));
        exporter.exportAdditionalInfo(document.getSectionWithValue(ApplicationUtils.ADDITIONAL_INFORMATION));
        exporter.exportInterests(document.getSectionWithValue(ApplicationUtils.INTERESTS_VALUE));
        exporter.commit();
    }

    private void exportCombined(Exporter exporter) {
        exporter.exportImage(document.getProfImage());
        exporter.exportPersonalInfo(document.getPersonalInfo());
        exporter.exportProfessionalProfile(document.getSectionWithValue(ApplicationUtils.PROFESSIONAL_PROFILE_VALUE));
        exporter.exportSkillsAndExperience(document.getSectionWithValue(ApplicationUtils.SKILLS_AND_EXPERIENCE_VALUE));
        exporter.exportProfessionalExperience(document.getSectionWithValue(ApplicationUtils.PROFESSIONAL_EXPERIENCE_VALUE));
        exporter.exportEducationAndTraining(document.getSectionWithValue(ApplicationUtils.EDUCATION_AND_TRAINING_VALUE));
        exporter.exportFurtherCourses(document.getSectionWithValue(ApplicationUtils.FURTHER_COURSES_VALUE));
        exporter.exportAdditionalInfo(document.getSectionWithValue(ApplicationUtils.ADDITIONAL_INFORMATION));
        exporter.exportInterests(document.getSectionWithValue(ApplicationUtils.INTERESTS_VALUE));
        exporter.commit();
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

}
