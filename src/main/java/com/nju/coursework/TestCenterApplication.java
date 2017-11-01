package com.nju.coursework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.nju.coursework")
public class TestCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestCenterApplication.class, args);
	}

}
