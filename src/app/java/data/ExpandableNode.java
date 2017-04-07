package app.java.data;

import java.util.ArrayList;
public class ExpandableNode extends Node {

    private ArrayList<Node> childrenData;

    public ExpandableNode(String key, String value) {
        super(key, value);
        super.TAG = this.getClass().getName();
        childrenData = new ArrayList<>();
    }

    public ArrayList<Node> getChildren() {
        return childrenData;
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
            throw new IndexOutOfBoundsException("Index " + index + " not found.");
        else
            return childrenData.get(index);
    }

    public Node getChild(String key){
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
