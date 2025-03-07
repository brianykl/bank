package com.example.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * entry point for the http server
 * exclude DataSourceAutoConfiguration since the application stores data in-memory
 */

 @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
 public class BankApplication {
 
	 public static void main(String[] args) {
		 SpringApplication.run(BankApplication.class, args);
	 }
 
 }
