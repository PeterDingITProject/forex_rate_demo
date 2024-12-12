package com.example.demo.vo;

import java.util.List;

public class SuccessResponse {

    private String code;

    private String message;

    private List<ForexResponse> currency;

    public SuccessResponse(String code, String message, List<ForexResponse> currency) {
        this.code = code;
        this.message = message;
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ForexResponse> getCurrency() {
        return currency;
    }

    public void setCurrency(List<ForexResponse> currency) {
        this.currency = currency;
    }
}
