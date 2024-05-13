package com.batch.springBatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SpringBatchAplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchAplicationApplication.class, args);
	}

}
