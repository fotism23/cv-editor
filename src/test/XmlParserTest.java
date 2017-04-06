package test;

import app.java.utils.parsers.XmlParser;
import org.junit.Test;

import static org.junit.Assert.*;


public class XmlParserTest {
    @Test
    public void testPersonalInfoParse() {
        XmlParser parser = new XmlParser("app/src/res/templates/functional_template.xml");
        assertEquals("Result", "Fotis Mitropoulos" ,parser.parseName());
        assertEquals("Result", "Christovasili 34" ,parser.parseAddress());
        assertEquals("Result", "fotismitropoulos@gmail.com" ,parser.parseEmail());
    }

    @Test
    public void testTemplateParse() {
        XmlParser parser = new XmlParser("app/src/res/templates/functional_template.xml");
        assertEquals("Result", 0 ,parser.parseTemplate());
    }

    @Test
    public void testCommonNodeParse() {
        XmlParser parser = new XmlParser("app/src/res/templates/functional_template.xml");
        assertNotNull("Result", parser.parseProfessionalProfile());
        assertNotNull("Result", parser.parseAdditionalInfo());
        assertNotNull("Result", parser.parseInterests());
    }

}