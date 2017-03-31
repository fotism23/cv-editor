package app.java.utils.exporters;

import app.java.data.Node;
import app.java.utils.ApplicationUtils;
import javafx.scene.image.Image;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class XmlExporter implements Exporter {

    private String path;
    private Document document;
    private Element rootElement;
    private Element documentNode;
    private int templateId;


    public XmlExporter(String filePath, int templateId) {
        this.path = filePath;
        this.templateId = templateId;
        initExporter();
        exportFileInfo();
        exportDocument();
    }

    private void initExporter() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            document = docBuilder.newDocument();
            rootElement = document.createElement("cv");
            document.appendChild(rootElement);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void exportFileInfo() {
        Element fileInfo = document.createElement("file_info");
        rootElement.appendChild(fileInfo);
        Element lastEdit = document.createElement("last_edit");
        lastEdit.appendChild(document.createTextNode(ApplicationUtils.getStringFormattedCurrentDate()));
        fileInfo.appendChild(lastEdit);
        Element template = document.createElement("template");
        template.appendChild(document.createTextNode(Integer.toString(templateId)));
        fileInfo.appendChild(template);
    }

    private void exportDocument() {
        documentNode = document.createElement("document");
        rootElement.appendChild(documentNode);
    }

    public void commit() {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportImage(Image image) {
        Element encodedImageElement = document.createElement("enc_image");
        try {
            if (image != null) {
                String encodedImage = ApplicationUtils.encodeImageToBase64(image);
                encodedImageElement.appendChild(document.createTextNode(encodedImage));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        documentNode.appendChild(encodedImageElement);
    }

    @Override
    public void exportPersonalInfo(HashMap<String, String> hashMap) {
        Element personalInfoElement = document.createElement("node");
        personalInfoElement.setAttribute("key", "1");

        Element element = document.createElement("value");
        element.appendChild(document.createTextNode("GENERAL INFORMATION"));
        personalInfoElement.appendChild(element);

        element = document.createElement("visible_key");
        element.appendChild(document.createTextNode("true"));
        personalInfoElement.appendChild(element);

        element = document.createElement("drawable_id");
        element.appendChild(document.createTextNode("-1"));
        personalInfoElement.appendChild(element);

        element = document.createElement(ApplicationUtils.PERSONAL_INFO_NAME);
        element.appendChild(document.createTextNode(hashMap.get(ApplicationUtils.PERSONAL_INFO_NAME)));
        personalInfoElement.appendChild(element);

        element = document.createElement(ApplicationUtils.PERSONAL_INFO_ADDRESS);
        element.appendChild(document.createTextNode(hashMap.get(ApplicationUtils.PERSONAL_INFO_ADDRESS)));
        personalInfoElement.appendChild(element);

        element = document.createElement(ApplicationUtils.PERSONAL_INFO_HOME);
        element.appendChild(document.createTextNode(hashMap.get(ApplicationUtils.PERSONAL_INFO_HOME)));
        personalInfoElement.appendChild(element);

        element = document.createElement(ApplicationUtils.PERSONAL_INFO_MOBILE);
        element.appendChild(document.createTextNode(hashMap.get(ApplicationUtils.PERSONAL_INFO_MOBILE)));
        personalInfoElement.appendChild(element);

        element = document.createElement(ApplicationUtils.PERSONAL_INFO_EMAIL);
        element.appendChild(document.createTextNode(hashMap.get(ApplicationUtils.PERSONAL_INFO_EMAIL)));
        personalInfoElement.appendChild(element);

        element = document.createElement(ApplicationUtils.PERSONAL_INFO_WEBSITE);
        element.appendChild(document.createTextNode(hashMap.get(ApplicationUtils.PERSONAL_INFO_WEBSITE)));
        personalInfoElement.appendChild(element);

        documentNode.appendChild(personalInfoElement);
    }

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
}
