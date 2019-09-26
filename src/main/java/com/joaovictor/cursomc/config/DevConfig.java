package com.joaovictor.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.joaovictor.cursomc.services.DBService;
import com.joaovictor.cursomc.services.InterfaceEmailService;
import com.joaovictor.cursomc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instanciateDataBase() throws ParseException {
		if (!"create".equals(strategy) && !"create-drop".equals(strategy)) {
			return false;
		}
		
		dbService.instanciateTestDataBase();
		return true;
	}
	
	@Bean
	public InterfaceEmailService interfaceEmailService() {
		return new SmtpEmailService();
	}
}
