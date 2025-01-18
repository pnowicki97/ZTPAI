package nowicki.piotr.spring_boot_docker.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.dto.UserResponseDto;
import nowicki.piotr.spring_boot_docker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserPageController {

    private final UserService userService;
    @GetMapping
    public String showAllUsers(HttpSession session, Model model){
        model.addAttribute("message", "Welcome to your dashboard!");
        String token = (String) session.getAttribute("jwtToken");
        model.addAttribute("jwtToken", token);

        return "desktop-user-page";
    }
}
