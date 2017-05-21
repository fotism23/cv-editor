package app.java.exporters;

import app.java.data.ListNode;
import app.java.data.Node;
import app.java.utils.ApplicationUtils;
import javafx.scene.image.Image;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TextExporter implements Exporter{

    private StringBuilder stringBuilder;
    private int tabs = 0;
    private BufferedWriter bufferedWriter;
    private FileWriter fileWriter;

    public TextExporter(String path) {
        stringBuilder = new StringBuilder();
        try {
            fileWriter = new FileWriter(path);
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportImage(Image image) {
        /*
        Export Image is not available in text format.
         */
    }

    @Override
    public void exportPersonalInfo(HashMap<String, String> hashMap) {
        stringBuilder.append(1);
        stringBuilder.append(".\t GENERAL INFORMATION\n");

        stringBuilder.append("Name: ");
        stringBuilder.append(hashMap.get(ApplicationUtils.PERSONAL_INFO_NAME));
        stringBuilder.append("\n");

        stringBuilder.append("Address: ");
        stringBuilder.append(hashMap.get(ApplicationUtils.PERSONAL_INFO_ADDRESS));
        stringBuilder.append("\n");

        stringBuilder.append("Telephone(Home): ");
        stringBuilder.append(hashMap.get(ApplicationUtils.PERSONAL_INFO_HOME));
        stringBuilder.append("\t");

        stringBuilder.append("(Mobile): ");
        stringBuilder.append(hashMap.get(ApplicationUtils.PERSONAL_INFO_MOBILE));
        stringBuilder.append("\n");

        stringBuilder.append("Email: ");
        stringBuilder.append(hashMap.get(ApplicationUtils.PERSONAL_INFO_EMAIL));
        stringBuilder.append("\n");
        stringBuilder.append("\n");
    }

    @Override
    public void exportProfessionalProfile(Node node) {
        appendContentNodeData(node);
    }

    @Override
    public void exportSkillsAndExperience(Node node) {
        appendNodeData(node);
    }

    @Override
    public void exportCareerSummary(Node node) {
        appendNodeData(node);
    }

    @Override
    public void exportEducationAndTraining(Node node) {
        appendNodeData(node);
    }

    @Override
    public void exportFurtherCourses(Node node) {
        appendNodeData(node);
    }

    @Override
    public void exportAdditionalInfo(Node node) {
        appendContentNodeData(node);
    }

    @Override
    public void exportInterests(Node node) {
        appendContentNodeData(node);
    }

    @Override
    public void exportCoreStrengths(Node node) {
        appendContentNodeData(node);
    }

    @Override
    public void exportProfessionalExperience(Node node) {
        appendNodeData(node);
    }

    @Override
    public void commit() {
        try {
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.close();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void appendNodeData(Node node) {
        stringBuilder.append(node.getKey());
        stringBuilder.append(". ");
        stringBuilder.append(node.getValue());
        stringBuilder.append("\t");
        stringBuilder.append(ApplicationUtils.getStringFormattedDate(node.getDate()));
        stringBuilder.append("\n");
        appendChildren((ListNode) node);
        stringBuilder.append("\n");
    }

    private void appendContentNodeData(Node node) {
        stringBuilder.append(node.getKey());
        stringBuilder.append(". ");
        stringBuilder.append(node.getValue());
        stringBuilder.append("\n");
        stringBuilder.append(node.getContent());
        stringBuilder.append("\n");
    }

    private void appendChildren(ListNode node) {
        ArrayList<Node> nodes = node.getChildren();
        if (nodes.isEmpty()) return;
        tabs ++;
        for (Node childNode : nodes) {
            for (int i = 0; i < tabs; i++) {
                stringBuilder.append("\t");
            }
            stringBuilder.append(childNode.getKey());
            stringBuilder.append("\t");
            stringBuilder.append(childNode.getValue());
            stringBuilder.append("\n");
            appendChildren((ListNode) childNode);
            stringBuilder.append("\n");
        }
        tabs--;
    }

}
