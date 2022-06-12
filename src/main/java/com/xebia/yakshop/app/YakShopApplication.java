package com.xebia.yakshop.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.xebia.yakshop.*")
public class YakShopApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(YakShopApplication.class, args);
	}
}
