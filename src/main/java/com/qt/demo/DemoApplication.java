package com.qt.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import sun.misc.BASE64Encoder;

@SpringBootApplication
@ServletComponentScan(basePackages = {"com.qt.demo"})
@EnableScheduling
@EnableAsync
public class DemoApplication {

	public static void main(String[] args) {
//		 String DATA = "XAccessKey:U2FsdGVkX1/GGZG1vdctM9+BnB8MJKWcbHJKOsijI+8=";
//		BASE64Encoder encoder = new BASE64Encoder();
//		String data = encoder.encode(DATA.getBytes());
////		log.info("BASE64加密：{}",data);
//		System.out.println(data);
		SpringApplication.run(DemoApplication.class, args);
	}

}
