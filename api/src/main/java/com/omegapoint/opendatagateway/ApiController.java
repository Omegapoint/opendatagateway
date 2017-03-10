package com.omegapoint.opendatagateway;

import com.omegapoint.opendatagateway.domain.Vatmark;
import com.omegapoint.opendatagateway.repositories.VatmarkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@EnableAutoConfiguration
public class ApiController {

	@Autowired
	private VatmarkerRepository repository;

	@RequestMapping("/vat/format")
	@ResponseBody
	String format() {
		return "Hello World!";
	}

	@RequestMapping("/vat/search")
	@ResponseBody
	List<Vatmark> search(String kommun) {
		return repository.findAllByKommun(kommun);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApiController.class, args);
	}
}

