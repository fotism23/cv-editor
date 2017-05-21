package app.java.data;

import app.java.utils.ApplicationUtils;
import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Document {
    private HashMap<String, String> personalInfo;
    private int template;
    private Image profImage;
    private ArrayList<Node> data;

    public Document() {
        personalInfo = new HashMap<>();
        data = new ArrayList<>();
    }

    public void addElement(String itemSelected, Node nodeToAdd) {
        Node parentNode = queryNode(itemSelected);
        parentNode.setDate(nodeToAdd.getDate());
        ((ListNode) parentNode).addChild(nodeToAdd);
    }

    public Node queryNode(String itemSelected) {
        String[] parts = itemSelected.split("\\.");
        Node node = data.get(Integer.parseInt(parts[0]) - 2);
        if (node.getKey().equals(itemSelected))
            return node;
        int keyIndex = 0;
        String tempKey = parts[keyIndex];
        while (true) {
            if (((ListNode) node).getChild(itemSelected) == null) {
                keyIndex++;
                tempKey += "." + parts[keyIndex];
                node = ((ListNode) node).getChild(tempKey);
            } else {
                return ((ListNode) node).getChild(itemSelected);
            }
        }
    }

    public boolean removeElement(String itemSelected) {
        if (itemSelected.length() <= 2) return false;
        String parentKey = itemSelected.substring(0, itemSelected.length() - 2);
        Node parentNode = queryNode(parentKey);
        return ((ListNode) parentNode).removeChild(itemSelected);
    }

    public Node getSectionWithValue(String value) {
        for (Node node : data) {
            if (node.getValue().equals(value)) return node;
        }
        return null;
    }

    public LocalDate getRangeOfNotAvailableDates(String itemSelected) {
        Node node = queryNode(itemSelected);
        return node.getDate();
    }

    public String getNextAvailableKey(String itemSelected) {
        String generatedKey = "";
        ListNode node = (ListNode) queryNode(itemSelected);
        if (node.getChildren().isEmpty())
            return itemSelected + ".1";
        else {
            for (Node n : node.getChildren())
                generatedKey = n.getKey();
            String lastCharacter = generatedKey.substring(generatedKey.length() - 1);
            int nextSegment = Integer.parseInt(lastCharacter);
            nextSegment++;
            generatedKey = generatedKey.substring(0, generatedKey.length()-1);
            generatedKey += nextSegment;
            return generatedKey;
        }
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public void setName(String value) {
        setPersonalInfoValue(ApplicationUtils.PERSONAL_INFO_NAME, value);
    }

    public void setAddress(String value) {
        setPersonalInfoValue(ApplicationUtils.PERSONAL_INFO_ADDRESS, value);
    }

    public void setHomePhone(String value) {
        setPersonalInfoValue(ApplicationUtils.PERSONAL_INFO_HOME, value);
    }

    public void setMobilePhone(String value) {
        setPersonalInfoValue(ApplicationUtils.PERSONAL_INFO_MOBILE, value);
    }

    public void setEmail(String value) {
        setPersonalInfoValue(ApplicationUtils.PERSONAL_INFO_EMAIL, value);
    }

    public void setWebsite(String value) {
        setPersonalInfoValue(ApplicationUtils.PERSONAL_INFO_WEBSITE, value);
    }

    public void setProfImage(Image profImage) {
        this.profImage = profImage;
    }

    private void setPersonalInfoValue(String key, String value) {
        if (personalInfo.containsKey(key))
            personalInfo.replace(key, value);
        else
            personalInfo.put(key, value);
    }

    public Image getProfImage() {
        return profImage;
    }

    public void setPersonalInfo(HashMap<String, String> personalInfo) {
        this.personalInfo = personalInfo;
    }

    public HashMap<String, String> getPersonalInfo() {
        return personalInfo;
    }

    public ArrayList<Node> getData() {
        return data;
    }

    public String queryPersonalInfo(String key) {
        return personalInfo.get(key);
    }

    public void addData(Node node) {
        data.add(node);
    }

    public int getTemplate() {
        return template;
    }
}
