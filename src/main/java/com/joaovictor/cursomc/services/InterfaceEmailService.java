package com.joaovictor.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.joaovictor.cursomc.domain.Pedido;

public interface InterfaceEmailService {
	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);
}