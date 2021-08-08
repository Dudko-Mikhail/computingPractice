package by.epam.computingPractice.jdbc.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class Souvenir extends Entity {
    private long makerId;
    private String name;
    private Date productionDate;
    private BigDecimal price;


    public Souvenir(long id, long makerId, String name, Date productionDate, BigDecimal price) {
        super(id);
        this.makerId = makerId;
        this.name = name;
        this.productionDate = productionDate;
        this.price = price;
    }

    public long getMakerId() {
        return makerId;
    }

    public void setMakerId(long makerId) {
        this.makerId = makerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        if (!super.equals(o)) return false;
        Souvenir souvenir = (Souvenir) o;
        return makerId == souvenir.makerId && Objects.equals(name, souvenir.name)
                && Objects.equals(productionDate, souvenir.productionDate) && Objects.equals(price, souvenir.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), makerId, name, productionDate, price);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Souvenir{");
        sb.append("id =").append(this.getId());
        sb.append(", makerId=").append(makerId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", productionDate=").append(productionDate);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
