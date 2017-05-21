package app.java.data;

import app.java.utils.ApplicationUtils;

import java.time.LocalDate;

public abstract class NodeFactory {

    public static Node createNewNode(String key, String value, String date){
        return new Node(key, value, ApplicationUtils.formatDateFromString(date));
    }

    public static ListNode createNewListNode(String key, String value, String date) {

        return new ListNode(key, value, ApplicationUtils.formatDateFromString(date));
    }

    public static Node createNewNode(String key, String value, LocalDate date){
        return new Node(key, value, date);
    }

    public static ListNode createNewListNode(String key, String value, LocalDate date) {

        return new ListNode(key, value, date);
    }
}
