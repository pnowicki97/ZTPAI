package nowicki.piotr.spring_boot_docker.controller;

import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.auth.RegisterRequest;
import nowicki.piotr.spring_boot_docker.dto.GroupDto;
import nowicki.piotr.spring_boot_docker.dto.UserResponseDto;
import nowicki.piotr.spring_boot_docker.model.Group;
import nowicki.piotr.spring_boot_docker.model.User;
import nowicki.piotr.spring_boot_docker.service.GroupService;
import nowicki.piotr.spring_boot_docker.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
    public String addGroup(@ModelAttribute("group") GroupDto group, @RequestParam(required = false) List<String> userIds){

        if (userIds != null && !userIds.isEmpty()) {
            // Process the selected user IDs
            groupService.saveGroup(group, userIds);
            // Add logic to create a group or associate users
        } else {
            groupService.saveGroup(group);
        }
 //       groupService.saveGroup(group, userIds);
        return "redirect:/users/showAllGroups";
    }

    @GetMapping("/showAllExpanses")
    public String showAllExpanses(Model model){
        return "desktop-group-page";
    }
}
