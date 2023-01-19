package com.shortit4me;

import com.shortit4me.controller.LinkController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class LinkShortenerApplication {

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(LinkShortenerApplication.class);
	}*/

	public static void main(String[] args) {
		SpringApplication.run(LinkShortenerApplication.class, args);
	}
}

/*@SpringBootApplication
public class LinkShortenerApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(LinkShortenerApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(LinkController.class, args);
	}
}*/
