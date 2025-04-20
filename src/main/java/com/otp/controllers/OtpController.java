package com.otp.controllers;

import com.otp.dto.OtpRequest;
import com.otp.dto.OtpResponse;
import com.otp.services.OtpService;
import com.otp.Sender.EmailOtpSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class OtpController implements HttpHandler {
    private final OtpService otpService;
    private final EmailOtpSender emailOtpSender;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(OtpController.class);

    // Внедрение зависимостей через конструктор
    public OtpController(OtpService otpService, EmailOtpSender emailOtpSender) {
        this.otpService = otpService;
        this.emailOtpSender = emailOtpSender;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            // Обработка запроса на генерацию OTP
            if ("POST".equals(method) && "/generate".equals(path)) {
                OtpRequest otpRequest = objectMapper.readValue(exchange.getRequestBody(), OtpRequest.class);
                String otpCode = otpService.createOtp(Integer.parseInt(otpRequest.getUserId()), otpRequest.getOperationId(), "email");

                // Отправка OTP на email
                emailOtpSender.send("email@example.com", "Ваш OTP код: " + otpCode);

                OtpResponse otpResponse = new OtpResponse();
                otpResponse.setOtpCode(otpCode);
                otpResponse.setStatus("OTP Generated Successfully");

                response = objectMapper.writeValueAsString(otpResponse);
                exchange.sendResponseHeaders(200, response.getBytes().length);
                logger.info("OTP generated and sent for user: {}", otpRequest.getUserId());

                // Обработка запроса на валидацию OTP
            } else if ("POST".equals(method) && "/validate".equals(path)) {
                OtpRequest otpRequest = objectMapper.readValue(exchange.getRequestBody(), OtpRequest.class);
                boolean isValid = otpService.verifyOtp(Integer.parseInt(otpRequest.getUserId()), otpRequest.getOperationId(), otpRequest.getOtpCode());

                OtpResponse otpResponse = new OtpResponse();
                otpResponse.setStatus(isValid ? "OTP Validated Successfully" : "Invalid OTP");

                response = objectMapper.writeValueAsString(otpResponse);
                exchange.sendResponseHeaders(isValid ? 200 : 400, response.getBytes().length);
                logger.info("OTP validation for user {}: {}", otpRequest.getUserId(), isValid ? "successful" : "failed");

                // Обработка запросов GET
            } else if ("GET".equals(method)) {
                response = "The server is running. Send a POST request to generate or validate OTP.";
                exchange.sendResponseHeaders(200, response.getBytes().length);

                // Обработка ошибок HTTP методов
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                return;
            }

            // Отправка ответа
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }

        } catch (SQLException e) {
            logger.error("Database error occurred while processing request", e);
            exchange.sendResponseHeaders(500, -1); // Internal Server Error
        } catch (Exception e) {
            logger.error("Error processing the request", e);
            exchange.sendResponseHeaders(500, -1); // Internal Server Error
        } finally {
            exchange.close();
        }
    }
}
