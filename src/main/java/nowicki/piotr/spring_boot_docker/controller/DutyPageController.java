package nowicki.piotr.spring_boot_docker.controller;

import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.dto.DutyDto;
import nowicki.piotr.spring_boot_docker.dto.ExpenseDto;
import nowicki.piotr.spring_boot_docker.dto.GroupDto;
import nowicki.piotr.spring_boot_docker.dto.UserResponseDto;
import nowicki.piotr.spring_boot_docker.model.Balance;
import nowicki.piotr.spring_boot_docker.model.Duty;
import nowicki.piotr.spring_boot_docker.service.CalculationsService;
import nowicki.piotr.spring_boot_docker.service.DutyService;
import nowicki.piotr.spring_boot_docker.service.GroupService;
import nowicki.piotr.spring_boot_docker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/duties")
public class DutyPageController {

    private final DutyService dutyService;
    private final UserService userService;
    private final GroupService groupService;
    private final CalculationsService calculationsService;
    private final ServletContext servletContext;
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
    public String addDuty(@ModelAttribute("duty") DutyDto dutyDto, @RequestParam("userId") String userId, @RequestParam("groupId") String groupId, @RequestParam("photoFile") MultipartFile photoFile, Model model) throws IOException {

        GroupDto selectedGroup = groupService.findById(groupId);
        model.addAttribute("selectedGroup", selectedGroup);
        model.addAttribute("group", selectedGroup);

        model.addAttribute("duty", new Duty());
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
        System.out.println("########################"+ dutyDto.name() + " " + dutyDto.beginDate() + " " + dutyDto.endDate());
        model.addAttribute("users",userDtoList);
        if (dutyDto.name().isEmpty()||dutyDto.value() == null||userId.isEmpty()){
            model.addAttribute("message", "Name, amount and done by can not be empty");
            return "desktop-add-duty";
        }
        if (groupId != null) {
            dutyService.saveDuty(dutyDto, userId, groupId, "/images/" + photoFile.getOriginalFilename());
        } else {
            return "desktop-add-expense";
        }
        return "redirect:/groups?selectedGroup=" + groupId;
    }

    @GetMapping("/dutiesOverview")
    public String showExpenseOverview(@RequestParam("selectedGroup") String groupId, Model model){
        GroupDto selectedGroup = groupService.findById(groupId);
        model.addAttribute("group", selectedGroup);
        List<DutyDto> duties = dutyService.findAllByGroupId(groupId);

        model.addAttribute("duties",duties);
        List<UserResponseDto> userDtoList = userService.findByGroupId(groupId);

        model.addAttribute("users",userDtoList);

        List<Balance> balances = calculationsService.getDutiesBalances(groupId);
        model.addAttribute("balances", balances);

        return "duties-overview-page";
    }
}
