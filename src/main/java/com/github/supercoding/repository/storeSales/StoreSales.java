package com.github.supercoding.repository.storeSales;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class StoreSales {
    private Integer id;
    private String storeName;
    private Integer amout;

    public StoreSales(Integer id, String storeName, Integer amout) {
        this.id = id;
        this.storeName = storeName;
        this.amout = amout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoreSales)) return false;
        StoreSales that = (StoreSales) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
