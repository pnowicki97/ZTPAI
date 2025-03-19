package nowicki.piotr.spring_boot_docker.controller;

import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.dto.DutyDto;
import nowicki.piotr.spring_boot_docker.dto.GroupDto;
import nowicki.piotr.spring_boot_docker.dto.UserResponseDto;
import nowicki.piotr.spring_boot_docker.model.Duty;
import nowicki.piotr.spring_boot_docker.service.DutyService;
import nowicki.piotr.spring_boot_docker.service.GroupService;
import nowicki.piotr.spring_boot_docker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/duties")
public class DutyPageController {

    private final DutyService dutyService;
    private final UserService userService;
    private final GroupService groupService;
    @GetMapping("/addDuty")
    public String showAddDutyForm(@RequestParam("selectedGroup") String groupId, Model model){
        GroupDto selectedGroup = groupService.findById(groupId);
        model.addAttribute("group", selectedGroup);

        model.addAttribute("duty", new Duty());

        List<UserResponseDto> userDtoList = userService.findByGroupId(groupId);

        model.addAttribute("users",userDtoList);

        return "desktop-add-duty";
    }
    @PostMapping("/addDuties")
    public String addDuty(@ModelAttribute("duty") DutyDto dutyDto, @RequestParam("userId") String userId, @RequestParam("groupId") String groupId, Model model){

        GroupDto selectedGroup = groupService.findById(groupId);
        model.addAttribute("selectedGroup", selectedGroup);
        model.addAttribute("group", selectedGroup);

        model.addAttribute("duty", new Duty());
        List<UserResponseDto> userDtoList = userService.findByGroupId(groupId);

        model.addAttribute("users",userDtoList);
        if (dutyDto.name().isEmpty()||dutyDto.value() == null||userId.isEmpty()){
            model.addAttribute("message", "Name, amount and done by can not be empty");
            return "desktop-add-duty";
        }
        if (groupId != null) {
            dutyService.saveDuty(dutyDto, userId, groupId);
        } else {
            return "desktop-add-expense";
        }
        return "redirect:/groups?selectedGroup=" + groupId;
    }

}
