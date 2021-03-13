package com.example.travel.service;

public interface MailService {
    Boolean send(String toEmail, String subject, String text);
}