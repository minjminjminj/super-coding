package com.github.supercoding.web.dto;

import lombok.Getter;

@Getter
public class Spec {
    private String cpu;
    private String capacity;

    public Spec() { }

    public Spec(String cpu, String capacity) {
        this.cpu = cpu;
        this.capacity = capacity;
    }
}
