package by.epam.computingPractice.jdbc.entity;

import java.util.Objects;

public class Maker extends Entity {
    private String name;
    private String country;

    public Maker(long id, String name, String country) {
        super(id);
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        if (!super.equals(o)) return false;
        Maker maker = (Maker) o;
        return Objects.equals(name, maker.name) && Objects.equals(country, maker.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Maker{");
        sb.append("id =").append(this.getId());
        sb.append(", name='").append(name).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
