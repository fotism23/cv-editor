package app.java.exporters;

import app.java.data.ListNode;
import app.java.data.Node;
import app.java.utils.ApplicationUtils;
import de.nixosoft.jlr.JLRGenerator;
import de.nixosoft.jlr.JLROpener;
import javafx.scene.image.Image;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;

public class LatexExporter implements Exporter {

    private JLRGenerator generator;
    private StringBuilder docStringBuilder;
    private boolean makePdf = false;
    private String path;

    public LatexExporter(String path) {
        this.path = path;
        if (path.endsWith(ApplicationUtils.PDF_FILE_EXTENSION)){
            makePdf = true;
        }
        docStringBuilder = new StringBuilder();
        generator = new JLRGenerator();
        initDocument();
    }

    private void initDocument() {
        docStringBuilder.append("\\documentclass[11pt,a4paper,sans]{moderncv} \n");
        docStringBuilder.append("\\moderncvstyle{banking}\n");
        docStringBuilder.append("\\moderncvcolor{blue}\n");
        docStringBuilder.append("\\usepackage[utf8]{inputenc}\n");
        docStringBuilder.append("\\usepackage[scale=0.75]{geometry}\n");
        docStringBuilder.append("\\usepackage{import}\n");
    }

    @Override
    public void exportImage(Image image) {

    }

    @Override
    public void exportPersonalInfo(HashMap<String, String> hashMap) {
        appendName(hashMap.get(ApplicationUtils.PERSONAL_INFO_NAME));
        appendAddress(hashMap.get(ApplicationUtils.PERSONAL_INFO_ADDRESS));
        appendHomePhone(hashMap.get(ApplicationUtils.PERSONAL_INFO_HOME));
        appendMobilePhone(hashMap.get(ApplicationUtils.PERSONAL_INFO_MOBILE));
        appendEmail(hashMap.get(ApplicationUtils.PERSONAL_INFO_EMAIL));
        appendWebsite(hashMap.get(ApplicationUtils.PERSONAL_INFO_WEBSITE));

        beginDocument();
    }

    private void beginDocument() {
        docStringBuilder.append("\\begin{document}\n");
        docStringBuilder.append("\\makecvtitle\n");
    }

    private void appendName(String name) {
        docStringBuilder.append("\\name{}{");
        docStringBuilder.append(name);
        docStringBuilder.append("}\n");
        docStringBuilder.append("\\title{Curriculum Vitae}\n");
    }

    private void appendAddress(String address) {
        docStringBuilder.append("\\address{");
        docStringBuilder.append(address);
        docStringBuilder.append("}{}{}\n");
    }

    private void appendHomePhone(String homePhone) {
        docStringBuilder.append("\\phone[fixed]{");
        docStringBuilder.append(homePhone);
        docStringBuilder.append("}\n");
    }

    private void appendMobilePhone(String mobilePhone) {
        docStringBuilder.append("\\phone[mobile]{");
        docStringBuilder.append(mobilePhone);
        docStringBuilder.append("}\n");
    }

    private void appendEmail(String email) {
        docStringBuilder.append("\\email{");
        docStringBuilder.append(email);
        docStringBuilder.append("}\n");
    }

    private void appendWebsite(String website) {
        docStringBuilder.append("\\homepage{");
        docStringBuilder.append(website);
        docStringBuilder.append("}\n");
    }

    private void appendChildren(ListNode node) {
        ArrayList<Node> nodes = node.getChildren();
        if (nodes.isEmpty()) return;
        for (Node childNode : nodes) {
            docStringBuilder.append("\\item{}{");
            docStringBuilder.append(childNode.getValue());
            docStringBuilder.append("\t");
            docStringBuilder.append(ApplicationUtils.getStringFormattedDate(node.getDate()));
            docStringBuilder.append("}\n");//
            docStringBuilder.append("\\begin{itemize}\n");
            appendChildren((ListNode) childNode);
            docStringBuilder.append("\\end{itemize}\n");
            //docStringBuilder.append("}\n");
        }
    }

    private void appendListSection(Node node) {
        docStringBuilder.append("\\section{");
        docStringBuilder.append(node.getValue());
        docStringBuilder.append("}\n");
        docStringBuilder.append("\\vspace{6pt}\n");
        docStringBuilder.append("\\begin{itemize}\n");
        appendChildren((ListNode) node);
        docStringBuilder.append("\\end{itemize}\n");
        docStringBuilder.append("\\vspace{8pt}\n");
    }

    private void appendSection(Node node) {
        docStringBuilder.append("\\section{");
        docStringBuilder.append(node.getValue());
        docStringBuilder.append("}\n");
        docStringBuilder.append("\\vspace{6pt}\n");
        //docStringBuilder.append("\\begin{itemize}\n");
        docStringBuilder.append("\\item{}{");
        docStringBuilder.append(node.getContent());
        docStringBuilder.append("}");
        docStringBuilder.append("\\vspace{8pt}\n");
        //docStringBuilder.append("\\end{itemize}\n");
    }

    @Override
    public void exportProfessionalProfile(Node node) {
        appendSection(node);
    }

    @Override
    public void exportSkillsAndExperience(Node node) {
        appendListSection(node);
    }

    @Override
    public void exportCareerSummary(Node node) {
        appendListSection(node);
    }

    @Override
    public void exportEducationAndTraining(Node node) {
        appendListSection(node);
    }

    @Override
    public void exportFurtherCourses(Node node) {
        appendListSection(node);
    }

    @Override
    public void exportAdditionalInfo(Node node) {
        appendSection(node);
    }

    @Override
    public void exportInterests(Node node) {
        appendSection(node);
    }

    @Override
    public void exportCoreStrengths(Node node) {
        appendSection(node);
    }

    @Override
    public void exportProfessionalExperience(Node node) {
        appendListSection(node);
    }

    @Override
    public void commit() {
        docStringBuilder.append("\\end{document}");
        generateTexFile();
        generateTempFile();
    }

    private void generateTexFile() {
        try {
            PrintWriter pr;
            if (!makePdf)
                pr = new PrintWriter(new FileOutputStream(path));
            else
                pr = new PrintWriter(new FileOutputStream(ApplicationUtils.TEX_TEMP_DIRECTORY + "temp.tex"));
            pr.print(docStringBuilder.toString());
            pr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void generatePdf() {
        File tempFile = generator.getPDF();
        File pdf = new File(path);
        try {
            Files.copy(tempFile.toPath(), pdf.toPath(), StandardCopyOption.REPLACE_EXISTING);
            JLROpener.open(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateTempFile() {
        try {
            generator.generate(new File(ApplicationUtils.TEX_TEMP_DIRECTORY + "temp.tex"),
                    new File(ApplicationUtils.TEX_TEMP_DIRECTORY),
                    new File(ApplicationUtils.TEX_TEMP_DIRECTORY));
            if (makePdf)
                generatePdf();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}