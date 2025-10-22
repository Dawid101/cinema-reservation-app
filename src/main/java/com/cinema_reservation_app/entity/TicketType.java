package com.cinema_reservation_app.entity;


import lombok.Getter;

import java.math.BigDecimal;

@Getter

public enum TicketType {
    NORMAL(BigDecimal.valueOf(33.99)),
    STUDENT(BigDecimal.valueOf(29.99));

    private final BigDecimal price;

    TicketType(BigDecimal price) {
        this.price = price;
    }

}
