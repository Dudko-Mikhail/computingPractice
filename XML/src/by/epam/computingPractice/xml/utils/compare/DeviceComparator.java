package by.epam.computingPractice.xml.utils.compare;

import by.epam.computingPractice.xml.entity.Device;

import java.util.Comparator;

public class DeviceComparator implements Comparator<Device> {

    @Override
    public int compare(Device first, Device second) {
        return first.getPrice().compareTo(second.getPrice());
    }
}
