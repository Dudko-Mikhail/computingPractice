package by.epam.computingPractice.xml.parse.StAXBuilder;

import by.epam.computingPractice.xml.constants.Component;
import by.epam.computingPractice.xml.constants.DevicesXMLTag;
import by.epam.computingPractice.xml.constants.PortType;
import by.epam.computingPractice.xml.entity.Device;
import by.epam.computingPractice.xml.entity.Port;
import by.epam.computingPractice.xml.parse.AbstractDevicesBuilder;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

public class DevicesStAXBuilder extends AbstractDevicesBuilder {
    private XMLInputFactory inputFactory;

    public DevicesStAXBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    public DevicesStAXBuilder(Set<Device> devices) {
        super(devices);
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildSetDevices(String fileName) {
        XMLStreamReader reader;
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            reader = inputFactory.createXMLStreamReader(fileInputStream);
            String name;
            while (reader.hasNext()) {
               int type = reader.next();
               if (type == XMLStreamConstants.START_ELEMENT) {
                   name = reader.getLocalName();
                   if (name.equals(DevicesXMLTag.DEVICE.getValue())) {
                       Device device = buildDevice(reader);
                       devices.add(device);
                   }
               }
            }
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
    }

    private Device buildDevice(XMLStreamReader reader) throws XMLStreamException {
        Device device = new Device();
        Device.Type deviceType = device.initType();
        DevicesXMLTag currentXMLTag;
        while (reader.hasNext()) {
            int type = reader.next();
            if (type == XMLStreamConstants.START_ELEMENT) {
                currentXMLTag = getDeviceXMLTagFromString(reader.getLocalName());
                switch (currentXMLTag) {
                    case NAME -> device.setName(getXMLTagTextContent(reader));
                    case ORIGIN -> device.setOrigin(getXMLTagTextContent(reader));
                    case PRICE -> device.setPrice(new BigDecimal(getXMLTagTextContent(reader)));
                    case PERIPHERAL -> deviceType.setPeripheral(Boolean.parseBoolean(getXMLTagTextContent(reader)));
                    case POWER_USAGE -> deviceType.setPowerUsage(Integer.parseInt(getXMLTagTextContent(reader)));
                    case HAS_COOLER -> deviceType.setHasCooler(Boolean.parseBoolean(getXMLTagTextContent(reader)));
                    case PORT -> {
                        int count;
                        PortType portType;
                        if (reader.getAttributeName(0).toString().equals("type")) {
                            portType = PortType.valueOf(reader.getAttributeValue(0));
                            count = Integer.parseInt(reader.getAttributeValue(1));
                        } else {
                            portType = PortType.valueOf(reader.getAttributeValue(1));
                            count = Integer.parseInt(reader.getAttributeValue(0));
                        }
                        Port port = new Port(portType, count);
                        deviceType.getPorts().add(port);
                    }
                    case COMPONENT -> {
                        Component component = Component.valueOf(reader.getAttributeValue(0));
                        deviceType.getComponentsGroup().add(component);
                    }
                    case CRITICAL -> device.setCritical(Boolean.parseBoolean(getXMLTagTextContent(reader)));
                }
            } else if (type == XMLStreamConstants.END_ELEMENT) {
                DevicesXMLTag devicesXMLTag = getDeviceXMLTagFromString(reader.getLocalName());
                if (devicesXMLTag == DevicesXMLTag.TYPE) {
                    device.setType(deviceType);
                }
                if (devicesXMLTag == DevicesXMLTag.DEVICE) {
                    break;
                }
            }
        }
        return device;
    }

    private DevicesXMLTag getDeviceXMLTagFromString(String tag) {
        String value = tag.strip();
        return switch (value) {
            case "powerUsage" -> DevicesXMLTag.POWER_USAGE;
            case "hasCooler" -> DevicesXMLTag.HAS_COOLER;
            default -> DevicesXMLTag.valueOf(value.toUpperCase());
        };
    }

    private String getXMLTagTextContent(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        while (reader.hasNext()) {
            int type = reader.next();
            if (type == XMLStreamConstants.CHARACTERS) {
                text = reader.getText();
                break;
            }
        }
        return text;
    }
}



