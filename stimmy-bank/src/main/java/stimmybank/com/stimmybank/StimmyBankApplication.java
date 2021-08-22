package stimmybank.com.stimmybank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StimmyBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(StimmyBankApplication.class, args);
	}




}
