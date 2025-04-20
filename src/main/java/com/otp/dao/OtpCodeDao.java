package com.otp.dao;

import com.otp.models.OtpCode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OtpCodeDao {
    private Connection connection;

    public OtpCodeDao(Connection connection) {
        this.connection = connection;
    }

    public void saveOtpCode(OtpCode otpCode) throws SQLException {
        String query = "INSERT INTO otp_codes (user_id, operation_id, code, status, channel, expires_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, otpCode.getUserId());
            stmt.setString(2, otpCode.getOperationId());
            stmt.setString(3, otpCode.getCode());
            stmt.setString(4, otpCode.getStatus());
            stmt.setString(5, otpCode.getChannel());
            stmt.setTimestamp(6, new Timestamp(otpCode.getExpiresAt().getTime()));
            stmt.executeUpdate();
        }
    }

    public List<OtpCode> getActiveOtpCodes() throws SQLException {
        String query = "SELECT * FROM otp_codes WHERE status = 'ACTIVE'";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            List<OtpCode> otpCodes = new ArrayList<>();
            while (rs.next()) {
                OtpCode otpCode = new OtpCode();
                otpCode.setId(rs.getInt("id"));
                otpCode.setUserId(rs.getInt("user_id"));
                otpCode.setOperationId(rs.getString("operation_id"));
                otpCode.setCode(rs.getString("code"));
                otpCode.setStatus(rs.getString("status"));
                otpCode.setChannel(rs.getString("channel"));
                otpCode.setExpiresAt(rs.getTimestamp("expires_at"));
                otpCodes.add(otpCode);
            }
            return otpCodes;
        }
    }

    public void updateOtpCodeStatus(int otpCodeId, String status) throws SQLException {
        String query = "UPDATE otp_codes SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, otpCodeId);
            stmt.executeUpdate();
        }
    }
}
