package nowicki.piotr.spring_boot_docker.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    @GetMapping("/hello")
    public String hello(){return "Hello World";}
}
