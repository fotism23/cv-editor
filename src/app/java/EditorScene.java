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
            this.file = null;
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
        //createCustomListView();
        this.canEdit = true;
        initializeTextFields();
        initializeMenuItems();
        initializeImageView();
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

    private void initializeImageView() {
        avatarImageView = (ImageView) mScene.lookup("#imageView");
        avatarImageView.maxHeight(169);
        avatarImageView.maxHeight(169);
        avatarImageView.setOnMouseClicked(event -> {
            File file =  FileChooserUtils.openImageFileChooser(mStage);
            if (file == null) return;
            try {
                avatarImageView.setImage(new Image(file.toURL().toString()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
    }

    private void initializeMenuItems() {
        menuBar = (MenuBar) mScene.lookup("#edit");
        initializeFileMenu();
        initializeEditMenu();
        initializeViewMenu();
        initializeHelpMenu();
    }

    private void initializeFileMenu() {
        MenuItem newMenuItem = new CheckMenuItem("New");
        // todo menu item listener
        MenuItem openMenuItem = new CheckMenuItem("Open");
        openMenuItem.setOnAction(event -> openAction());

        MenuItem saveMenuItem = new CheckMenuItem("Save");
        saveMenuItem.setOnAction(event -> saveAction());

        MenuItem saveAsMenuItem = new CheckMenuItem("Save As");
        saveAsMenuItem.setOnAction(event -> saveAsAction());

        MenuItem exportMenuItem = new CheckMenuItem("Export");
        exportMenuItem.setOnAction(event -> exportAction());

        MenuItem closeMenuItem = new CheckMenuItem("Close");
        closeMenuItem.setOnAction(event -> closeAction());

        menuBar.getMenus().get(0).getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, exportMenuItem, closeMenuItem);
    }

    private void closeAction() {
        if (saved) mStage.close();
        else{
            saveAction();
            mStage.close();
        }
    }

    private void initializeViewMenu() {
        MenuItem aboutMenuItem = new CheckMenuItem("About");
        aboutMenuItem.setOnAction(event -> ApplicationUtils.showAboutDialog());
        menuBar.getMenus().get(3).getItems().add(aboutMenuItem);
    }

    private void initializeHelpMenu() {

    }

    private void initializeEditMenu() {
        CheckMenuItem toggleEdit = new CheckMenuItem("Toggle Edit");
        toggleEdit.setSelected(canEdit);
        //toggleEdit.set
        toggleEdit.selectedProperty().addListener((observable, oldValue, newValue) -> toggleEdit());
        menuBar.getMenus().get(1).getItems().addAll(toggleEdit);
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

    }

    private void saveFile() {
        saved = true;
    }

    private void openFile() throws UnsupportedDataTypeException {
        dataGenerator = new DataGenerator(file.getPath(), ApplicationUtils.XML_TYPE_ID);
        showData();
    }


    private void showData() {
        createCustomListView();
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
