package app.java.utils.parsers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import app.java.data.ExpandableNode;
import app.java.data.Node;
import app.java.utils.ApplicationUtils;
import javafx.scene.image.Image;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlParser implements Parser {
    private File xmlFile;
    private Document document;

    public XmlParser(String filePath) {
        this.xmlFile = new File(filePath);
        initParser();
    }

    private void initParser() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Image parseImage() {
        Element docElement = (Element) document.getElementsByTagName("document").item(0);
        org.w3c.dom.Node imageElement = docElement.getElementsByTagName("enc_image").item(0);
        Element eElement = (Element) imageElement;
        System.out.println(eElement.getTextContent());
        if (!eElement.getTextContent().trim().equals("")) {
            try {
                return ApplicationUtils.decodeImageFromBase64(eElement.getTextContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public int parseTemplate() {
        Element fileInfoElement = (Element) document.getElementsByTagName("file_info").item(0);
        return Integer.parseInt(fileInfoElement.getElementsByTagName("template").item(0).getTextContent());
    }

    @Override
    public String parseName() {
        return getDataFromInfoNode(ApplicationUtils.PERSONAL_INFO_NAME);
    }

    @Override
    public String parseAddress() {
        return getDataFromInfoNode(ApplicationUtils.PERSONAL_INFO_ADDRESS);
    }

    @Override
    public String parseHomePhone() {
        return getDataFromInfoNode(ApplicationUtils.PERSONAL_INFO_HOME);
    }

    @Override
    public String parseMobilePhone() {
        return getDataFromInfoNode(ApplicationUtils.PERSONAL_INFO_MOBILE);
    }

    @Override
    public String parseEmail() {
        return getDataFromInfoNode(ApplicationUtils.PERSONAL_INFO_EMAIL);
    }

    @Override
    public String parseWebsite() {
        return getDataFromInfoNode(ApplicationUtils.PERSONAL_INFO_WEBSITE);
    }

    @Override
    public Node parseProfessionalProfile() {
        return getRequestedContentNode("PROFESSIONAL PROFILE");
    }

    @Override
    public Node parseSkillsAndExperience() {
        return getRequestedNode("SKILLS AND EXPERIENCE");
    }

    @Override
    public Node parseCareerSummary() {
        return getRequestedNode("CAREER SUMMARY");
    }

    @Override
    public Node parseEducationAndTraining() {
        return getRequestedNode("EDUCATION AND TRAINING");
    }

    @Override
    public Node parseFurtherCourses() {
        return getRequestedNode("FURTHER COURSES");
    }

    @Override
    public Node parseAdditionalInfo() {
        return getRequestedContentNode("ADDITIONAL INFORMATION");
    }

    @Override
    public Node parseInterests() {
        return getRequestedContentNode("INTERESTS");
    }

    @Override
    public Node parseCoreStrengths() {
        return getRequestedContentNode("CORE STRENGTHS");
    }

    @Override
    public Node parseProfessionalExperience() {
        return getRequestedNode("PROFESSIONAL EXPERIENCE");
    }

    private String getDataFromInfoNode(String queryStr) {
        Element docElement = (Element) document.getElementsByTagName("document").item(0);
        org.w3c.dom.Node infoElement = docElement.getElementsByTagName("node").item(0);
        if (infoElement.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            Element eElement = (Element) infoElement;
            return eElement.getElementsByTagName(queryStr).item(0).getTextContent();
        }
        return null;
    }

    private Node getRequestedContentNode(String queryStr) {
        Element docElement = (Element) document.getElementsByTagName("document").item(0);
        NodeList nList = docElement.getElementsByTagName("node");
        for (int temp = 1; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("value").item(0).getTextContent().equals(queryStr)) {
                    Node node = new Node(eElement.getAttribute("key"), eElement.getElementsByTagName("value").item(0).getTextContent());
                    node.setKeyVisibility(Boolean.parseBoolean(eElement.getElementsByTagName("visible_key").item(0).getTextContent()));
                    node.setLabelDrawableId(Integer.parseInt(eElement.getElementsByTagName("drawable_id").item(0).getTextContent()));
                    node.setContent(retrieveContent(eElement));
                    return node;
                }
            }
        }
        return null;
    }

    private Node getRequestedNode(String queryStr) {
        Element docElement = (Element) document.getElementsByTagName("document").item(0);
        NodeList nList = docElement.getElementsByTagName("node");
        for (int temp = 1; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("value").item(0).getTextContent().equals(queryStr)) {
                    return getData(eElement);
                }
            }
        }
        return null;
    }

    private String retrieveContent(Element eElement) {
        if (eElement.getElementsByTagName("content").item(0) != null)
            return eElement.getElementsByTagName("content").item(0).getTextContent();
        else
            return null;
    }

    private ArrayList<Node> getChildren(Element eElement) {
        ArrayList<Node> children = new ArrayList<>();
        if (eElement.hasChildNodes()) {
            NodeList nList = eElement.getElementsByTagName("node");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                org.w3c.dom.Node nNode = nList.item(temp);
                if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element elem = (Element) nNode;
                    System.out.println("ELEMENT TO ADD : " + getData(elem).getKey());
                    children.add(getData(elem));
                }
            }
        }
        return children;
    }

    private Node getData(Element eElement) {
        Node node = new ExpandableNode(eElement.getAttribute("key"), eElement.getElementsByTagName("value").item(0).getTextContent());
        node.setKeyVisibility(Boolean.parseBoolean(eElement.getElementsByTagName("visible_key").item(0).getTextContent()));
        node.setLabelDrawableId(Integer.parseInt(eElement.getElementsByTagName("drawable_id").item(0).getTextContent()));
        System.out.println("drawable id " + node.getLabelDrawableId());
        Element elem = (Element) eElement.getElementsByTagName("children").item(0);
        if (elem != null) {
            for (Node n : getChildren(elem))
                System.out.println("elem : " + n.getKey());
            System.out.println();
            ((ExpandableNode) node).addChildren(getChildren(elem));
        }
        return node;
    }
}
