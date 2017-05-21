package test;

import app.java.parsers.XmlParser;
import javafx.embed.swing.JFXPanel;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class XmlParserTest {
    @Test
    public void testPersonalInfoParse() {
        XmlParser parser = new XmlParser("src/app/res/templates/functional_template.xml");
        assertEquals("Result", "Fotis Mitropoulos" ,parser.parseName());
        assertEquals("Result", "Kapou" ,parser.parseAddress());
        assertEquals("Result", "fotismitropoulos@gmail.com" ,parser.parseEmail());
    }

    @Test
    public void testTemplateParse() {
        XmlParser parser = new XmlParser("src/app/res/templates/functional_template.xml");
        assertEquals("Result", 2 ,parser.parseTemplate());
    }

    @BeforeClass
    public static void initToolkit() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            new JFXPanel(); // initializes JavaFX environment
            latch.countDown();
        });

        if (!latch.await(5L, TimeUnit.SECONDS))
            throw new ExceptionInInitializerError();
    }

    @Test
    public void testCommonNodeParse() {
        XmlParser parser = new XmlParser("src/app/res/templates/functional_template.xml");
        assertNotNull("Result", parser.parseProfessionalProfile());
        assertEquals("Result", "PROFESSIONAL PROFILE" ,parser.parseProfessionalProfile().getValue());
        assertNotNull("Result", parser.parseAdditionalInfo());
        assertEquals("Result", "ADDITIONAL INFORMATION" ,parser.parseAdditionalInfo().getValue());
        assertNotNull("Result", parser.parseInterests());
        assertEquals("Result", "INTERESTS" ,parser.parseInterests().getValue());
    }

}