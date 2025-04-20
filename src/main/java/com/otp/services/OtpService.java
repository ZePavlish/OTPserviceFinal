package com.otp.services;

import com.otp.dao.OtpCodeDao;
import com.otp.models.OtpCode;
import com.otp.Sender.OtpSender;
import com.otp.Sender.EmailOtpSender;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class OtpService {
    private final OtpCodeDao otpDao;
    private final OtpSender otpSender;

    public OtpService(Connection connection) throws SQLException {
        this.otpDao = new OtpCodeDao(connection);
        this.otpSender = new EmailOtpSender();
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

        otpDao.saveOtpCode(otp);

        otpSender.send(channel, "Ваш OTP код: " + code); // Отправка OTP кода

        return code;
    }

    public boolean verifyOtp(int userId, String operationId, String code) throws SQLException {
        OtpCode otp = otpDao.getActiveOtpCodes().stream()
                .filter(o -> o.getUserId() == userId && o.getOperationId().equals(operationId))
                .findFirst().orElse(null);

        return otp != null && otp.getCode().equals(code) && otp.getExpiresAt().after(new Date());
    }

    private String generateRandomCode() {
        return String.format("%06d", (int)(Math.random() * 1000000));
    }
}
