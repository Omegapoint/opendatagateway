package com.omegapoint.opendatagateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class ApiController {

	@RequestMapping("/vat/format")
	@ResponseBody
	String format() {
		return "Hello World!";
	}

	@RequestMapping("/vat/search")
	@ResponseBody
	String search() {
		return "Hello World!";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApiController.class, args);
	}
}

