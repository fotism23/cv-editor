package app.java.data;

public abstract class NodeFactory {
    public static Node createNewNode(String key, String value){
        return new Node(key, value);
    }

    public static ExpandableNode createNewExpandableNode(String key, String value) {
        return new ExpandableNode(key, value);
    }
}
