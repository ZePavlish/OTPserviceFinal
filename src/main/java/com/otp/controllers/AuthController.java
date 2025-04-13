package com.otp.controllers;
import com.otp.services.AuthService;

import com.otp.models.User;
import com.otp.utils.JsonUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;

public class AuthController implements HttpHandler {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try (InputStream is = exchange.getRequestBody()) {
            String requestBody = new String(is.readAllBytes());
            User user = JsonUtils.parseJson(requestBody, User.class);

            boolean isRegistered = authService.register(user);
            exchange.sendResponseHeaders(isRegistered ? 200 : 400, -1);
        } catch (Exception e) {
            exchange.sendResponseHeaders(500, -1); // Internal Error
        } finally {
            exchange.close();
        }
    }
}