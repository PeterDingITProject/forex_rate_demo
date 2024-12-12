package com.example.demo.entity;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "forex_rates")
public class ForexRateEntity {

    @Id
    private String id;

    private LocalDateTime date;

    private BigDecimal usdToNtd;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getUsdToNtd() {
        return usdToNtd;
    }

    public void setUsdToNtd(BigDecimal usdToNtd) {
        this.usdToNtd = usdToNtd;
    }
}
