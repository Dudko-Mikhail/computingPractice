package by.epam.computingPractice.xml.parse.SAXBuilder;

import by.epam.computingPractice.xml.entity.Device;
import by.epam.computingPractice.xml.parse.AbstractDevicesBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Set;

public class DevicesSAXBuilder extends AbstractDevicesBuilder {
    private XMLReader xmlReader;
    private DevicesHandler handler = new DevicesHandler();

    public DevicesSAXBuilder() {
        initSAXBuilder();
    }

    public DevicesSAXBuilder(Set<Device> devices) {
        super(devices);
        initSAXBuilder();
    }
    @Override
    public void buildSetDevices(String fileName) {
        try {
            xmlReader.parse(fileName);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
        devices.addAll(handler.getDevices());
    }

    private void initSAXBuilder() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            xmlReader = parser.getXMLReader();
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        xmlReader.setContentHandler(handler);
    }
}
