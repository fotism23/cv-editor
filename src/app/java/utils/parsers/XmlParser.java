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
        /*
        if (imageElement.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            Element eElement = (Element) imageElement;
            System.out.println(eElement.getElementsByTagName("name").item(0).getTextContent());
        }
        */
        return null;
    }

    @Override
    public int parseTemplate() {
        Element fileInfoElement = (Element) document.getElementsByTagName("file_info").item(0);
        return Integer.parseInt(fileInfoElement.getElementsByTagName("template").item(0).getTextContent());
    }

    @Override
    public String parseName() {
        Element docElement = (Element) document.getElementsByTagName("document").item(0);
        org.w3c.dom.Node infoElement = docElement.getElementsByTagName("node").item(0);
        if (infoElement.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            Element eElement = (Element) infoElement;

            //System.out.println("Root element :" + eElement.getElementsByTagName("name").item(0).getTextContent());
            return eElement.getElementsByTagName(ApplicationUtils.PERSONAL_INFO_NAME).item(0).getTextContent();
        }
        return null;
    }

    @Override
    public String parseAddress() {
        Element docElement = (Element) document.getElementsByTagName("document").item(0);
        org.w3c.dom.Node infoElement = docElement.getElementsByTagName("node").item(0);
        if (infoElement.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            Element eElement = (Element) infoElement;
            return eElement.getElementsByTagName(ApplicationUtils.PERSONAL_INFO_ADDRESS).item(0).getTextContent();
        }
        return null;
    }

    @Override
    public String parseHomePhone() {
        Element docElement = (Element) document.getElementsByTagName("document").item(0);
        org.w3c.dom.Node infoElement = docElement.getElementsByTagName("node").item(0);
        if (infoElement.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            Element eElement = (Element) infoElement;
            return eElement.getElementsByTagName(ApplicationUtils.PERSONAL_INFO_HOME).item(0).getTextContent();
        }
        return null;
    }

    @Override
    public String parseMobilePhone() {
        Element docElement = (Element) document.getElementsByTagName("document").item(0);
        org.w3c.dom.Node infoElement = docElement.getElementsByTagName("node").item(0);
        if (infoElement.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            Element eElement = (Element) infoElement;
            return eElement.getElementsByTagName(ApplicationUtils.PERSONAL_INFO_MOBILE).item(0).getTextContent();
        }
        return null;
    }

    @Override
    public String parseEmail() {
        Element docElement = (Element) document.getElementsByTagName("document").item(0);
        org.w3c.dom.Node infoElement = docElement.getElementsByTagName("node").item(0);
        if (infoElement.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            Element eElement = (Element) infoElement;
            return eElement.getElementsByTagName(ApplicationUtils.PERSONAL_INFO_EMAIL).item(0).getTextContent();
        }
        return null;
    }

    @Override
    public String parseWebsite() {
        Element docElement = (Element) document.getElementsByTagName("document").item(0);
        org.w3c.dom.Node infoElement = docElement.getElementsByTagName("node").item(0);
        if (infoElement.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            Element eElement = (Element) infoElement;
            return eElement.getElementsByTagName(ApplicationUtils.PERSONAL_INFO_WEBSITE).item(0).getTextContent();
        }
        return null;
    }

    @Override
    public Node parseProfessionalProfile() {
        Element docElement = (Element) document.getElementsByTagName("document").item(0);
        NodeList nList = docElement.getElementsByTagName("node");
        for (int temp = 1; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("value").item(0).getTextContent().equals("PROFESSIONAL PROFILE")) {
                    Node node = new Node(eElement.getAttribute("key"), eElement.getElementsByTagName("value").item(0).getTextContent());
                    node.setKeyVisibility(Boolean.parseBoolean(eElement.getElementsByTagName("visible_key").item(0).getTextContent()));
                    node.setContent(retrieveContent(eElement));
                    return node;
                }
            }
        }

        return null;
    }

    @Override
    public Node parseSkillsAndExperience() {
        Element docElement = (Element) document.getElementsByTagName("document").item(0);
        NodeList nList = docElement.getElementsByTagName("node");
        for (int temp = 1; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("value").item(0).getTextContent().equals("SKILLS AND EXPERIENCE")) {
                    return getData(eElement);
                }
            }
        }
        return null;
    }

    @Override
    public Node parseCareerSummary() {
        Element docElement = (Element) document.getElementsByTagName("document").item(0);
        NodeList nList = docElement.getElementsByTagName("node");
        for (int temp = 1; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("value").item(0).getTextContent().equals("CAREER SUMMARY")) {
                    return getData(eElement);
                }
            }
        }
        return null;
    }

    @Override
    public Node parseEducationAndTraining() {
        Element docElement = (Element) document.getElementsByTagName("document").item(0);
        NodeList nList = docElement.getElementsByTagName("node");
        for (int temp = 1; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("value").item(0).getTextContent().equals("EDUCATION AND TRAINING")) {
                    return getData(eElement);
                }
            }
        }
        return null;
    }

    @Override
    public Node parseFurtherCourses() {
        Element docElement = (Element) document.getElementsByTagName("document").item(0);
        NodeList nList = docElement.getElementsByTagName("node");
        for (int temp = 1; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("value").item(0).getTextContent().equals("FURTHER COURSES")) {
                    return getData(eElement);
                }
            }
        }
        return null;
    }

    @Override
    public Node parseAdditionalInfo() {
        Element docElement = (Element) document.getElementsByTagName("document").item(0);
        NodeList nList = docElement.getElementsByTagName("node");
        for (int temp = 1; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("value").item(0).getTextContent().equals("ADDITIONAL INFORMATION")) {
                    Node node = new Node(eElement.getAttribute("key"), eElement.getElementsByTagName("value").item(0).getTextContent());
                    node.setContent(retrieveContent(eElement));
                    return node;
                }
            }
        }
        return null;
    }

    @Override
    public Node parseInterests() {
        Element docElement = (Element) document.getElementsByTagName("document").item(0);
        NodeList nList = docElement.getElementsByTagName("node");

        for (int temp = 1; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("value").item(0).getTextContent().equals("INTERESTS")) {
                    Node node = new Node(eElement.getAttribute("key"), eElement.getElementsByTagName("value").item(0).getTextContent());
                    node.setContent(retrieveContent(eElement));
                    return node;
                }
            }
        }
        return null;
    }

    @Override
    public Node parseCoreStrengths() {
        Element docElement = (Element) document.getElementsByTagName("document").item(0);
        NodeList nList = docElement.getElementsByTagName("node");

        for (int temp = 1; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("value").item(0).getTextContent().equals("CORE STRENGTHS")) {
                    Node node = new Node(eElement.getAttribute("key"), eElement.getElementsByTagName("value").item(0).getTextContent());
                    node.setContent(retrieveContent(eElement));
                    return node;
                }
            }
        }

        return null;
    }

    @Override
    public Node parseProfessionalExperience() {
        Element docElement = (Element) document.getElementsByTagName("document").item(0);
        NodeList nList = docElement.getElementsByTagName("node");
        for (int temp = 1; temp < nList.getLength(); temp++) {
            org.w3c.dom.Node nNode = nList.item(temp);
            if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.getElementsByTagName("value").item(0).getTextContent().equals("PROFESSIONAL EXPERIENCE")) {
                    return getData(eElement);
                }
            }
        }
        return null;
    }

    private String retrieveContent(Element eElement) {
        if (eElement.getElementsByTagName("content").item(0) != null)
            return eElement.getElementsByTagName("content").item(0).getTextContent();
        else return null;
    }

    private ArrayList<Node> getChildren(Element eElement) {
        ArrayList<Node> children = new ArrayList<>();
        if (eElement.hasChildNodes()) {
            NodeList nList = eElement.getElementsByTagName("node");
            for (int temp = 1; temp < nList.getLength(); temp++) {
                org.w3c.dom.Node nNode = nList.item(temp);
                if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element elem = (Element) nNode;
                    Node node = new ExpandableNode(elem.getAttribute("key"), elem.getElementsByTagName("value").item(0).getTextContent());

                    Element element = (Element) elem.getElementsByTagName("children").item(0);
                    if (element != null)
                        ((ExpandableNode) node).addChildren(getChildren(element));
                    children.add(node);
                }
            }
            return children;
        }
        return null;
    }

    private Node getData(Element eElement) {
        Node node = new ExpandableNode(eElement.getAttribute("key"), eElement.getElementsByTagName("value").item(0).getTextContent());
        node.setKeyVisibility(Boolean.parseBoolean(eElement.getElementsByTagName("visible_key").item(0).getTextContent()));
        node.setLabelDrawableId(Integer.parseInt(eElement.getElementsByTagName("drawable_id").item(0).getTextContent()));
        Element elem = (Element) eElement.getElementsByTagName("children").item(0);
        ((ExpandableNode) node).addChildren(getChildren(elem));
        return node;
    }
}
