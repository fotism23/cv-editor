package test;

import app.java.DocumentController;
import app.java.Merger;
import app.java.data.Node;
import app.java.utils.ApplicationUtils;
import javafx.embed.swing.JFXPanel;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.activation.UnsupportedDataTypeException;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

public class MergerTest {
    private static DocumentController firstDocumentController;
    private static DocumentController secondDocumentController;

    @BeforeClass
    public static void initData() throws InterruptedException, UnsupportedDataTypeException {
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            new JFXPanel(); // initializes JavaFX environment
            latch.countDown();
        });

        if (!latch.await(5L, TimeUnit.SECONDS))
            throw new ExceptionInInitializerError();
        firstDocumentController = new DocumentController();
        firstDocumentController.importData("src/app/res/templates/functional_template.xml", ApplicationUtils.XML_TYPE_ID);
        secondDocumentController = new DocumentController();
        secondDocumentController.importData("src/app/res/templates/functional_template.xml", ApplicationUtils.XML_TYPE_ID);
    }

    @Test
    public void testMergeAction(){
        ArrayList<Node> selectedNodes = pickSomeNodes();
        Merger.merge(new File("src/test/results/mergeTest.xml"), firstDocumentController, secondDocumentController, selectedNodes);
        assertNotNull(new File("src/test/results/mergeTest.xml"));
    }

    private ArrayList<Node> pickSomeNodes() {
        ArrayList<Node> selectedNodes = new ArrayList<>();
        selectedNodes.add(firstDocumentController.getDocument().queryNode("3"));
        selectedNodes.add(firstDocumentController.getDocument().queryNode("2"));
        selectedNodes.add(secondDocumentController.getDocument().queryNode("4"));
        selectedNodes.add(secondDocumentController.getDocument().queryNode("5"));
        return selectedNodes;
    }
}
