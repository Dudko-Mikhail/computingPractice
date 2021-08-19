package by.epam.computingPractice.xml.entity;

import by.epam.computingPractice.xml.constants.PortType;

import java.util.Objects;

public class Port {
    private PortType type;
    private int count;

    public Port(PortType type, int count) {
        this.type = type;
        this.count = count;
    }

    public PortType getType() {
        return type;
    }

    public void setType(PortType type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Port port = (Port) o;
        return count == port.count && type == port.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, count);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Port{");
        sb.append("type=").append(type);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
