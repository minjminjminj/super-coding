package com.github.supercoding.web.dto;

import com.github.supercoding.repository.ItemEntity;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Item {
    private String id;
    private String name;
    private String type;
    private int price;
    private Spec spec;

    public Item() { }

    public Item(Integer id, ItemBody itemBody) {
        this.id = String.valueOf(id);
        this.name = itemBody.getName();
        this.type = itemBody.getType();
        this.price = itemBody.getPrice();
        this.spec = itemBody.getSpec();
    }

    public Item(String id, String name, String type, int price, String cpu, String capacity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.spec = new Spec(cpu, capacity);
    }

    public Item(ItemEntity itemEntity) {
        this.id = String.valueOf(itemEntity.getId());
        this.name = itemEntity.getName();
        this.type = itemEntity.getType();
        this.price = itemEntity.getPrice();
        this.spec = new Spec(itemEntity.getCpu(), itemEntity.getCapacity());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
