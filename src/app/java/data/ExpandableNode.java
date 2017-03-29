package app.java.data;


import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ExpandableNode extends Node {

    //private ArrayList<Node> children;
    private ListView<Node> listView;
    private ObservableList<Node> childrenData;

    public ExpandableNode(String key, String value) {
        super(key, value);
        super.TAG = this.getClass().getName();
    }

    public ObservableList<Node> getChildren() {
        if (childrenData.size() == 0) return null;
        else return childrenData;
    }

    public void addChild(Node item) {
        childrenData.add(item);
    }

    public void addChildren(ArrayList<Node> items) {
        childrenData.addAll(items);
    }

    public boolean removeChild(String key) {
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

    public Node getChild(int index) throws Exception {
        if (index > childrenData.size() - 1)
            throw new IndexOutOfBoundsException("Index not found.");
        else
            return childrenData.get(index);
    }

    public Node getChild(String key) throws Exception {
        int index = 0;
        for (Node n : childrenData) {
            if (n.getKey().equals(key)){
                return childrenData.get(index);
            }
            index++;
        }
        throw new NoSuchElementException("Child Node with key : '" + key + "' not found.");
    }

    @Override
    public ListCell<Node> getListCell() {
        return super.getListCell();
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
                    HBox hBox;
                    try {
                        hBox = new HBox(getKeyLabel(), getValueTextField());
                        setGraphic(hBox);
                        VBox vBox = new VBox(hBox, new ListView<Node>());
                        hBox.setSpacing(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return listView;
    }
}
