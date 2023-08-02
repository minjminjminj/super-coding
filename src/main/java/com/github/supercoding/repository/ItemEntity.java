package com.github.supercoding.repository;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ItemEntity {
    private Integer id;
    private String name;
    private String type;
    private Integer price;
    private String cpu;
    private String capacity;

    public ItemEntity(Integer id, String name, String type, Integer price, String cpu, String capacity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.cpu = cpu;
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemEntity)) return false;
        ItemEntity that = (ItemEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
