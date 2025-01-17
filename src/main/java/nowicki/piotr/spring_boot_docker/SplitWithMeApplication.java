package nowicki.piotr.spring_boot_docker;

import nowicki.piotr.spring_boot_docker.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@SpringBootApplication
@RestController
public class SplitWithMeApplication {

	@GetMapping("/")
	public String home() {
		return "login";
	}

	public static void main(String[] args) {
		SpringApplication.run(SplitWithMeApplication.class, args);
	}

}
