package nowicki.piotr.spring_boot_docker.controller;

import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.auth.RegisterRequest;
import nowicki.piotr.spring_boot_docker.dto.ExpenseDto;
import nowicki.piotr.spring_boot_docker.dto.GroupDto;
import nowicki.piotr.spring_boot_docker.dto.UserResponseDto;
import nowicki.piotr.spring_boot_docker.model.Group;
import nowicki.piotr.spring_boot_docker.model.User;
import nowicki.piotr.spring_boot_docker.service.ExpenseService;
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
    private final ExpenseService expenseService;

    @GetMapping("/addGroup")
    public String showAddGroupForm(Model model){
        model.addAttribute("group", new Group());
        List<UserResponseDto> userDtoList = userService.findAllUsers();
        model.addAttribute("users",userDtoList);
        return "desktop-add-group";
    }
    @PostMapping("/addGroup")
    public String addGroup(@ModelAttribute("group") GroupDto group, @RequestParam(required = false) List<String> userIds, Model model){

        if (group.name().isEmpty()){
            List<UserResponseDto> userDtoList = userService.findAllUsers();
            model.addAttribute("users",userDtoList);
            model.addAttribute("message", "Group name can not be empty");
            return "desktop-add-group";
        }
        if (userIds != null && !userIds.isEmpty()) {
            groupService.saveGroup(group, userIds);
        } else {
            groupService.saveGroup(group);
        }
        return "redirect:/users";
    }

    @GetMapping
    public String showAllExpenses(@RequestParam("selectedGroup") String groupName, Model model){
        List<ExpenseDto> expenses = expenseService.findAllByGroupId(groupName);
        GroupDto selectedGroup = groupService.findById(groupName);
        model.addAttribute("expenses",expenses);
        model.addAttribute("selectedGroup", selectedGroup);
        return "desktop-group-page";
    }
}
