package app.java.utils;

import app.java.InitScene;
import app.java.Main;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.codec.binary.Base64;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public final class ApplicationUtils {
    public static final String APPLICATION_TITLE = "CV Editor";
    public static final String APPLICATION_ICON_PATH = "../res/drawable/cv_icon.png";

    public static final int INIT_WINDOW_HEIGHT = 400;
    public static final int INIT_WINDOW_WIDTH = 600;
    public static final String INIT_WINDOW_LAYOUT_FXML = "../res/layout/init_window.fxml";

    public static final int EDITOR_WINDOW_WIDTH = 900;
    public static final int EDITOR_WINDOW_HEIGHT = 900;

    public static final String EDITOR_WINDOW_LAYOUT_FXML = "../res/layout/cveditor_window.fxml";
    public static final String COMPARATOR_WINDOW_LAYOUT_FXML = "../res/layout/comparator_window.fxml";

    public static final String BLACK_DOT_PATH = "../res/drawable/black_dot.png";
    public static final String WHITE_DOT_PATH = "../res/drawable/white_dot.png";

    public static final String FUNCTIONAL_TEMPLATE_PATH = "src/app/res/templates/functional_template.xml";
    public static final String CHRONOLOGICAL_TEMPLATE_PATH = "src/app/res/templates/chronological_template.xml";
    public static final String COMBINED_TEMPLATE_PATH = "src/app/res/templates/combined_template.xml";

    public static final String LATEX_FILE_EXTENSION = ".tex";
    public static final String TEXT_FILE_EXTENSION = ".txt";
    public static final String PDF_FILE_EXTENSION = ".pdf";

    public static final String APPLICATION_FILE_EXTENSION = ".cv";

    public static final int FUNCTIONAL_TEMPLATE_ID = 2;
    public static final int CHRONOLOGICAL_TEMPLATE_ID = 0;
    public static final int COMBINED_TEMPLATE_ID = 1;

    public static final int XML_TYPE_ID = 0;
    public static final int LATEX_TYPE_ID = 1;
    public static final int TEXT_TYPE_ID = 2;

    public static final String PERSONAL_INFO_NAME = "name";
    public static final String PERSONAL_INFO_ADDRESS = "address";
    public static final String PERSONAL_INFO_HOME = "home";
    public static final String PERSONAL_INFO_MOBILE = "mobile";
    public static final String PERSONAL_INFO_EMAIL = "email";
    public static final String PERSONAL_INFO_WEBSITE = "website";

    public static final String PROFESSIONAL_PROFILE_VALUE = "PROFESSIONAL PROFILE";
    public static final String SKILLS_AND_EXPERIENCE_VALUE = "SKILLS AND EXPERIENCE";
    public static final String CAREER_SUMMARY_VALUE = "CAREER SUMMARY";
    public static final String EDUCATION_AND_TRAINING_VALUE = "EDUCATION AND TRAINING";
    public static final String FURTHER_COURSES_VALUE = "FURTHER COURSES";
    public static final String ADDITIONAL_INFORMATION = "ADDITIONAL INFORMATION";
    public static final String INTERESTS_VALUE = "INTERESTS";
    public static final String CORE_STRENGTHS_VALUE = "CORE STRENGTHS";
    public static final String PROFESSIONAL_EXPERIENCE_VALUE = "PROFESSIONAL EXPERIENCE";

    public static final int TAB_SIZE = 20;

    public static final String TEX_TEMP_DIRECTORY = "src/app/res/temp/";

    public static String encodeImageToBase64(Image image) throws IOException {
        BufferedImage swingImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(swingImage, "png", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        Base64 base64 = new Base64();
        String encodedImage = base64.encodeToString(byteArrayOutputStream.toByteArray());
        byteArrayOutputStream.close();
        return encodedImage;
    }

    public static Image decodeImageFromBase64(String encodedImage) throws IOException {
        Base64 base64 = new Base64();
        byte[] bytes = base64.decode(encodedImage);
        return SwingFXUtils.toFXImage(ImageIO.read(new ByteArrayInputStream(bytes)), null);
    }

    public static int compareDates(String dateString1, String dateDate2) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy:HH:mm");
        Date date1 = sdf.parse(dateString1);
        Date date2 = sdf.parse(dateDate2);
        return date1.compareTo(date2);
    }

    public static String getStringFormattedCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy:HH:mm");
        return sdf.format(new Date());
    }

    public static String getStringFormattedDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy");
        LocalDateTime ldt;
        ldt = LocalDateTime.of(date, LocalTime.now());

        return formatter.format(ldt);
    }

    public static void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("About");
        alert.setHeaderText("CV Editor v.1.0");

        ImageView imageView = new ImageView(InitScene.class.getResource("../res/drawable/cv_logo.png").toString());
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        alert.setWidth(200);
        alert.setHeight(200);
        alert.setGraphic(imageView);

        alert.setContentText("Created By\nFotios Mitropoulos\nAntreas Athanasiadis");
        DialogPane pane = alert.getDialogPane();
        pane.getStylesheets().add(InitScene.class.getResource("../res/styles/dialog_style.css").toExternalForm());
        pane.getStyleClass().add("myDialog");
        alert.show();
    }

    public static Optional<String> showTemplateOptionDialog() {
        List<String> choices = new ArrayList<>();
        choices.add("Functional CV");
        choices.add("Chronological CV");
        choices.add("Combined CV");

        ImageView imageView = new ImageView(InitScene.class.getResource("../res/drawable/cv_logo.png").toString());
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Functional CV", choices);
        dialog.setTitle("Template Selection");
        dialog.setHeaderText("Select one of the following choices");
        dialog.setContentText("Template:");
        dialog.setGraphic(imageView);
        dialog.getDialogPane().getStylesheets().add(Main.class.getResource("../res/styles/dialog_style.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("myDialog");
        return dialog.showAndWait();
    }

    public static Callback<DatePicker, DateCell> getDateCellFactory(LocalDate lastDate) {
        return new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(lastDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
    }

    public static LocalDate formatDateFromString(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy");
        return LocalDate.parse(date.substring(0, 10), formatter);
    }
}
