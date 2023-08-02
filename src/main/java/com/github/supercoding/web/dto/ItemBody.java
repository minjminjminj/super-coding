package com.github.supercoding.web.dto;

import lombok.Getter;

@Getter
public class ItemBody {
    private String name;
    private String type;
    private int price;
    private Spec spec;

    public ItemBody() { }
}
