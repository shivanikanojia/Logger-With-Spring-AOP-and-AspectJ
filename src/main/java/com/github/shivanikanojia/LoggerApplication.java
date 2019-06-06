package com.github.shivanikanojia;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class LoggerApplication {

	public static void main(String[] args) {
final SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder();
//		springApplicationBuilder.sources(AopConfig.class);
//	    springApplicationBuilder.showBanner(true);
	    springApplicationBuilder.logStartupInfo(true);
	    springApplicationBuilder.run(args);
//		SpringApplication.run(LoggerApplication.class, args);
	}

}
