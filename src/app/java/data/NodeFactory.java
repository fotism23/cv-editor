package app.java.data;

public abstract class NodeFactory {
    public Node createNewNode(String key, String value){
        return new Node(key, value);
    }

    public Node createNewExpandableNode(String key, String value) {
        return new ExpandableNode(key, value);
    }
}
