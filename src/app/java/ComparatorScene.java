package app.java;


import app.java.data.ListNode;
import app.java.data.Node;
import app.java.utils.ApplicationUtils;
import app.java.utils.FileChooserUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.activation.UnsupportedDataTypeException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class ComparatorScene {
    private Stage mStage;
    private File leftFile;
    private File rightFile;
    private int tabMultiplier = 1;
    private String name;
    private boolean select = true;
    private Map<String, Object> namespace;
    private Label nameLabel;
    private HashMap<String, Node> valueNodeMap;
    private ArrayList<Node> selectedNodes;
    private DocumentController leftDocumentController;
    private DocumentController rightDocumentController;

    ComparatorScene(File leftFile, File rightFile) {
        this.leftFile = leftFile;
        this.rightFile = rightFile;
    }

    Scene initialize(Stage stage) throws Exception {
        this.mStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ApplicationUtils.COMPARATOR_WINDOW_LAYOUT_FXML));
        Parent root = loader.load();
        namespace = loader.getNamespace();
        mStage.setTitle(ApplicationUtils.APPLICATION_TITLE);
        mStage.setResizable(true);
        mStage.setMinHeight(ApplicationUtils.EDITOR_WINDOW_HEIGHT);
        mStage.setMinWidth(ApplicationUtils.EDITOR_WINDOW_WIDTH);
        // mStage.setOnCloseRequest(event -> closeAction());
        Scene mScene = new Scene(root, ApplicationUtils.EDITOR_WINDOW_WIDTH, ApplicationUtils.EDITOR_WINDOW_HEIGHT);
        initializeFields();
        openFiles();
        return mScene;
    }

    private void initializeFields() {
        nameLabel = (Label) namespace.get("nameLabel");
        Button mergeButton = (Button) namespace.get("mergeButton");
        mergeButton.setOnAction(event -> mergeAction());
        selectedNodes = new ArrayList<>();
        valueNodeMap = new HashMap<>();
    }

    private void openFiles() throws UnsupportedDataTypeException {
        leftDocumentController = new DocumentController();
        rightDocumentController = new DocumentController();
        leftDocumentController.importData(leftFile.getPath(), ApplicationUtils.XML_TYPE_ID);
        rightDocumentController.importData(rightFile.getPath(), ApplicationUtils.XML_TYPE_ID);
        checkIfIsComparable();
        showData();
    }

    private void checkIfIsComparable() {
        if (!leftDocumentController.getDocument().queryPersonalInfo(ApplicationUtils.PERSONAL_INFO_NAME)
                .equals(rightDocumentController.getDocument().queryPersonalInfo(ApplicationUtils.PERSONAL_INFO_NAME))) {
            showNotAvailableForComparisonPopup();
            System.exit(0);
        }
        name = leftDocumentController.getDocument().queryPersonalInfo(ApplicationUtils.PERSONAL_INFO_NAME);
    }

    private void showData() {
        nameLabel.setText(name);
        showSections();
    }

    private void showSections() {
        showLeftSection();
        resetTabMultiplier();
        select = false;
        showRightSection();
    }

    private void resetTabMultiplier() {
        tabMultiplier = 1;
    }

    private void showLeftSection() {
        AnchorPane leftAnchorPane = (AnchorPane) namespace.get("leftAnchorPane");
        leftAnchorPane.setFocusTraversable(false);
        leftAnchorPane.setPadding(new Insets(0, 0, 30, 0));

        ArrayList<Node> leftDocumentData = leftDocumentController.getDocument().getData();

        VBox leftDataList = new VBox();
        leftDataList.setLayoutY(30);
        leftDataList.setLayoutX(30);
        leftDataList.setMinWidth(400);
        leftDataList.setSpacing(8);

        for (Node node : leftDocumentData) {
            leftDataList.getChildren().add(getCell(node, true));
        }
        leftAnchorPane.getChildren().add(leftDataList);
    }

    private void showRightSection() {
        AnchorPane rightAnchorPane = (AnchorPane) namespace.get("rightAnchorPane");
        rightAnchorPane.setFocusTraversable(false);
        rightAnchorPane.setPadding(new Insets(0, 0, 30, 0));

        ArrayList<Node> rightDocumentData = rightDocumentController.getDocument().getData();

        VBox rightDataList = new VBox();
        rightDataList.setLayoutY(30);
        rightDataList.setLayoutX(30);
        rightDataList.setMinWidth(400);
        rightDataList.setSpacing(8);

        for (Node node : rightDocumentData) {
            rightDataList.getChildren().add(getCell(node, true));
        }
        rightAnchorPane.getChildren().add(rightDataList);
    }

    private VBox getCell(Node node, boolean isSelectable) {
        VBox cell = new VBox();
        HBox header;
        if (isSelectable)
            header = getSelectableHeader(node);
        else
            header = getHeader(node);

        cell.getChildren().add(header);
        cell.getChildren().add(getContent(node));
        cell.setSpacing(4);
        return cell;
    }

    private HBox getHeader(Node node) {
        HBox headerHBox = new HBox(node.getKeyLabel(), new Label(node.getValue()));

        headerHBox.setSpacing(10);
        headerHBox.setLayoutX(ApplicationUtils.TAB_SIZE * tabMultiplier);
        return headerHBox;
    }

    private HBox getSelectableHeader(Node node) {
        CheckBox checkBox = new CheckBox();
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) selectedNodes.add(node);
            else selectedNodes.remove(node);
        });
        HBox headerHBox = new HBox(checkBox, node.getKeyLabel(), new Label(node.getValue()));
        headerHBox.setSpacing(10);
        headerHBox.setLayoutX(ApplicationUtils.TAB_SIZE * tabMultiplier);
        return headerHBox;
    }

    private HBox getContent(Node node) {
        HBox contentHBox = new HBox();
        contentHBox.getChildren().add(new Label("\t"));

        if (getAdditionalData(node) != null)
            contentHBox.getChildren().add(getAdditionalData(node));
        return contentHBox;
    }

    private VBox getAdditionalData(Node parentNode) {
        if (parentNode instanceof ListNode)
            return getNodeChildren((ListNode) parentNode);
        else
            return getNodeContent(parentNode);
    }

    private VBox getNodeChildren(ListNode parentNode) {
        VBox vBox = new VBox();
        ArrayList<Node> data = parentNode.getChildren();
        tabMultiplier++;
        for (Node node : data) {
            if (node.getKey().length() == 3 && select)
                valueNodeMap.put(node.getValue(), node);
            else if (!select && valueNodeMap.containsKey(node.getValue()))
                vBox.setStyle("-fx-background-color: #ffc0cb;");

            vBox.getChildren().add(getCell(node, false));
        }
        tabMultiplier--;
        return vBox;
    }

    private VBox getNodeContent(Node parentNode) {
        TextArea textArea = new TextArea(parentNode.getContent());
        if (select)
            valueNodeMap.put(parentNode.getValue(), parentNode);
        else if (!select && valueNodeMap.containsKey(parentNode.getValue()))
            textArea.setStyle("-fx-background-color: #ffc0cb;");

        textArea.setEditable(false);
        textArea.setPrefHeight(50);
        return new VBox(textArea);
    }

    private void showNotAvailableForComparisonPopup() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Different Names");
        alert.setHeaderText(null);
        alert.setContentText("The documents that you have selected belong to different individuals!");
        alert.getDialogPane().getStylesheets().add(Main.class.getResource("../res/styles/dialog_style.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("myDialog");
        alert.showAndWait();
    }

    private void mergeAction() {
        File target = getTargetFile();
        if (null == target) return;
        Merger.merge(target, leftDocumentController, rightDocumentController, selectedNodes);

    }

    private File getTargetFile() {
        return FileChooserUtils.saveAsFileChooser(mStage);
    }
}
