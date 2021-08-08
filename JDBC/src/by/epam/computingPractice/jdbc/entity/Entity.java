package by.epam.computingPractice.jdbc.entity;

import java.io.Serializable;
import java.util.Objects;

public abstract class Entity implements Serializable {
    private long id;

    public Entity(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id < 0");
        }
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;
        Entity entity = (Entity) o;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
