package app.java;

import app.java.data.DataGenerator;
import app.java.utils.ApplicationUtils;
import app.java.utils.FileChooserUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.activation.UnsupportedDataTypeException;
import java.io.File;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.util.Optional;

public class EditorScene {
    private Stage mStage;
    private boolean openFile;
    private File file;
    private boolean canEdit;
    private Scene mScene;
    private boolean hasSavePath;
    private boolean saved;
    private DataGenerator dataGenerator;

    @FXML
    private TextField nameTextField, addressTextField, phoneTextField, mobileTextField, emailTextField, websiteTextField;
    @FXML
    private MenuBar menuBar;
    @FXML
    private ImageView avatarImageView;

    public EditorScene(boolean template, File file) {
        if (template){
            this.hasSavePath = false;
            this.saved = true;
            this.file = file;
        } else {
            this.hasSavePath = true;
            this.file = file;
        }
    }

    public Scene initialize(Stage stage) throws Exception {
        this.mStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource(ApplicationUtils.EDITOR_WINDOW_LAYOUT_FXML));
        mStage.setTitle(ApplicationUtils.APPLICATION_TITLE);
        mStage.setResizable(true);
        mStage.setMinHeight(500);
        mStage.setMinWidth(500);
        mStage.setOnCloseRequest(event -> closeAction());
        mScene = new Scene(root, ApplicationUtils.EDITOR_WINDOW_WIDTH, ApplicationUtils.EDITOR_WINDOW_HEIGHT);
        this.canEdit = true;
        initializeTextFields();
        initializeMenuItems();
        initializeImageView();
        openFile();
        return mScene;
    }

    private void initializeTextFields() {
        nameTextField = (TextField) mScene.lookup("#name");
        addressTextField = (TextField) mScene.lookup("#address");
        phoneTextField = (TextField) mScene.lookup("#home");
        mobileTextField = (TextField) mScene.lookup("#mobile");
        emailTextField = (TextField) mScene.lookup("#email");
        websiteTextField = (TextField) mScene.lookup("#website");
        toggleEdit();
    }

    private void initializeMenuItems() {
        menuBar = (MenuBar) mScene.lookup("#edit");
        initializeFileMenu();
        initializeEditMenu();
        initializeViewMenu();
        initializeHelpMenu();
    }

    private void initializeImageView() {
        avatarImageView = (ImageView) mScene.lookup("#imageView");
        avatarImageView.maxHeight(169);
        avatarImageView.maxHeight(169);
        avatarImageView.setOnMouseClicked(event -> {
            File file =  FileChooserUtils.openImageFileChooser(mStage);
            if (file == null) return;
            try {
                Image image = new Image(file.toURL().toString());
                avatarImageView.setImage(image);
                dataGenerator.setProfImage(image);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
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
        else{
            saveAction();
            mStage.close();
        }
    }

    private void newAction() {
        if (!saved){
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
            openFile();
        } catch (UnsupportedDataTypeException e) {
            e.printStackTrace();
        }
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
        if (!saved){
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
        try {
            dataGenerator.exportData(file.getPath(), ApplicationUtils.LATEX_TYPE_ID);
        } catch (UnsupportedDataTypeException e) {
            e.printStackTrace();
        }
    }

    private void saveFile() {
        saved = true;
        try {
            dataGenerator.exportData(file.getPath(), ApplicationUtils.XML_TYPE_ID);
        } catch (UnsupportedDataTypeException e) {
            e.printStackTrace();
        }
    }

    private void openFile() throws UnsupportedDataTypeException {
        dataGenerator = new DataGenerator();
        dataGenerator.importData(file.getPath(), ApplicationUtils.XML_TYPE_ID);
        showData();
    }

    private void showData() {
        showPersonalInfo();
        showProfImage();
        createCustomListView();
    }

    private void showProfImage() {
        Image image = dataGenerator.getProfImage();
        if (image == null)
            showDefaultImage();
        else
            avatarImageView.setImage(image);
    }

    private void showDefaultImage() {
        avatarImageView.setImage(new Image(getClass().getResource("../res/drawable/cv_logo.png").toString()));
    }

    private void showPersonalInfo() {
        try {
            nameTextField.setText(dataGenerator.queryPersonalInfo(ApplicationUtils.PERSONAL_INFO_NAME));
            addressTextField.setText(dataGenerator.queryPersonalInfo(ApplicationUtils.PERSONAL_INFO_ADDRESS));
            phoneTextField.setText(dataGenerator.queryPersonalInfo(ApplicationUtils.PERSONAL_INFO_HOME));
            mobileTextField.setText(dataGenerator.queryPersonalInfo(ApplicationUtils.PERSONAL_INFO_MOBILE));
            emailTextField.setText(dataGenerator.queryPersonalInfo(ApplicationUtils.PERSONAL_INFO_EMAIL));
            websiteTextField.setText(dataGenerator.queryPersonalInfo(ApplicationUtils.PERSONAL_INFO_WEBSITE));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    private void createCustomListView() {
        
    }

    /*
    private void createCustomListView() {
        ObservableList<Node> data = FXCollections.observableArrayList();
        data.addAll(new Node("Occupation", true), new Node("Experience", true));

        final ListView<Node> listView = new ListView<Node>(data);
        listView.setCellFactory(new Callback<ListView<Node>, ListCell<Node>>() {
            @Override
            public ListCell<Node> call(ListView<Node> param) {
                return new ListCell<Node>() {
                    @Override
                    protected void updateItem(Node item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            VBox vBox = new VBox(new Text(item.getKey()));
                            HBox hBox = new HBox(new Label("adsfga"));
                        }
                    }
                };
            }
        });
        AnchorPane anchorPane = (AnchorPane) m_scene.lookup("#anchor_pane");
        listView.setLayoutX(50);
        listView.setLayoutY(300);
        listView.setMinWidth(400);
        anchorPane.getChildren().add(listView);
    }
    */


}
