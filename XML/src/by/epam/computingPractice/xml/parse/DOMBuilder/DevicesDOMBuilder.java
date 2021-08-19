package by.epam.computingPractice.xml.parse.DOMBuilder;

import by.epam.computingPractice.xml.constants.Component;
import by.epam.computingPractice.xml.constants.PortType;
import by.epam.computingPractice.xml.entity.Device;
import by.epam.computingPractice.xml.entity.Port;
import by.epam.computingPractice.xml.parse.AbstractDevicesBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

public class DevicesDOMBuilder extends AbstractDevicesBuilder {
    private DocumentBuilder docBuilder;

    public DevicesDOMBuilder() {
        initDOMBuilder();
    }

    public DevicesDOMBuilder(Set<Device> devices) {
        super(devices);
        initDOMBuilder();
    }

    @Override
    public void buildSetDevices(String fileName) {
        try {
            Document doc = docBuilder.parse(fileName);
            Element root = doc.getDocumentElement();
            NodeList devicesNodeList = root.getElementsByTagName("device");
            for (int i = 0; i < devicesNodeList.getLength(); i++) {
                var deviceElement = (Element) devicesNodeList.item(i);
                devices.add(buildDevice(deviceElement));
            }
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private Device buildDevice(Element deviceElement) {
        Device device = new Device();
        device.setName(getElementTextContent(deviceElement, "name"));
        device.setOrigin(getElementTextContent(deviceElement, "origin"));
        device.setPrice(new BigDecimal(getElementTextContent(deviceElement, "price")));
        device.setCritical(getElementBooleanContent(deviceElement, "critical"));

        Device.Type type = device.initType();
        Element typeElement = (Element) deviceElement.getElementsByTagName("type").item(0);
        type.setPeripheral(getElementBooleanContent(typeElement, "peripheral"));
        int powerUsage = Integer.parseInt(getElementTextContent(typeElement, "powerUsage"));
        type.setPowerUsage(powerUsage);
        type.setHasCooler(getElementBooleanContent(typeElement, "hasCooler"));

        NodeList allPorts = typeElement.getElementsByTagName("port");
        for (int i = 0; i < allPorts.getLength(); i++) {
            Element portElement = (Element) allPorts.item(i);
            PortType portType = PortType.valueOf(portElement.getAttribute("type"));
            int count = Integer.parseInt(portElement.getAttribute("count"));
            type.getPorts().add(new Port(portType, count));
        }

        NodeList allComponents = typeElement.getElementsByTagName("component");
        for (int i = 0; i < allComponents.getLength(); i++) {
            Element componentElement = (Element) allComponents.item(i);
            Component component = Component.valueOf(componentElement.getAttribute("name"));
            type.getComponentsGroup().add(component);
        }
        device.setType(type);
        return device;
    }

    private static String getElementTextContent(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }

    private static boolean getElementBooleanContent(Element element, String tagName) {
        return Boolean.parseBoolean(getElementTextContent(element, tagName));
    }

    private void initDOMBuilder() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
