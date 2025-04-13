package com.otp.dao;

import com.otp.models.OtpCode;
import java.sql.*;
import java.util.Date;

public class OtpDao {
    private final Connection connection;

    public OtpDao() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/otp_service",
                "postgres",
                "MArio12345"
        );
    }

    public void save(OtpCode otp) throws SQLException {
        String sql = "INSERT INTO otp_codes (user_id, operation_id, code, status, channel, expires_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, otp.getUserId());
            stmt.setString(2, otp.getOperationId());
            stmt.setString(3, otp.getCode());
            stmt.setString(4, otp.getStatus());
            stmt.setString(5, otp.getChannel());
            stmt.setTimestamp(6, new Timestamp(otp.getExpiresAt().getTime()));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    otp.setId(rs.getInt(1));
                }
            }
        }
    }

    public OtpCode findActiveCode(int userId, String operationId) throws SQLException {
        String sql = "SELECT * FROM otp_codes WHERE user_id = ? AND operation_id = ? AND status = 'ACTIVE' AND expires_at > NOW()";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, operationId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                OtpCode otp = new OtpCode();
                otp.setId(rs.getInt("id"));
                otp.setCode(rs.getString("code"));
                otp.setStatus(rs.getString("status"));
                otp.setExpiresAt(new Date(rs.getTimestamp("expires_at").getTime()));
                return otp;
            }
            return null;
        }
    }
}