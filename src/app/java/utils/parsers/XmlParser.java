package app.java.utils.parsers;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import app.java.data.ExpandableNode;
import app.java.data.Node;
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
    public int parseTemplate() {
        Element fileInfoElement = document.getElementById("file_info");
        return Integer.parseInt(fileInfoElement.getElementsByTagName("template").item(0).getTextContent());
    }

    @Override
    public String parseName() {
        Element docElement = document.getElementById("document");
        org.w3c.dom.Node infoElement = docElement.getElementsByTagName("info").item(0);
        if (infoElement.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            Element eElement = (Element) infoElement;
            return eElement.getElementsByTagName("name").item(0).getTextContent();
        }
        return null;
    }

    @Override
    public String parseAddress() {
        Element docElement = document.getElementById("document");
        org.w3c.dom.Node infoElement = docElement.getElementsByTagName("info").item(0);
        if (infoElement.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            Element eElement = (Element) infoElement;
            return eElement.getElementsByTagName("address").item(0).getTextContent();
        }
        return null;
    }

    @Override
    public String parseHomePhone() {
        Element docElement = document.getElementById("document");
        org.w3c.dom.Node infoElement = docElement.getElementsByTagName("info").item(0);
        if (infoElement.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            Element eElement = (Element) infoElement;
            return eElement.getElementsByTagName("phone").item(0).getTextContent();
        }
        return null;
    }

    @Override
    public String parseMobilePhone() {
        Element docElement = document.getElementById("document");
        org.w3c.dom.Node infoElement = docElement.getElementsByTagName("info").item(0);
        if (infoElement.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            Element eElement = (Element) infoElement;
            return eElement.getElementsByTagName("mobile").item(0).getTextContent();
        }
        return null;
    }

    @Override
    public String parseEmail() {
        Element docElement = document.getElementById("document");
        org.w3c.dom.Node infoElement = docElement.getElementsByTagName("info").item(0);
        if (infoElement.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            Element eElement = (Element) infoElement;
            return eElement.getElementsByTagName("email").item(0).getTextContent();
        }
        return null;
    }

    @Override
    public String parseWebsite() {
        Element docElement = document.getElementById("document");
        org.w3c.dom.Node infoElement = docElement.getElementsByTagName("info").item(0);
        if (infoElement.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            Element eElement = (Element) infoElement;
            return eElement.getElementsByTagName("website").item(0).getTextContent();
        }
        return null;
    }

    @Override
    public Node parseProfessionalProfile() {
        Element docElement = document.getElementById("document");
        NodeList nList = docElement.getElementsByTagName("node");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE ) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("value").item(0).getTextContent().equals("PROFESSIONAL PROFILE")){
                    Node node = new Node(eElement.getAttribute("key"), eElement.getElementsByTagName("value").item(0).getTextContent());
                    node.setContent(eElement.getElementsByTagName("content").item(0).getTextContent());
                    return node;
                }
            }
        }

        return null;
    }

    @Override
    public Node parseSkillsAndExperience() {
        Element docElement = document.getElementById("document");
        NodeList nList = docElement.getElementsByTagName("node");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE ) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("value").item(0).getTextContent().equals("SKILLS AND EXPERIENCE")){
                    Node node = new ExpandableNode(eElement.getAttribute("key"), eElement.getElementsByTagName("value").item(0).getTextContent());
                    // todo add children
                    //node.setContent(eElement.getElementsByTagName("content").item(0).getTextContent());
                    return node;
                }
            }
        }
        return null;
    }

    @Override
    public Node parseCareerSummary() {
        Element docElement = document.getElementById("document");
        NodeList nList = docElement.getElementsByTagName("node");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE ) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("value").item(0).getTextContent().equals("CAREER SUMMARY")){
                    Node node = new ExpandableNode(eElement.getAttribute("key"), eElement.getElementsByTagName("value").item(0).getTextContent());
                    // todo add children
                    //node.setContent(eElement.getElementsByTagName("content").item(0).getTextContent());
                    return node;
                }
            }
        }
        return null;
    }

    @Override
    public Node parseEducationAndTraining() {
        Element docElement = document.getElementById("document");
        NodeList nList = docElement.getElementsByTagName("node");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE ) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("value").item(0).getTextContent().equals("EDUCATION AND TRAINING")){
                    Node node = new ExpandableNode(eElement.getAttribute("key"), eElement.getElementsByTagName("value").item(0).getTextContent());
                    // todo add children
                    //node.setContent(eElement.getElementsByTagName("content").item(0).getTextContent());
                    return node;
                }
            }
        }
        return null;
    }

    @Override
    public Node parseFurtherCourses() {
        Element docElement = document.getElementById("document");
        NodeList nList = docElement.getElementsByTagName("node");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE ) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("value").item(0).getTextContent().equals("FURTHER COURSES")){
                    Node node = new ExpandableNode(eElement.getAttribute("key"), eElement.getElementsByTagName("value").item(0).getTextContent());
                    // todo add children
                    //node.setContent(eElement.getElementsByTagName("content").item(0).getTextContent());
                    return node;
                }
            }
        }
        return null;
    }

    @Override
    public Node parseAdditionalInfo() {
        Element docElement = document.getElementById("document");
        NodeList nList = docElement.getElementsByTagName("node");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE ) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("value").item(0).getTextContent().equals("ADDITIONAL INFORMATION")){
                    Node node = new ExpandableNode(eElement.getAttribute("key"), eElement.getElementsByTagName("value").item(0).getTextContent());
                    // todo add children
                    //node.setContent(eElement.getElementsByTagName("content").item(0).getTextContent());
                    return node;
                }
            }
        }
        return null;
    }

    @Override
    public Node parseInterests() {
        Element docElement = document.getElementById("document");
        NodeList nList = docElement.getElementsByTagName("node");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE ) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("value").item(0).getTextContent().equals("INTERESTS")){
                    Node node = new ExpandableNode(eElement.getAttribute("key"), eElement.getElementsByTagName("value").item(0).getTextContent());
                    // todo add children
                    //node.setContent(eElement.getElementsByTagName("content").item(0).getTextContent());
                    return node;
                }
            }
        }
        return null;
    }

    @Override
    public Node parseCoreStrengths() {
        Element docElement = document.getElementById("document");
        NodeList nList = docElement.getElementsByTagName("node");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE ) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("value").item(0).getTextContent().equals("CORE STRENGTHS")){
                    Node node = new Node(eElement.getAttribute("key"), eElement.getElementsByTagName("value").item(0).getTextContent());
                    node.setContent(eElement.getElementsByTagName("content").item(0).getTextContent());
                    return node;
                }
            }
        }

        return null;
    }

    @Override
    public Node parseProfessionalExperience() {
        return null;
    }
}
