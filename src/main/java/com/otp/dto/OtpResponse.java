package com.otp.dto;

public class OtpResponse {
    private String otpCode;
    private String status;

    // геттеры и сеттеры
    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
