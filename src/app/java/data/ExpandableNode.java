package app.java.data;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ExpandableNode extends Node {

    //private ArrayList<Node> children;
    private ListView<Node> listView;
    private ObservableList<Node> childrenData;

    public ExpandableNode(String key, String value) {
        super(key, value);
        super.TAG = this.getClass().getName();
        childrenData = FXCollections.observableArrayList();
    }

    public ObservableList<Node> getChildren() {
        return childrenData;
    }

    public ArrayList getChildrenArrayList() {
        return new ArrayList<>(Arrays.asList(childrenData.toArray()));
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
}
