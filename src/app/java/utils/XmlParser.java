package app.java.utils;


import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XmlParser implements Parser{

    private String filePath;
    File xmlFile;
    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    Document document;

    public XmlParser(String filePath) {
        this.filePath = filePath;
        this.xmlFile = new File(filePath);
        initParser();
    }

    private void initParser() {
        try {
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String parseName() {
        return null;
    }

    @Override
    public String parseAddress() {
        return null;
    }

    @Override
    public String parseHomePhone() {
        return null;
    }

    @Override
    public String parseMobilePhone() {
        return null;
    }

    @Override
    public String parseEmail() {
        return null;
    }

    @Override
    public String parseWebsite() {
        return null;
    }
}
