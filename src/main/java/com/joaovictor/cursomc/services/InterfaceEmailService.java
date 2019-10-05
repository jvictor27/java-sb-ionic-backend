package com.joaovictor.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.joaovictor.cursomc.domain.Cliente;
import com.joaovictor.cursomc.domain.Pedido;

public interface InterfaceEmailService {
	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Pedido obj);

	void sendHtmlEmail(MimeMessage msg);
	
	void sendNewPasswordEmail(Cliente cliente, String newPass);
}