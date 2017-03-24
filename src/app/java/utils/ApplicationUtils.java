package app.java.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ApplicationUtils {
    public static final String APPLICATION_TITLE = "CV Editor";
    public static final String APPLICATION_ICON_PATH = "../res/drawable/cv_icon.png";

    public static final int INIT_WINDOW_HEIGHT = 400;
    public static final int INIT_WINDOW_WIDTH = 600;
    public static final String INIT_WINDOW_LAYOUT_FXML = "../res/layout/init_window.fxml";

    public static final int EDITOR_WINDOW_WIDTH = 900;
    public static final int EDITOR_WINDOW_HEIGHT = 900;
    public static final String EDITOR_WINDOW_LAYOUT_FXML = "../res/layout/cveditor_window.fxml";

    public static final String BLACK_DOT_PATH = "../res/drawable/black_dot.png";
    public static final String WHITE_DOT_PATH = "../res/drawable/white_dot.png";

    public static final String FUNCTIONAL_TEMPLATE_PATH = "../res/templates/functional_template.xml";
    public static final String CHRONOLOGICAL_TEMPLATE_PATH = "../res/templates/chronological_template.xml";
    public static final String COMBINED_TEMPLATE_PATH = "../res/templates/combined_template.xml";

    public static final String APPLICATION_FILE_EXTENSION = ".cv";

    public static final int BLACK_DOT_DRAWABLE = 0;
    public static final int WHITE_DOT_DRAWABLE = 1;
    public static final int STRING_KEY = -1;

    public String encodeImageToBase64(Image image) throws IOException {
        BufferedImage swingImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(swingImage, "png", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        Base64 base64 = new Base64();
        String encodedImage = base64.encodeToString(byteArrayOutputStream.toByteArray());
        byteArrayOutputStream.close();
        return encodedImage;
    }

    public Image decodeImageFromBase64(String encodedImage) throws IOException {
        Base64 base64 = new Base64();
        byte[] bytes = base64.decode(encodedImage);
        return SwingFXUtils.toFXImage(ImageIO.read(new ByteArrayInputStream(bytes)), null);
    }

    public int compareDates(String dateString1, String dateDate2) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy:HH:mm");
        Date date1 = sdf.parse(dateString1);
        Date date2 = sdf.parse(dateDate2);
        return date1.compareTo(date2);
    }

    public String getStringFormattedCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy:HH:mm");
        return sdf.format(new Date());
    }
}
