package com.otp;

import com.otp.services.OtpService;
import com.otp.controllers.OtpController;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        try {
            // Подключение к базе данных PostgreSQL
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/otp_service", "postgres", "MArio12345");

            // Создание сервиса OTP
            OtpService otpService = new OtpService(connection);

            // Создание и запуск HTTP сервера
            HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
            HttpHandler handler = new OtpController(otpService);
            server.createContext("/otp", handler);
            server.start();

            System.out.println("Server started on port 8081");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
