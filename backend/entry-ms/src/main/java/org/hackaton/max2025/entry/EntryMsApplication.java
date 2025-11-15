package org.hackaton.max2025.entry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EntryMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntryMsApplication.class, args);
	}

}
