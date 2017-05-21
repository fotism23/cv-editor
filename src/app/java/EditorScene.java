package app.java;

import app.java.data.ListNode;
import app.java.data.Node;
import app.java.data.NodeFactory;
import app.java.utils.ApplicationUtils;
import app.java.utils.FileChooserUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.activation.UnsupportedDataTypeException;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class EditorScene {
    private Stage mStage;
    private File file;
    private boolean canEdit;
    private boolean hasSavePath;
    private boolean saved;
    private DocumentController documentController;
    private ArrayList<Node> data;
    private VBox dataList;
    private int tabMultiplier = 1;
    private ContextMenu contextMenu;
    private String itemSelected;
    private Map<String, Object> namespace;

    @FXML
    private TextField nameTextField, addressTextField, phoneTextField, mobileTextField, emailTextField, websiteTextField;
    @FXML
    private MenuBar menuBar;
    @FXML
    private ImageView avatarImageView;

    EditorScene(boolean template, File file) {
        if (template) {
            this.hasSavePath = false;
            this.saved = true;
            this.file = file;
        } else {
            this.hasSavePath = true;
            this.file = file;
        }
    }

    Scene initialize(Stage stage) throws Exception {
        this.mStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ApplicationUtils.EDITOR_WINDOW_LAYOUT_FXML));
        Parent root = loader.load();
        namespace = loader.getNamespace();
        mStage.setTitle(ApplicationUtils.APPLICATION_TITLE);
        mStage.setResizable(true);
        mStage.setMinHeight(ApplicationUtils.EDITOR_WINDOW_HEIGHT);
        mStage.setMinWidth(ApplicationUtils.EDITOR_WINDOW_WIDTH);
        mStage.setOnCloseRequest(event -> closeAction());
        Scene mScene = new Scene(root, ApplicationUtils.EDITOR_WINDOW_WIDTH, ApplicationUtils.EDITOR_WINDOW_HEIGHT);

        this.canEdit = true;
        initializeFields();
        initializeMenuItems();
        initializeImageView();
        openFile();
        return mScene;
    }

    private void initializeFields() {
        nameTextField = (TextField) namespace.get("name");
        addressTextField = (TextField) namespace.get("address");
        phoneTextField = (TextField) namespace.get("home");
        mobileTextField = (TextField) namespace.get("mobile");
        emailTextField = (TextField) namespace.get("email");
        websiteTextField = (TextField) namespace.get("website");
        toggleEdit();
    }

    private void initializeMenuItems() {
        menuBar = (MenuBar) namespace.get("editMenu");
        initializeFileMenu();
        initializeEditMenu();
        initializeViewMenu();
        initializeHelpMenu();
    }

    private void initializeImageView() {
        avatarImageView = (ImageView) namespace.get("imageView");
        avatarImageView.maxHeight(169);
        avatarImageView.maxHeight(169);
        avatarImageView.setOnMouseClicked(event -> {
            File file = FileChooserUtils.openImageFileChooser(mStage);
            if (file == null) return;
            Image image = new Image("file:///" + file.toString());
            avatarImageView.setImage(image);
            documentController.getDocument().setProfImage(image);
        });
    }

    private void initializeFileMenu() {
        MenuItem newMenuItem = new MenuItem("New");
        newMenuItem.setOnAction(event -> newAction());

        MenuItem openMenuItem = new MenuItem("Open");
        openMenuItem.setOnAction(event -> openAction());

        MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.setOnAction(event -> saveAction());

        MenuItem saveAsMenuItem = new MenuItem("Save As");
        saveAsMenuItem.setOnAction(event -> saveAsAction());

        MenuItem exportMenuItem = new MenuItem("Export");
        exportMenuItem.setOnAction(event -> exportAction());

        MenuItem closeMenuItem = new MenuItem("Close");
        closeMenuItem.setOnAction(event -> closeAction());

        menuBar.getMenus().get(0).getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, exportMenuItem, closeMenuItem);
    }

    private void initializeEditMenu() {
        CheckMenuItem toggleEdit = new CheckMenuItem("Toggle Edit");
        toggleEdit.setSelected(canEdit);
        toggleEdit.selectedProperty().addListener((observable, oldValue, newValue) -> toggleEdit());
        menuBar.getMenus().get(1).getItems().addAll(toggleEdit);
    }

    private void initializeViewMenu() {

    }

    private void initializeHelpMenu() {
        MenuItem aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setOnAction(event -> ApplicationUtils.showAboutDialog());
        menuBar.getMenus().get(3).getItems().add(aboutMenuItem);
    }

    private void closeAction() {
        if (saved) mStage.close();
        else {
            saveAction();
            mStage.close();
        }
    }

    private void newAction() {
        if (!saved) {
            saveAction();
        }
        Optional<String> result = ApplicationUtils.showTemplateOptionDialog();
        if (result.isPresent()) {
            switch (result.get()) {
                case "Functional CV":
                    this.file = new File(ApplicationUtils.FUNCTIONAL_TEMPLATE_PATH);
                    break;
                case "Combined CV":
                    this.file = new File(ApplicationUtils.COMBINED_TEMPLATE_PATH);
                    break;
                case "Chronological CV":
                    this.file = new File(ApplicationUtils.CHRONOLOGICAL_TEMPLATE_PATH);
                    break;
                default:
                    throw new UnsupportedOperationException("This template type is not supported.");
            }
        }
        if (file == null) return;
        hasSavePath = false;
        try {
            clearList();
            openFile();
        } catch (UnsupportedDataTypeException e) {
            e.printStackTrace();
        }
    }

    private void clearList() {

    }

    private void toggleEdit() {
        canEdit = !canEdit;
        saved = false;
        nameTextField.setEditable(canEdit);
        addressTextField.setEditable(canEdit);
        phoneTextField.setEditable(canEdit);
        mobileTextField.setEditable(canEdit);
        emailTextField.setEditable(canEdit);
        websiteTextField.setEditable(canEdit);
    }

    private void openAction() {
        if (!saved) {
            saveAction();
        }
        File file = FileChooserUtils.openFileChooser(mStage);
        if (null == file) return;
        try {
            openFile();
        } catch (UnsupportedDataTypeException e) {
            e.printStackTrace();
        }
    }

    private void saveAction() {
        if (!hasSavePath) {
            saveAsAction();
            return;
        }
        saveFile();
    }

    private void saveAsAction() {
        File file = FileChooserUtils.saveAsFileChooser(mStage);
        if (file != null) {
            this.file = file;
            hasSavePath = true;
            saveFile();
        }
    }

    private void exportAction() {
        File file = FileChooserUtils.exportAsFileChooser(mStage);
        if (file == null) return;
        if (file.getPath().endsWith(ApplicationUtils.TEXT_FILE_EXTENSION)) {
            try {
                documentController.exportData(file.getPath(), ApplicationUtils.TEXT_TYPE_ID);
            } catch (UnsupportedDataTypeException e) {
                e.printStackTrace();
            }
        } else if (file.getPath().endsWith(ApplicationUtils.PDF_FILE_EXTENSION)){
            try {
                documentController.exportData(file.getPath(), ApplicationUtils.LATEX_TYPE_ID);
            } catch (UnsupportedDataTypeException e) {
                e.printStackTrace();
            }
        } else {
            try {
                documentController.exportData(file.getPath(), ApplicationUtils.LATEX_TYPE_ID);
            } catch (UnsupportedDataTypeException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile() {
        saved = true;
        try {
            documentController.exportData(file.getPath(), ApplicationUtils.XML_TYPE_ID);
        } catch (UnsupportedDataTypeException e) {
            e.printStackTrace();
        }
    }

    private void openFile() throws UnsupportedDataTypeException {
        documentController = new DocumentController();
        documentController.importData(file.getPath(), ApplicationUtils.XML_TYPE_ID);
        showData();
    }

    private void showData() {
        showPersonalInfo();
        showProfImage();
        createEditorFields();
    }

    private void showProfImage() {
        Image image = documentController.getDocument().getProfImage();
        if (image == null)
            showDefaultImage();
        else
            avatarImageView.setImage(image);
    }

    private void showDefaultImage() {
        avatarImageView.setImage(new Image(getClass().getResource("../res/drawable/cv_logo.png").toString()));
    }

    private void showPersonalInfo() {
        nameTextField.setText(documentController.getDocument().queryPersonalInfo(ApplicationUtils.PERSONAL_INFO_NAME));
        addressTextField.setText(documentController.getDocument().queryPersonalInfo(ApplicationUtils.PERSONAL_INFO_ADDRESS));
        phoneTextField.setText(documentController.getDocument().queryPersonalInfo(ApplicationUtils.PERSONAL_INFO_HOME));
        mobileTextField.setText(documentController.getDocument().queryPersonalInfo(ApplicationUtils.PERSONAL_INFO_MOBILE));
        emailTextField.setText(documentController.getDocument().queryPersonalInfo(ApplicationUtils.PERSONAL_INFO_EMAIL));
        websiteTextField.setText(documentController.getDocument().queryPersonalInfo(ApplicationUtils.PERSONAL_INFO_WEBSITE));
    }

    private void createEditorFields() {
        AnchorPane anchorPane = (AnchorPane) namespace.get("anchorPane");
        anchorPane.setFocusTraversable(false);
        anchorPane.setPadding(new Insets(0, 0, 30, 0));

        data = documentController.getDocument().getData();
        dataList = new VBox();
        dataList.setLayoutY(300);
        dataList.setLayoutX(50);
        dataList.setMinWidth(500);
        dataList.setSpacing(8);

        for (Node node : data)
            dataList.getChildren().add(getCell(node, false));

        anchorPane.getChildren().add(dataList);
    }

    private void addItem() {
        showAddPopup();
        refreshList();
    }

    private void removeItem() {
        documentController.getDocument().removeElement(itemSelected);
        refreshList();
    }

    private void refreshList() {
        dataList.getChildren().clear();
        data = documentController.getDocument().getData();
        for (Node node : data)
            dataList.getChildren().add(getCell(node, false));

    }

    private void initializeCellContextMenu() {
        this.contextMenu = new ContextMenu();
        MenuItem addMenuItem = new MenuItem("Add");
        addMenuItem.setOnAction(event -> addItem());
        MenuItem removeMenuItem = new MenuItem("Remove");
        removeMenuItem.setOnAction(event -> removeItem());

        this.contextMenu.getItems().addAll(addMenuItem, removeMenuItem);
    }

    private VBox getCell(Node node, boolean isHeaderEditable) {
        initializeCellContextMenu();
        VBox cell = new VBox();
        HBox header;

        if (!isHeaderEditable)
            header = getHeader(node);
        else
            header = getHeader(node);

        setEditListener(header, node);
        setContextMenuListener(header, node);
        cell.getChildren().add(header);
        cell.getChildren().add(getContent(node));
        cell.setSpacing(4);
        return cell;
    }

    private void setEditListener(HBox header, Node node) {
        header.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (!canEdit)
                    return;
                itemSelected = node.getKey();
                showEditPopup(itemSelected);
            }
        });
    }

    private Stage initializePopup(String title) {
        Stage stage = new Stage();
        stage.setTitle(title);

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mStage);
        stage.setHeight(300);
        stage.setWidth(300);
        stage.setResizable(false);
        return stage;
    }

    private void showAddPopup() {
        Stage stage = initializePopup("Insert Info");

        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setAlignment(Pos.CENTER);

        String key = documentController.getDocument().getNextAvailableKey(itemSelected);
        Label keyLabel = new Label(key);
        TextArea textContent = new TextArea("Value");

        DatePicker datePicker = new DatePicker();

        if (documentController.getDocument().getTemplate() == ApplicationUtils.CHRONOLOGICAL_TEMPLATE_ID) {
            LocalDate lastDate = documentController.getDocument().getRangeOfNotAvailableDates(itemSelected);
            datePicker.setDayCellFactory(ApplicationUtils.getDateCellFactory(lastDate));
        }

        ToggleGroup drawableSelectionGroup = new ToggleGroup();

        RadioButton stringDrawableRadioButton = new RadioButton("Text key");
        stringDrawableRadioButton.setSelected(true);
        stringDrawableRadioButton.setUserData(-1);
        stringDrawableRadioButton.setToggleGroup(drawableSelectionGroup);

        RadioButton whiteDotDrawableRadioButton = new RadioButton("White dot");
        whiteDotDrawableRadioButton.setToggleGroup(drawableSelectionGroup);
        whiteDotDrawableRadioButton.setUserData(Node.WHITE_DOT_DRAWABLE_ID);

        RadioButton blackDotDrawableRadioButton = new RadioButton("Black dot");
        blackDotDrawableRadioButton.setUserData(Node.BLACK_DOT_DRAWABLE_ID);
        blackDotDrawableRadioButton.setToggleGroup(drawableSelectionGroup);

        Button addButton = new Button("Add");
        addButton.setDisable(true);

        final LocalDate[] datePicked = {LocalDate.now()};
        final int[] drawableId = {-1};
        final boolean[] keyVisibility = {true};

        datePicker.valueProperty().addListener(((observable, oldValue, newValue) -> {
            datePicked[0] = newValue;
            addButton.setDisable(false);
        }
        ));

        addButton.setOnMouseClicked(event -> {
            if (drawableSelectionGroup.getSelectedToggle() != null) {
                switch ((int) drawableSelectionGroup.getSelectedToggle().getUserData()) {
                    case -1:
                        keyVisibility[0] = true;
                        break;
                    case Node.WHITE_DOT_DRAWABLE_ID:
                        keyVisibility[0] = false;
                        drawableId[0] = Node.WHITE_DOT_DRAWABLE_ID;
                        break;
                    case Node.BLACK_DOT_DRAWABLE_ID:
                        keyVisibility[0] = false;
                        drawableId[0] = Node.BLACK_DOT_DRAWABLE_ID;
                        break;
                }
            }

            ListNode nodeToAdd = NodeFactory.createNewListNode(key, textContent.getText(), datePicked[0]);
            nodeToAdd.setKeyVisibility(keyVisibility[0]);
            nodeToAdd.setLabelDrawableId(drawableId[0]);
            documentController.getDocument().addElement(itemSelected, nodeToAdd);
            stage.close();
            refreshList();
        });

        vBox.getChildren().addAll(keyLabel, textContent, new Label("Select Date"), datePicker, new HBox(stringDrawableRadioButton, whiteDotDrawableRadioButton, blackDotDrawableRadioButton));
        vBox.getChildren().add(addButton);

        Scene scene = new Scene(vBox, 100, 100);
        scene.getStylesheets().add(getClass().getResource("../res/styles/dialog_style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void showEditPopup(String itemSelected) {
        Stage stage = initializePopup("Edit Info");
        stage.setTitle("Edit Info");

        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setAlignment(Pos.CENTER);

        Node node = documentController.getDocument().queryNode(itemSelected);

        TextArea textContent = new TextArea(node.getValue());
        DatePicker datePicker = new DatePicker();
        Button editButton = new Button("Edit");

        vBox.getChildren().addAll(new Label(node.getKey()), textContent, new Label("Select Date"), datePicker);
        vBox.getChildren().add(editButton);

        editButton.setOnMouseClicked(event -> {
            node.setValue(textContent.getText());
            stage.close();
            refreshList();
        });

        Scene scene = new Scene(vBox, 100, 100);
        scene.getStylesheets().add(getClass().getResource("../res/styles/dialog_style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void setContextMenuListener(HBox header, Node node) {
        header.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            if (!canEdit)
                return;
            contextMenu.hide();
            if (event.getButton().equals(MouseButton.SECONDARY)) {
                contextMenu.show(mStage, event.getScreenX(), event.getScreenY());
                itemSelected = node.getKey();
            }
        });
    }

    private HBox getContent(Node node) {
        HBox contentHBox = new HBox();
        contentHBox.getChildren().add(new Label("\t"));

        if (getAdditionalData(node) != null)
            contentHBox.getChildren().add(getAdditionalData(node));
        return contentHBox;
    }

    private HBox getHeader(Node node) {
        HBox headerHBox = new HBox(node.getKeyLabel(), new Label(node.getValue()));
        headerHBox.setSpacing(10);
        headerHBox.setLayoutX(ApplicationUtils.TAB_SIZE * tabMultiplier);
        return headerHBox;
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
            vBox.getChildren().add(getCell(node, true));
        }
        tabMultiplier--;
        return vBox;
    }

    private VBox getNodeContent(Node parentNode) {
        TextArea textArea = new TextArea(parentNode.getContent());
        textArea.textProperty().addListener((observable, oldValue, newValue) -> parentNode.setContent(textArea.getText()));
        textArea.setPrefHeight(50);
        return new VBox(textArea);
    }
}
