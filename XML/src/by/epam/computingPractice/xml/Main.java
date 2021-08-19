package by.epam.computingPractice.xml;

import by.epam.computingPractice.xml.XSLTransformer.XMLTransformer;
import by.epam.computingPractice.xml.constants.DevicesBuilderType;
import by.epam.computingPractice.xml.utils.compare.DeviceComparator;
import by.epam.computingPractice.xml.parse.AbstractDevicesBuilder;
import by.epam.computingPractice.xml.parse.DevicesBuilderFactory;
import by.epam.computingPractice.xml.utils.validator.XSDValidatorXML;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;


public class Main {

    public static void main(String[] args) {
        try {
            XSDValidatorXML validatorXML = new XSDValidatorXML("devices.xsd");
            validatorXML.validate("devices.xml");
            System.out.println("================================================");

            AbstractDevicesBuilder DOMBuilder = DevicesBuilderFactory.getInstance(DevicesBuilderType.DOM);
            DOMBuilder.buildSetDevices("devices.xml");
            DOMBuilder.getDevices().forEach(System.out::println);
            System.out.println("================================================");

            AbstractDevicesBuilder SAXBuilder = DevicesBuilderFactory.getInstance(DevicesBuilderType.SAX);
            SAXBuilder.buildSetDevices("devices.xml");
            SAXBuilder.getDevices().forEach(System.out::println);
            System.out.println("================================================");

            AbstractDevicesBuilder StAXBuilder = DevicesBuilderFactory.getInstance(DevicesBuilderType.STAX);
            StAXBuilder.buildSetDevices("devices.xml");
            StAXBuilder.getDevices().stream().sorted(new DeviceComparator()).forEach(System.out::println);
            System.out.println("================================================");

        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        try {
            XMLTransformer transformer = new XMLTransformer("devices.xsl");
            transformer.transform("devices.xml", "result.xml");
        } catch (FileNotFoundException | TransformerException e) {
            e.printStackTrace();
        }
    }
}


