package app.java.data;


import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static app.java.utils.ApplicationUtils.*;

public class Node {
    public String TAG = this.getClass().getName();


    public static final int BLACK_DOT_DRAWABLE_ID = 0;
    public static final int WHITE_DOT_DRAWABLE_ID = 1;
    private static final int UNINITIALIZED = -1;

    private String key;
    private TextField valueTextField;
    private boolean isKeyVisible;
    private int drawableId;
    private String content;

    public Node(String key, String value) {
        this.key = key;
        this.valueTextField = new TextField();
        this.valueTextField.setText(value);
        this.drawableId = UNINITIALIZED;
    }

    public javafx.scene.Node getKeyLabel() {
        if (isKeyVisible)
            return getTextLabel();
        else
            try {
                return getDotLabel();
            } catch (Exception e) {
                e.printStackTrace();
                return getTextLabel();
            }
    }

    private Label getTextLabel() {
        return new Label(key);
    }

    private ImageView getDotLabel() throws Exception {
        if (drawableId == UNINITIALIZED)
            throw new Exception("Drawable not initialized.");
        else
            return generateDot();
    }

    private ImageView generateDot() {
        if (drawableId == BLACK_DOT_DRAWABLE_ID)
            return blackDotImageView();
        else
            return whiteDotImageView();
    }

    private ImageView blackDotImageView() {
        Image image = new Image(BLACK_DOT_PATH);
        return new ImageView(image);
    }

    private ImageView whiteDotImageView() {
        Image image = new Image(WHITE_DOT_PATH);
        return new ImageView(image);
    }

    public String getKey() {
        return key;
    }

    public void setKeyVisibility(boolean visibility){
        isKeyVisible = isKeyVisible;
    }

    public void setLabelText() {
        this.key = key;
    }

    public void setLabelDrawableId(int id) {
        drawableId = id;
    }

    public int getLabelDrawableId() {
        return drawableId;
    }

    public TextField getValueTextField() {
        return valueTextField;
    }

    public String getValue () { return valueTextField.getText(); }

    public void setValue(String value) {
        valueTextField.setText(value);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Boolean getKeyVisibility() {
        return isKeyVisible;
    }
}
