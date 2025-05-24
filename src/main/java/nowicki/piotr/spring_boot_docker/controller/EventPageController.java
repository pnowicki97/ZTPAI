package nowicki.piotr.spring_boot_docker.controller;

import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.dto.DutyDto;
import nowicki.piotr.spring_boot_docker.dto.EventDto;
import nowicki.piotr.spring_boot_docker.dto.GroupDto;
import nowicki.piotr.spring_boot_docker.dto.UserResponseDto;
import nowicki.piotr.spring_boot_docker.model.Balance;
import nowicki.piotr.spring_boot_docker.model.Duty;
import nowicki.piotr.spring_boot_docker.model.Event;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventPageController {

    private final EventService eventService;
    private final UserService userService;
    private final GroupService groupService;
    private final CalculationsService calculationsService;
    private final ServletContext servletContext;
    @GetMapping("/addEvent")
    public String showAddEventForm(@RequestParam("selectedGroup") String groupId, Model model){
        GroupDto selectedGroup = groupService.findById(groupId);
        model.addAttribute("group", selectedGroup);

        model.addAttribute("event", new Event());
        return "desktop-add-event";
    }
    @PostMapping("/addEvents")
    public String addEvent(@ModelAttribute("event") EventDto eventDto, @RequestParam("groupId") String groupId, @RequestParam("photoFile") MultipartFile photoFile, Model model) throws IOException {

        GroupDto selectedGroup = groupService.findById(groupId);
        model.addAttribute("selectedGroup", selectedGroup);
        model.addAttribute("group", selectedGroup);

        model.addAttribute("event", new Event());
        List<UserResponseDto> userDtoList = userService.findByGroupId(groupId);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        System.out.println("########################"+ eventDto.title() + " " + eventDto.start() + " " + eventDto.end());
        model.addAttribute("users",userDtoList);
        if (eventDto.title().isEmpty()){
            model.addAttribute("message", "Name, and done by can not be empty");
            return "desktop-add-event";
        }
        if (groupId != null) {
            eventService.saveEvent(eventDto, user.getId(), groupId, "/images/" + photoFile.getOriginalFilename());
        } else {
            return "desktop-add-event";
        }
        return "redirect:/groups?selectedGroup=" + groupId;
    }
}
