package by.epam.computingPractice.xml.constants;

public enum DevicesXMLTag {
    DEVICES("devices"),
    DEVICE("device"),
    NAME("name"),
    ORIGIN("origin"),
    PRICE("price"),
    TYPE("type"),
    PERIPHERAL("peripheral"),
    POWER_USAGE("powerUsage"),
    HAS_COOLER("hasCooler"),
    PORTS("ports"),
    PORT("port"),
    COMPONENTS("components"),
    COMPONENT("component"),
    CRITICAL("critical");

    private String value;

    DevicesXMLTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
