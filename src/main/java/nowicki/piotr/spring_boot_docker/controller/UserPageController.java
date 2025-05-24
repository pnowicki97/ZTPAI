package nowicki.piotr.spring_boot_docker.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.auth.AuthenticationRequest;
import nowicki.piotr.spring_boot_docker.dto.EventDto;
import nowicki.piotr.spring_boot_docker.dto.GroupDto;
import nowicki.piotr.spring_boot_docker.dto.UserDto;
import nowicki.piotr.spring_boot_docker.dto.UserResponseDto;
import nowicki.piotr.spring_boot_docker.model.User;
import nowicki.piotr.spring_boot_docker.model.Group;
import nowicki.piotr.spring_boot_docker.service.AuthenticationService;
import nowicki.piotr.spring_boot_docker.service.EventService;
import nowicki.piotr.spring_boot_docker.service.GroupService;
import nowicki.piotr.spring_boot_docker.service.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserPageController {

    private final UserService userService;
    private final GroupService groupService;
    private final EventService eventService;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String showAllGroups(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<GroupDto> groups = groupService.findAllByUserId(user.getId());
        List<EventDto> events = eventService.findAllByUserId(user.getId());
        model.addAttribute("groups",groups);
        model.addAttribute("events",events);
        return "home-page";
    }

    @GetMapping("/changeUser")
    public String showChangeUserForm(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "user-page";
    }
    @PostMapping("/changeUser")
    public String changeUser(@ModelAttribute("user") UserDto userDto, @RequestParam("newPassword") String newPassword, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        AuthenticationRequest request = new AuthenticationRequest(user.getName(),userDto.password());
        try {
            authenticationService.authenticate(request);
            userService.editUser(userDto, passwordEncoder.encode(newPassword));
            return "login";
        }
        catch (Exception e) {
            model.addAttribute("message", "Change failed: " + e.getMessage());
            return "user-page";
        }
    }
}
