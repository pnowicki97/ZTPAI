package nowicki.piotr.spring_boot_docker.controller;

import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.dto.DutyDto;
import nowicki.piotr.spring_boot_docker.dto.EventDto;
import nowicki.piotr.spring_boot_docker.dto.GroupDto;
import nowicki.piotr.spring_boot_docker.dto.UserResponseDto;
import nowicki.piotr.spring_boot_docker.model.Duty;
import nowicki.piotr.spring_boot_docker.model.Event;
import nowicki.piotr.spring_boot_docker.model.User;
import nowicki.piotr.spring_boot_docker.service.EventService;
import nowicki.piotr.spring_boot_docker.service.GroupService;
import nowicki.piotr.spring_boot_docker.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarPageController {

    private final UserService userService;
    private final GroupService groupService;
    private final EventService eventService;

    @PostMapping("/addEvents")
    public String addEvent(@RequestBody EventDto eventDto, @RequestParam("selectedGroup") String groupName, Model model){

        GroupDto selectedGroup = groupService.findById(groupName);
        List<UserResponseDto> userDtoList = userService.findByGroupId(groupName);

        model.addAttribute("selectedGroup", selectedGroup);
        model.addAttribute("group", selectedGroup);
        model.addAttribute("event", new Event());
        model.addAttribute("users",userDtoList);

        if (eventDto.title().isEmpty()||eventDto.start() == null||eventDto.end() == null){
            model.addAttribute("message", "Name, amount and done by can not be empty");
            return "group-page";
        }
        if (groupName != null) {
            for(UserResponseDto user : userDtoList)
                eventService.saveEvent(eventDto, user.id(), groupName);
        } else {
            return "group-page";
        }
        return "group-page";
    }

}
