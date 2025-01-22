package nowicki.piotr.spring_boot_docker.controller;

import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.auth.RegisterRequest;
import nowicki.piotr.spring_boot_docker.dto.GroupDto;
import nowicki.piotr.spring_boot_docker.dto.UserResponseDto;
import nowicki.piotr.spring_boot_docker.model.Group;
import nowicki.piotr.spring_boot_docker.service.GroupService;
import nowicki.piotr.spring_boot_docker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupPageController {

    private final GroupService groupService;
    private final UserService userService;

    @GetMapping("/addGroup")
    public String showAddGroupForm(Model model){
        model.addAttribute("group", new Group());
        List<UserResponseDto> userDtoList = userService.findAllUsers();
        model.addAttribute("users",userDtoList);
        return "desktop-add-group";
    }
    @PostMapping("/addGroup")
    public String addGroup(@ModelAttribute("group") GroupDto group, Model model){
        groupService.saveGroup(group);
        return "desktop-user-page";
    }
}
