package nowicki.piotr.spring_boot_docker.controller;

import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.dto.ExpenseDto;
import nowicki.piotr.spring_boot_docker.dto.GroupDto;
import nowicki.piotr.spring_boot_docker.dto.UserResponseDto;
import nowicki.piotr.spring_boot_docker.model.Balance;
import nowicki.piotr.spring_boot_docker.model.Expense;
import nowicki.piotr.spring_boot_docker.model.Group;
import nowicki.piotr.spring_boot_docker.service.CalculationsService;
import nowicki.piotr.spring_boot_docker.service.ExpenseService;
import nowicki.piotr.spring_boot_docker.service.GroupService;
import nowicki.piotr.spring_boot_docker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpensePageController {

    private final ExpenseService expenseService;
    private final UserService userService;
    private final GroupService groupService;
    private final CalculationsService calculationsService;
    private final ServletContext servletContext;
    @GetMapping("/addExpense")
    public String showAddExpenseForm(@RequestParam("selectedGroup") String groupId, Model model){
        GroupDto selectedGroup = groupService.findById(groupId);
        model.addAttribute("group", selectedGroup);

        model.addAttribute("expense", new Expense());

        List<UserResponseDto> userDtoList = userService.findByGroupId(groupId);

        model.addAttribute("users",userDtoList);

        return "add-expense-page";
    }
    @PostMapping("/addExpenses")
    public String addExpense(@ModelAttribute("expense") ExpenseDto expenseDto, @RequestParam("userId") String userId, @RequestParam(required = false) List<String> userIds, @RequestParam("groupId") String groupId, @RequestParam("photoFile") MultipartFile photoFile, Model model) throws IOException {

        GroupDto selectedGroup = groupService.findById(groupId);
        model.addAttribute("selectedGroup", selectedGroup);
        model.addAttribute("group", selectedGroup);

        model.addAttribute("expense", new Expense());

        List<UserResponseDto> userDtoList = userService.findByGroupId(groupId);
        model.addAttribute("users",userDtoList);

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

        if (expenseDto.name().isEmpty()||expenseDto.amount() == null||userId.isEmpty()){
            model.addAttribute("message", "Name, amount and paid by can not be empty");
            return "add-expense-page";
        }
        if (groupId != null && !userIds.isEmpty()) {
            expenseService.saveExpense(expenseDto, userId, groupId, userIds, "/images/" + photoFile.getOriginalFilename());
        }
        else if (groupId != null){
            expenseService.saveExpense(expenseDto, userId, groupId, "/images/" + photoFile.getOriginalFilename());
            }
        else
            return "add-expense-page";
        return "redirect:/groups?selectedGroup=" + groupId;
    }

    @GetMapping("/expensesOverview")
    public String showExpenseOverview(@RequestParam("selectedGroup") String groupId, Model model){
        GroupDto selectedGroup = groupService.findById(groupId);
        model.addAttribute("group", selectedGroup);
        List<ExpenseDto> expenses = expenseService.findAllByGroupId(groupId);

        model.addAttribute("expenses",expenses);
        List<UserResponseDto> userDtoList = userService.findByGroupId(groupId);

        model.addAttribute("users",userDtoList);

        List<Balance> balances = calculationsService.getExpensesBalances(groupId);
        model.addAttribute("balances", balances);

        return "expenses-overview-page";
    }

}
