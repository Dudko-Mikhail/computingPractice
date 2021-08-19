package by.epam.computingPractice.xml.parse;

import by.epam.computingPractice.xml.entity.Device;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractDevicesBuilder {
    protected Set<Device> devices;

    protected AbstractDevicesBuilder() {
        devices = new HashSet<>();
    }

    protected AbstractDevicesBuilder(Set<Device> devices) {
        if (devices != null) {
            this.devices = devices;
        }
        else {
            devices = new HashSet<>();
        }
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public abstract void buildSetDevices(String fileName);

}
