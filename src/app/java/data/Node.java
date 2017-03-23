package app.java.data;


import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static app.java.utils.ApplicationUtils.*;

public class Node {
    public static final int BLACK_DOT_DRAWABLE_ID = 0;
    public static final int WHITE_DOT_DRAWABLE_ID = 1;
    private static final int UNITIALIZED = -1;

    private String TAG = "node";

    private String key;
    private String value;
    private boolean isKeyVisible;
    private int drawableId;
    //private ArrayList<Node> children;
    private ListView<Node> listView;
    private ObservableList<Node> childrenData;

    public Node(String key, String value) {
        this.key = key;
        this.value = value;
        this.drawableId = UNITIALIZED;
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


    public javafx.scene.Node getKeyLabel() throws Exception {
        if (isKeyVisible)
            return getTextLabel();
        else
            return getDotLabel();
    }

    private Label getTextLabel() {
        return new Label(key);
    }

    private ImageView getDotLabel() throws Exception {
        if (drawableId == UNITIALIZED)
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




    public ObservableList<Node> getChildren() {
        if (childrenData.size() == 0) return null;
        else return childrenData;
    }

    public void addChild(Node item) {
        childrenData.add(item);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean deleteChild(String key) {
        int index = 0;
        for (Node n : childrenData) {
            if (n.getKey().equals(key)){
                childrenData.remove(index);
                return true;
            }
            index++;
        }
        return false;
    }

    public ListView<Node> toListView() {
        if (childrenData.size() <=  0) return null;
        //ObservableList<Node> data = FXCollections.observableArrayList();
        //data.addAll(children);

        listView = new ListView<>(childrenData);
        listView.setCellFactory(param -> new ListCell<Node>(){
            @Override
            protected void updateItem(Node item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    HBox hBox = new HBox(new Label(key), new Label(value));
                    VBox vBox = new VBox(hBox, new ListView<Node>());
                    hBox.setSpacing(10);

                }
            }
        });
        return listView;
    }

}
