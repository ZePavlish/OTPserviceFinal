package com.otp.Sender;

import java.io.FileWriter;
import java.io.IOException;

public class FileOtpSender implements OtpSender {

    @Override
    public void send(String destination, String message) {
        try (FileWriter writer = new FileWriter("otp_log.txt", true)) {
            writer.write("To: " + destination + " | Message: " + message + "\n");
        } catch (IOException e) {
            e.printStackTrace(); // можно заменить на логгер
        }
    }
}
