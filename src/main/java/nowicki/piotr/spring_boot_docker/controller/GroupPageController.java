package nowicki.piotr.spring_boot_docker.controller;

import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.auth.RegisterRequest;
import nowicki.piotr.spring_boot_docker.dto.*;
import nowicki.piotr.spring_boot_docker.model.Group;
import nowicki.piotr.spring_boot_docker.model.User;
import nowicki.piotr.spring_boot_docker.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupPageController {

    private final GroupService groupService;
    private final UserService userService;
    private final ExpenseService expenseService;
    private final DutyService dutyService;
    private final EventService eventService;
    private final ServletContext servletContext;

    @GetMapping("/addGroup")
    public String showAddGroupForm(Model model){
        model.addAttribute("group", new Group());
        List<UserResponseDto> userDtoList = userService.findAllUsers();
        model.addAttribute("users",userDtoList);
        return "add-group-page";
    }
    @PostMapping("/addGroup")
    public String addGroup(@ModelAttribute("group") GroupDto group, @RequestParam(required = false) List<String> userIds, @RequestParam("photoFile") MultipartFile photoFile, Model model) throws IOException {

        if (group.name().isEmpty()){
            List<UserResponseDto> userDtoList = userService.findAllUsers();
            model.addAttribute("users",userDtoList);
            model.addAttribute("message", "Group name can not be empty");
            return "add-group-page";
        }
        if (!photoFile.isEmpty()) {
            String realPathToUploads = servletContext.getRealPath("/uploads/");
            File uploadDir = new File(realPathToUploads);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String originalFilename = photoFile.getOriginalFilename();
            //TODO: zmienić tak, żeby działało od razu a nie po restarcie
            File dest = new File("C:\\Users\\nowik\\Documents\\ZTPAI\\SplitWithMe\\src\\main\\resources\\static\\images/" + originalFilename);
            photoFile.transferTo(dest);
        }
        if (userIds != null && !userIds.isEmpty()) {
            groupService.saveGroup(group, userIds, "/images/" + photoFile.getOriginalFilename());
        } else {
            groupService.saveGroup(group, "/images/" + photoFile.getOriginalFilename());
        }
        return "redirect:/users";
    }

    @GetMapping
    public String showAllExpenses(@RequestParam("selectedGroup") String groupName, Model model){
        List<ExpenseDto> expenses = expenseService.findAllByGroupId(groupName);
        List<DutyDto> duties = dutyService.findAllByGroupId(groupName);
        GroupDto selectedGroup = groupService.findById(groupName);
        List<EventDto> events = eventService.findAllByGroupId(groupName);
        model.addAttribute("expenses",expenses);
        model.addAttribute("selectedGroup", selectedGroup);
        model.addAttribute("duties",duties);
        model.addAttribute("events",events);
        return "group-page";
    }
}

