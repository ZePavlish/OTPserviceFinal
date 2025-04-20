package com.otp.dto;

public class OtpRequest {
    private String userId;
    private String operationId;
    private String otpCode;  // Добавлено поле для otpCode

    // Геттеры и сеттеры
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getOtpCode() {  // Добавлен геттер
        return otpCode;
    }

    public void setOtpCode(String otpCode) {  // Добавлен сеттер
        this.otpCode = otpCode;
    }
}

