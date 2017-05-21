package test;

import app.java.DocumentController;
import app.java.data.ListNode;
import app.java.data.NodeFactory;
import app.java.utils.ApplicationUtils;
import javafx.embed.swing.JFXPanel;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.activation.UnsupportedDataTypeException;
import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EditorTest {
    private static DocumentController documentController;

    @BeforeClass
    public static void initData() throws InterruptedException, UnsupportedDataTypeException {
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            new JFXPanel(); // initializes JavaFX environment
            latch.countDown();
        });

        if (!latch.await(5L, TimeUnit.SECONDS))
            throw new ExceptionInInitializerError();
        documentController = new DocumentController();
        documentController.importData("src/app/res/templates/functional_template.xml", ApplicationUtils.XML_TYPE_ID);
    }

    @Test
    public void testAddAction(){
        ListNode nodeToAdd = NodeFactory.createNewListNode("3.3", "test value", ApplicationUtils.getStringFormattedCurrentDate());
        nodeToAdd.setKeyVisibility(true);
        nodeToAdd.setLabelDrawableId(-1);
        documentController.getDocument().addElement("3", nodeToAdd);
        assertNotNull(documentController.getDocument().queryNode("3.3"));
    }

    @Test
    public void testRemoveAction() {
        assertEquals("Result", true, documentController.getDocument().removeElement("3.1"));
    }

    @Test
    public void testEditAction() {
        documentController.getDocument().removeElement("3.1");
        ListNode nodeToAdd = NodeFactory.createNewListNode("3.1", "test value", ApplicationUtils.getStringFormattedCurrentDate());
        nodeToAdd.setKeyVisibility(true);
        nodeToAdd.setLabelDrawableId(-1);
        documentController.getDocument().addElement("3", nodeToAdd);
        assertNotNull("Result", documentController.getDocument().removeElement("3.1") );
    }
}
