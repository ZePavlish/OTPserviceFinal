package com.otp.services;

import com.otp.dao.OtpDao;
import com.otp.models.OtpCode;
import java.sql.SQLException;
import java.util.Date;

public class OtpService {
    private final OtpDao otpDao;

    public OtpService() throws SQLException {
        this.otpDao = new OtpDao();
    }

    public String createOtp(int userId, String operationId, String channel) throws SQLException {
        String code = generateRandomCode();

        OtpCode otp = new OtpCode();
        otp.setUserId(userId);
        otp.setOperationId(operationId);
        otp.setCode(code);
        otp.setStatus("ACTIVE");
        otp.setChannel(channel);
        otp.setExpiresAt(new Date(System.currentTimeMillis() + 300000));

        otpDao.save(otp);
        return code;
    }

    public boolean verifyOtp(int userId, String operationId, String code) throws SQLException {
        OtpCode otp = otpDao.findActiveCode(userId, operationId);
        return otp != null && otp.getCode().equals(code);
    }

    private String generateRandomCode() {
        return String.format("%06d", (int)(Math.random() * 1000000));
    }
}