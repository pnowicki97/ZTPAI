package nowicki.piotr.spring_boot_docker.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.dto.GroupDto;
import nowicki.piotr.spring_boot_docker.dto.UserDto;
import nowicki.piotr.spring_boot_docker.dto.UserResponseDto;
import nowicki.piotr.spring_boot_docker.service.GroupService;
import nowicki.piotr.spring_boot_docker.service.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserPageController {

    private final UserService userService;
    private final GroupService groupService;
    @GetMapping
    public String showAllUsers(Model model){
        model.addAttribute("message", "Welcome to your dashboard!");
        List<UserResponseDto> userDtoList = userService.findAllUsers();
        model.addAttribute("users",userDtoList);
        return "desktop-user-page";
    }

    @GetMapping("/showAllGroups")
    public String showAllGroups(Model model){
        List<GroupDto> groups = groupService.findAllGroups();
        System.out.println("1" );
        for(GroupDto group : groups){
            System.out.println("1" + group.name());
        }
        model.addAttribute("groups",groups);
        return "desktop-user-page";
    }
}
