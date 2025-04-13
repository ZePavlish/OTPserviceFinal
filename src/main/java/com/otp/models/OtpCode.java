package com.otp.models;

import java.util.Date;

public class OtpCode {
    private int id;
    private int userId;
    private String operationId;
    private String code;
    private String status; // ACTIVE, EXPIRED, USED
    private String channel; // SMS, EMAIL, TELEGRAM
    private Date expiresAt;

    // Геттеры
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getOperationId() { return operationId; }
    public String getCode() { return code; }
    public String getStatus() { return status; }
    public String getChannel() { return channel; }
    public Date getExpiresAt() { return expiresAt; }

    // Сеттеры
    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setOperationId(String operationId) { this.operationId = operationId; }
    public void setCode(String code) { this.code = code; }
    public void setStatus(String status) { this.status = status; }
    public void setChannel(String channel) { this.channel = channel; }
    public void setExpiresAt(Date expiresAt) { this.expiresAt = expiresAt; }
}