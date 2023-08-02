package com.github.supercoding.web.dto;

import lombok.Getter;

@Getter
public class BuyOrder {
    private Integer itemId;
    private Integer itemNums;

    public BuyOrder() {
    }
}
