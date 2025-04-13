package com.otp.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.otp.dao.UserDao;
import com.otp.models.User;
import java.sql.SQLException;


public class AuthService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService() throws SQLException {
        this.userDao = new UserDao();
        this.passwordEncoder = new BCryptPasswordEncoder(); // Инициализация BCryptPasswordEncoder
    }

    // Метод регистрации
    public boolean register(User user) {
        try {
            // Шифруем пароль перед сохранением
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword); // Устанавливаем зашифрованный пароль

            userDao.create(user); // Сохраняем пользователя в базе данных
            return true;
        } catch (SQLException e) {
            System.err.println("Ошибка регистрации: " + e.getMessage());
            return false;
        }
    }

    // Метод логина
    public User login(String username, String password) {
        try {
            User user = userDao.findByUsername(username);
            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                // Проверяем введенный пароль с зашифрованным в базе
                return user;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Ошибка входа: " + e.getMessage());
            return null;
        }
    }
}
