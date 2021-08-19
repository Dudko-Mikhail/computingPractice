package by.epam.computingPractice.xml.entity;

import by.epam.computingPractice.xml.constants.Component;
import by.epam.computingPractice.xml.constants.PortType;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Device {
    private String name;
    private String origin;
    private BigDecimal price;
    private Type type;
    private boolean critical;

    public Device() {
        type = new Type();
    }

    public Device(String name, String origin, BigDecimal price, boolean peripheral, int powerUsage,
                  boolean hasCooler, EnumSet<Component> componentsGroup, Set<Port> ports, boolean critical) {
        this.name = name;
        this.origin = origin;
        this.price = price;
        this.type = new Type(peripheral, powerUsage, hasCooler, componentsGroup, ports);
        this.critical = critical;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isCritical() {
        return critical;
    }

    public void setCritical(boolean critical) {
        this.critical = critical;
    }

    public Type initType() {
        return this.new Type();
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Device device = (Device) o;
        return critical == device.critical
                && Objects.equals(name, device.name)
                && Objects.equals(origin, device.origin)
                && Objects.equals(price, device.price)
                && Objects.equals(type, device.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, origin, price, type, critical);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Device{");
        sb.append("name='").append(name).append('\'');
        sb.append(", origin='").append(origin).append('\'');
        sb.append(", price=").append(price).append("rub");
        sb.append(", type=").append(type);
        sb.append(", critical=").append(critical);
        sb.append('}');
        return sb.toString();
    }

    public class Type {
        private boolean peripheral;
        private int powerUsage;
        private boolean hasCooler;
        private EnumSet<Component> componentsGroup;
        private Set<Port> ports;

        private Type() {
            componentsGroup = EnumSet.noneOf(Component.class);
            ports = new HashSet<>();
        }

        private Type(boolean peripheral, int powerUsage, boolean hasCooler,
                    EnumSet<Component> componentsGroup, Set<Port> ports) {
            this.peripheral = peripheral;
            this.powerUsage = powerUsage;
            this.hasCooler = hasCooler;
            this.componentsGroup = componentsGroup;
            this.ports = ports;
        }

        public boolean isPeripheral() {
            return peripheral;
        }

        public void setPeripheral(boolean peripheral) {
            this.peripheral = peripheral;
        }

        public int getPowerUsage() {
            return powerUsage;
        }

        public void setPowerUsage(int powerUsage) {
            this.powerUsage = powerUsage;
        }

        public boolean isHasCooler() {
            return hasCooler;
        }

        public void setHasCooler(boolean hasCooler) {
            this.hasCooler = hasCooler;
        }

        public EnumSet<Component> getComponentsGroup() {
            return componentsGroup;
        }

        public void setComponentsGroup(EnumSet<Component> componentsGroup) {
            this.componentsGroup = componentsGroup;
        }

        public Set<Port> getPorts() {
            return ports;
        }

        public void setPorts(Set<Port> ports) {
            this.ports = ports;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || o.getClass() != this.getClass()) return false;
            Type type = (Type) o;
            return peripheral == type.peripheral
                    && hasCooler == type.hasCooler
                    && powerUsage == type.powerUsage
                    && Objects.equals(componentsGroup, type.componentsGroup)
                    && Objects.equals(ports, type.ports);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Type{");
            sb.append("peripheral=").append(peripheral);
            sb.append(", powerUsage=").append(powerUsage).append("watt");
            sb.append(", hasCooler=").append(hasCooler);
            sb.append(", componentsGroup=").append(componentsGroup);
            sb.append(", ports=").append(ports);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public int hashCode() {
            return Objects.hash(peripheral, hasCooler, powerUsage, componentsGroup, ports);
        }
    }
}


