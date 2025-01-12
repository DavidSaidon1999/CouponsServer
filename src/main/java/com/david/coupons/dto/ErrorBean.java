package com.david.coupons.dto;

public class ErrorBean {

    private int errorNumber;
    private String errorMessage;

    public ErrorBean(int errorNumber, String errorMessage) {
        this.errorNumber = errorNumber;
        this.errorMessage = errorMessage;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "ErrorBean{" +
                "errorNumber=" + errorNumber +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
