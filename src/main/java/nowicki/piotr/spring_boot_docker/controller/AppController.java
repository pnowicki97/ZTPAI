package nowicki.piotr.spring_boot_docker.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.auth.AuthenticationRequest;
import nowicki.piotr.spring_boot_docker.auth.AuthenticationResponse;
import nowicki.piotr.spring_boot_docker.auth.RegisterRequest;
import nowicki.piotr.spring_boot_docker.model.User;
import nowicki.piotr.spring_boot_docker.service.AuthenticationService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AppController {

    private final AuthenticationService authenticationService;

    @GetMapping("/registerUser")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/registerUser")
    public String register(@ModelAttribute("user") RegisterRequest registerRequest, Model model){
        try {
            AuthenticationResponse response = authenticationService.register(registerRequest);
            return "redirect:/auth/login";
        }
        catch (Exception e) {
            model.addAttribute("message", "Registration failed: " + e.getMessage());
            return "register";
        }
    }
    @GetMapping("/login")
    public String showLoginForm(Model model){
        model.addAttribute("user", new User());
        return "login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute("user") AuthenticationRequest authenticationRequest,Model model, HttpServletResponse response){
        try {
            AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
            model.addAttribute("message", "User successfully logged!");
            model.addAttribute("jwtToken", authenticationResponse.getToken());
            Cookie jwtCookie = new Cookie("jwtToken", authenticationResponse.getToken());
            jwtCookie.setHttpOnly(true); // Prevent access via JavaScript
            jwtCookie.setSecure(false); // Set to true in production (requires HTTPS)
            jwtCookie.setPath("/"); // Cookie is sent with all requests to this domain
            jwtCookie.setMaxAge(24 * 60 * 60); // Token is valid for 7 days

            // Add the cookie to the response
            response.addCookie(jwtCookie);
            return "redirect:/users/showAllGroups";
        }
        catch (Exception e) {
            model.addAttribute("message", "Login failed: " + e.getMessage());
            return "login";
        }
    }
}
