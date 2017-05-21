package app.java;

import app.java.data.Document;
import app.java.data.Node;
import app.java.utils.ApplicationUtils;

import javax.activation.UnsupportedDataTypeException;
import java.io.File;
import java.util.ArrayList;

public abstract class Merger {

    public static void merge(File file, DocumentController firstDocument, DocumentController secondDocument, ArrayList<Node> selectedNodes) {
        if (firstDocument.getDocument().getTemplate() == ApplicationUtils.FUNCTIONAL_TEMPLATE_ID
                && secondDocument.getDocument().getTemplate() == ApplicationUtils.FUNCTIONAL_TEMPLATE_ID)
            mergeToFunctional(file, firstDocument, selectedNodes);
        else if (firstDocument.getDocument().getTemplate() == ApplicationUtils.CHRONOLOGICAL_TEMPLATE_ID
                && secondDocument.getDocument().getTemplate() == ApplicationUtils.CHRONOLOGICAL_TEMPLATE_ID)
            mergeToChronological(file, firstDocument, selectedNodes);
        else
            mergeToCombined(file, firstDocument, selectedNodes);
    }

    private static void mergeToFunctional(File file, DocumentController firstDocument, ArrayList<Node> selectedNodes) {
        Document document = new Document();
        document.setTemplate(ApplicationUtils.FUNCTIONAL_TEMPLATE_ID);
        document.setPersonalInfo(firstDocument.getDocument().getPersonalInfo());

        for (Node node : firstDocument.getDocument().getData()) {
            if (!listContains(selectedNodes, node)) {
                document.addData(node);
            } else {
                document.addData(getNodeFromList(selectedNodes, node.getKey()));
            }
        }
        DocumentController documentController = new DocumentController();
        documentController.setDocument(document);
        try {
            documentController.exportData(file.getPath(), ApplicationUtils.XML_TYPE_ID);
        } catch (UnsupportedDataTypeException e) {
            e.printStackTrace();
        }
    }

    private static void mergeToChronological(File file, DocumentController firstDocument, ArrayList<Node> selectedNodes) {
        Document document = new Document();
        document.setTemplate(ApplicationUtils.CHRONOLOGICAL_TEMPLATE_ID);
        document.setPersonalInfo(firstDocument.getDocument().getPersonalInfo());

        for (Node node : firstDocument.getDocument().getData()) {
            if (!listContains(selectedNodes, node)) {
                document.addData(node);
            } else {
                document.addData(getNodeFromList(selectedNodes, node.getKey()));
            }
        }
        DocumentController documentController = new DocumentController();
        documentController.setDocument(document);
        try {
            documentController.exportData(file.getPath(), ApplicationUtils.XML_TYPE_ID);
        } catch (UnsupportedDataTypeException e) {
            e.printStackTrace();
        }
    }

    private static void mergeToCombined(File file, DocumentController firstDocument, ArrayList<Node> selectedNodes) {
        Document document = new Document();
        document.setTemplate(ApplicationUtils.COMBINED_TEMPLATE_ID);
        document.setPersonalInfo(firstDocument.getDocument().getPersonalInfo());

        for (Node node : firstDocument.getDocument().getData()) {
            if (!listContains(selectedNodes, node)) {
                document.addData(node);
            } else {
                document.addData(getNodeFromList(selectedNodes, node.getKey()));
            }
        }
        DocumentController documentController = new DocumentController();
        documentController.setDocument(document);
        try {
            documentController.exportData(file.getPath(), ApplicationUtils.XML_TYPE_ID);
        } catch (UnsupportedDataTypeException e) {
            e.printStackTrace();
        }
    }

    private static Node getNodeFromList(ArrayList<Node> list, String key) {
        for (Node node : list) {
            if (node.getKey().equals(key))
                return node;
        }
        return null;
    }

    private static boolean listContains(ArrayList<Node> list, Node element) {
        for (Node node : list) {
            if (node.getKey().equals(element.getKey()))
                return true;
        }
        return false;
    }


}
