package app.java.data;

import java.time.LocalDate;
import java.util.ArrayList;

public class ListNode extends Node {

    private ArrayList<Node> childrenData;

    public ListNode(String key, String value, LocalDate date) {
        super(key, value, date);
        super.TAG = this.getClass().getName();
        childrenData = new ArrayList<>();
    }

    public ArrayList<Node> getChildren() {
        return childrenData;
    }

    void addChild(Node item) {
        childrenData.add(item);
    }

    public void addChildren(ArrayList<Node> items) {
        childrenData.addAll(items);
    }

    boolean removeChild(String key) {
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

    Node getChild(String key){
        int index = 0;
        for (Node n : childrenData) {
            if (n.getKey().equals(key)){
                return childrenData.get(index);
            }
            index++;
        }
        if (key.startsWith(this.getKey()) && !key.equals(this.getKey()))
            System.out.println("grandchild");
        return null;
    }
}
