package by.epam.computingPractice.xml.parse;

import by.epam.computingPractice.xml.constants.DevicesBuilderType;
import by.epam.computingPractice.xml.entity.Device;
import by.epam.computingPractice.xml.parse.AbstractDevicesBuilder;
import by.epam.computingPractice.xml.parse.DOMBuilder.DevicesDOMBuilder;
import by.epam.computingPractice.xml.parse.SAXBuilder.DevicesSAXBuilder;
import by.epam.computingPractice.xml.parse.StAXBuilder.DevicesStAXBuilder;

import java.util.Set;

public class DevicesBuilderFactory {

    public static AbstractDevicesBuilder getInstance(DevicesBuilderType type) {
        if (type != null) {
            return switch (type) {
                case DOM -> new DevicesDOMBuilder();
                case SAX -> new DevicesSAXBuilder();
                case STAX -> new DevicesStAXBuilder();
            };
        }
        else {
            throw new IllegalArgumentException("BuilderType - null");
        }
    }

    public static AbstractDevicesBuilder getInstance(DevicesBuilderType type, Set<Device> devices) {
        if (type != null) {
            return switch (type) {
                case DOM -> new DevicesDOMBuilder(devices);
                case SAX -> new DevicesSAXBuilder(devices);
                case STAX -> new DevicesStAXBuilder(devices);
            };
        }
        else {
            throw new IllegalArgumentException("BuilderType - null");
        }
    }

    private DevicesBuilderFactory() {}
}

