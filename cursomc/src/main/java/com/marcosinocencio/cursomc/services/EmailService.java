/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosinocencio.cursomc.services;

import com.marcosinocencio.cursomc.domain.Cliente;
import com.marcosinocencio.cursomc.domain.Pedido;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;

/**
 *
 * @author Vinicius
 */
public interface EmailService {
    void sendOrderConfirmationEmail(Pedido obj);
    void sendEmail(SimpleMailMessage msg);
    void sendOrderConfirmationHtmlEmail(Pedido obj); 
    void sendHtmlEmail(MimeMessage msg);
    void sendNewPasswordEmail(Cliente cliente, String newPass);
}
