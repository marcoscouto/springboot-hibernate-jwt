package com.marcoscouto.cursomc.services;

import com.marcoscouto.cursomc.domain.Client;
import com.marcoscouto.cursomc.domain.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractMailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationEmail(Order order) {
        SimpleMailMessage sm = prepareSimpleMailMessageOrder(order);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageOrder(Order order){
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(order.getClient().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Order confirmed! Order number: " + order.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(order.toString());
        return sm;
    }

    @Override
    public void sendNewPasswordEmail(Client client, String newPass){
        SimpleMailMessage sm = prepareNewPasswordEmail(client, newPass);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(Client client, String newPass){
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(client.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Forgot my password " + client.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("New Password: " + newPass);
        return sm;
    }


}
