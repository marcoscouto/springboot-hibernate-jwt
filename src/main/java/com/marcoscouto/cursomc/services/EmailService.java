package com.marcoscouto.cursomc.services;

import com.marcoscouto.cursomc.domain.Client;
import com.marcoscouto.cursomc.domain.Order;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Order order);

    void sendEmail(SimpleMailMessage msg);

    void sendNewPasswordEmail(Client client, String newPass);

}
