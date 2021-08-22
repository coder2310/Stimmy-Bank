package stimmybank.com.stimmybank.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository){
        return args -> {
            User ahmed =new User("ahmed",
                      "salem",
                      "aks646",
                      "Python2310");
            User john = new User("john",
                    "cena",
                    "john2310",
                    "Python2310");
            userRepository.saveAll(List.of(ahmed,john));
        };
    }
}
