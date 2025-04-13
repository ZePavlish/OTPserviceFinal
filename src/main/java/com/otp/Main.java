package com.otp;
import com.otp.services.AuthService;

import com.otp.controllers.AuthController;
import com.otp.controllers.OtpController;
import com.otp.services.OtpService;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. Инициализируем сервисы
            AuthService authService = new AuthService();
            OtpService otpService = new OtpService();

            // 2. Создаем HTTP-сервер с бэклогом (очередь соединений)
            HttpServer server = HttpServer.create(
                    new InetSocketAddress(8080),
                    50 // бэклог
            );

            // 3. Регистрируем обработчики с корневым путем
            server.createContext("/api/auth", new AuthController(authService));
            server.createContext("/api/otp", new OtpController(otpService));

            // 4. Настраиваем executor (пул потоков)
            server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());

            // 5. Запускаем сервер
            server.start();
            System.out.println("✅ Сервер запущен на http://localhost:8080");

            // 6. Добавляем shutdown hook для graceful shutdown
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("🛑 Остановка сервера...");
                server.stop(1); // задержка перед остановкой (секунды)
            }));

        } catch (Exception e) {
            System.err.println("❌ Ошибка при запуске сервера:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}