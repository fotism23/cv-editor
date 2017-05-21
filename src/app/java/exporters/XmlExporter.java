package app.java.exporters;

import app.java.data.ListNode;
import app.java.data.Node;
import app.java.utils.ApplicationUtils;
import javafx.scene.image.Image;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
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

        element = document.createElement("date");
        element.appendChild(document.createTextNode(ApplicationUtils.getStringFormattedCurrentDate()));
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

    @Override
    public void exportProfessionalProfile(Node node) {
        documentNode.appendChild(getContentNode(node));
    }

    @Override
    public void exportSkillsAndExperience(Node node) {
        documentNode.appendChild(getNode(node));
    }

    @Override
    public void exportCareerSummary(Node node) {
        documentNode.appendChild(getNode(node));
    }

    @Override
    public void exportEducationAndTraining(Node node) {
        documentNode.appendChild(getNode(node));
    }

    @Override
    public void exportFurtherCourses(Node node) {
        documentNode.appendChild(getNode(node));
    }

    @Override
    public void exportAdditionalInfo(Node node) {
        documentNode.appendChild(getContentNode(node));
    }

    @Override
    public void exportInterests(Node node) {
        documentNode.appendChild(getContentNode(node));
    }

    @Override
    public void exportCoreStrengths(Node node) {
        documentNode.appendChild(getContentNode(node));
    }

    @Override
    public void exportProfessionalExperience(Node node) {
        documentNode.appendChild(getNode(node));
    }

    private Element getContentNode(Node node) {
        Element contentElement = document.createElement("node");
        contentElement.setAttribute("key", node.getKey());

        Element element = document.createElement("value");
        element.appendChild(document.createTextNode(node.getValue()));
        contentElement.appendChild(element);

        element = document.createElement("visible_key");
        element.appendChild(document.createTextNode(Boolean.toString(node.getKeyVisibility())));
        contentElement.appendChild(element);

        element = document.createElement("date");
        element.appendChild(document.createTextNode(ApplicationUtils.getStringFormattedDate(node.getDate())));
        contentElement.appendChild(element);

        element = document.createElement("drawable_id");
        element.appendChild(document.createTextNode(Integer.toString(node.getLabelDrawableId())));
        contentElement.appendChild(element);

        element = document.createElement("content");
        if (node.getContent() != null) {
            element.appendChild(document.createTextNode(node.getContent()));
        }

        contentElement.appendChild(element);
        return contentElement;
    }

    private Element getNode(Node node) {
        Element nodeToReturn = document.createElement("node");
        nodeToReturn.setAttribute("key", node.getKey());

        Element element = document.createElement("value");
        element.appendChild(document.createTextNode(node.getValue()));
        nodeToReturn.appendChild(element);

        element = document.createElement("visible_key");
        element.appendChild(document.createTextNode(Boolean.toString(node.getKeyVisibility())));
        nodeToReturn.appendChild(element);

        element = document.createElement("drawable_id");
        element.appendChild(document.createTextNode(Integer.toString(node.getLabelDrawableId())));
        nodeToReturn.appendChild(element);

        element = document.createElement("date");
        element.appendChild(document.createTextNode(ApplicationUtils.getStringFormattedDate(node.getDate())));
        nodeToReturn.appendChild(element);

        nodeToReturn.appendChild(getChildren((ListNode) node));

        return nodeToReturn;
    }

    private Element getChildren(ListNode node) {
        ArrayList<Node> nodes = node.getChildren();
        Element elem = document.createElement("children");
        if (nodes.size() <= 0) return elem;

        for (int temp = 0; temp < nodes.size(); temp++) {
            Element element = document.createElement("node");
            element.setAttribute("key", node.getKey() + "." + (temp + 1));

            Element subElem = document.createElement("value");
            subElem.appendChild(document.createTextNode(nodes.get(temp).getValue()));
            element.appendChild(subElem);

            subElem = document.createElement("visible_key");
            subElem.appendChild(document.createTextNode(Boolean.toString(nodes.get(temp).getKeyVisibility())));
            element.appendChild(subElem);

            subElem = document.createElement("drawable_id");
            subElem.appendChild(document.createTextNode(Integer.toString(nodes.get(temp).getLabelDrawableId())));
            element.appendChild(subElem);

            subElem = document.createElement("date");
            subElem.appendChild(document.createTextNode(ApplicationUtils.getStringFormattedDate(nodes.get(temp).getDate())));
            element.appendChild(subElem);

            Element children = getChildren((ListNode) nodes.get(temp));
            if (children != null)
                element.appendChild(children);
            elem.appendChild(element);
        }
        return elem;
    }


}
