package by.epam.computingPractice.xml.parse.SAXBuilder;

import by.epam.computingPractice.xml.constants.Component;
import by.epam.computingPractice.xml.constants.DevicesXMLTag;
import by.epam.computingPractice.xml.constants.PortType;
import by.epam.computingPractice.xml.entity.Device;
import by.epam.computingPractice.xml.entity.Port;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class DevicesHandler extends DefaultHandler {
    private Set<Device> devices;
    private DevicesXMLTag currentXMLTag;
    private Device currentDevice;
    private Device.Type currentType;

    public DevicesHandler() {
        devices = new HashSet<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        currentXMLTag = getDeviceXMLTagFromString(qName);
        switch (currentXMLTag) {
            case DEVICE -> currentDevice = new Device();
            case TYPE -> currentType = currentDevice.initType();
            case PORT -> {
                int portTypeIndex = attributes.getIndex("type");
                int countIndex = attributes.getIndex("count");
                PortType portType = PortType.valueOf(attributes.getValue(portTypeIndex));
                int count = Integer.parseInt(attributes.getValue(countIndex));
                currentType.getPorts().add(new Port(portType, count));
            }
            case COMPONENT -> {
                Component component = Component.valueOf(attributes.getValue(0));
                currentType.getComponentsGroup().add(component);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        DevicesXMLTag devicesXMLTag = getDeviceXMLTagFromString(qName);
        switch (devicesXMLTag) {
            case TYPE -> currentDevice.setType(currentType);
            case DEVICE -> devices.add(currentDevice);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String value = new String(ch, start, length).strip();
        if (currentXMLTag != null) {
            switch (currentXMLTag) {
                case NAME -> currentDevice.setName(value);
                case ORIGIN -> currentDevice.setOrigin(value);
                case PRICE -> currentDevice.setPrice(new BigDecimal(value));
                case PERIPHERAL -> currentType.setPeripheral(Boolean.parseBoolean(value));
                case CRITICAL -> currentDevice.setCritical(Boolean.parseBoolean(value));
                case POWER_USAGE -> currentType.setPowerUsage(Integer.parseInt(value));
                case HAS_COOLER -> currentType.setHasCooler(Boolean.parseBoolean(value));
            }
            currentXMLTag = null;
        }
    }

    private DevicesXMLTag getDeviceXMLTagFromString(String tag) {
        String value = tag.strip();
        return switch (value) {
            case "powerUsage" -> DevicesXMLTag.POWER_USAGE;
            case "hasCooler" -> DevicesXMLTag.HAS_COOLER;
            default -> DevicesXMLTag.valueOf(value.toUpperCase());
        };
    }

    public Set<Device> getDevices() {
        return devices;
    }
}
