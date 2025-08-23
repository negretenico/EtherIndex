package com.negretenico.ether.index;

import com.negretenico.ether.index.model.AlchemyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableConfigurationProperties(AlchemyProperties.class)
public class EtherIndexApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtherIndexApplication.class, args);
	}

}
