package com.otp.Sender;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailOtpSender implements OtpSender {

    @Override
    public void send(String destination, String message) {
        // Настройки для почтового сервера
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Настройка сессии
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("your-email@gmail.com", "your-password");
            }
        });

        try {
            // Создание сообщения
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("your-email@gmail.com"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(destination));
            msg.setSubject("Your OTP Code");
            msg.setText(message);

            // Отправка сообщения
            Transport.send(msg);
            System.out.println("OTP sent to " + destination);
        } catch (MessagingException e) {
            e.printStackTrace(); // обработка ошибок
        }
    }
}
