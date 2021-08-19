package by.epam.computingPractice.xml.utils.validator;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class XSDValidatorXML {
    private Validator validator;

    public XSDValidatorXML(String XSDFilePath) throws SAXException {
        if (!Files.exists(Path.of(XSDFilePath))) {
            throw new SAXException(new FileNotFoundException("(Не удаётся найти указанный файл)"));
        }
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new StreamSource(XSDFilePath));
        validator = schema.newValidator();
    }

    public void validate(String XMLFilePath) throws IOException, SAXException {
        if (!Files.exists(Path.of(XMLFilePath))) {
            throw new SAXException(new FileNotFoundException("(Не удаётся найти указанный файл)"));
        }
        validator.validate(new StreamSource(XMLFilePath));
        System.out.println("XML file is valid");
    }
}
