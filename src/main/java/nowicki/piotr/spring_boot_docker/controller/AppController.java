package nowicki.piotr.spring_boot_docker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AppController {
    @GetMapping("/hello")
    public String hello(){return "login";}
}
