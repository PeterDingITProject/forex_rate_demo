package com.example.demo.vo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ForexResponse {

    private LocalDate date;

    private BigDecimal usd;

    public ForexResponse(LocalDate date, BigDecimal usd) {
        this.date = date;
        this.usd = usd;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getUsd() {
        return usd;
    }

    public void setUsd(BigDecimal usd) {
        this.usd = usd;
    }
}
