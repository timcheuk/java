package com.timhappyjava.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SpringBootApplication
public class SpringsecurityApplication {
	
	static final Log log = LogFactory.getLog(SpringsecurityApplication.class);
	
	public static void main(String[] args) {
        Log log = LogFactory.getLog(SpringsecurityApplication.class);
        log.info("start...");
        log.warn("end.");
		SpringApplication.run(SpringsecurityApplication.class, args);
	}

}
