package test;

import app.java.DocumentController;
import app.java.utils.ApplicationUtils;
import javafx.embed.swing.JFXPanel;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.activation.UnsupportedDataTypeException;
import javax.swing.*;
import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

public class ExporterTest {
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
        documentController.importData("src/app/res/templates/chronological_template.xml", ApplicationUtils.XML_TYPE_ID);
    }

    @Test
    public void testXmlExportAction() throws UnsupportedDataTypeException {
        documentController.exportData("src/test/results/exportXmlTest.xml", ApplicationUtils.XML_TYPE_ID);
        assertNotNull("Xml Exporter", new File("src/test/results/exportXmlTest.xml"));
    }

    @Test
    public void testTextExportAction() throws UnsupportedDataTypeException {
        documentController.exportData("src/test/results/exportTextTest.xml", ApplicationUtils.TEXT_TYPE_ID);
        assertNotNull("Xml Exporter", new File("src/test/results/exportTextTest.xml"));
    }

    @Test
    public void testLatexExportAction() throws UnsupportedDataTypeException {
        documentController.exportData("src/test/results/exportLatexTest.tex", ApplicationUtils.LATEX_TYPE_ID);
        assertNotNull("Xml Exporter", new File("src/test/results/exportLatexTest.tex"));
    }
}
