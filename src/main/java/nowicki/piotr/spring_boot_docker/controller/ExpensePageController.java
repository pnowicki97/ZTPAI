package nowicki.piotr.spring_boot_docker.controller;

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

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpensePageController {

    private final ExpenseService expenseService;
    private final UserService userService;
    private final GroupService groupService;
    private final CalculationsService calculationsService;
    @GetMapping("/addExpense")
    public String showAddExpenseForm(@RequestParam("selectedGroup") String groupId, Model model){
        GroupDto selectedGroup = groupService.findById(groupId);
        model.addAttribute("group", selectedGroup);

        model.addAttribute("expense", new Expense());

        List<UserResponseDto> userDtoList = userService.findByGroupId(groupId);

        model.addAttribute("users",userDtoList);

        return "desktop-add-expense";
    }
    @PostMapping("/addExpenses")
    public String addExpense(@ModelAttribute("expense") ExpenseDto expenseDto, @RequestParam("userId") String userId, @RequestParam(required = false) List<String> userIds, @RequestParam("groupId") String groupId, Model model){

        GroupDto selectedGroup = groupService.findById(groupId);
        model.addAttribute("selectedGroup", selectedGroup);
        model.addAttribute("group", selectedGroup);

        model.addAttribute("expense", new Expense());

        List<UserResponseDto> userDtoList = userService.findByGroupId(groupId);
        model.addAttribute("users",userDtoList);

        if (expenseDto.name().isEmpty()||expenseDto.amount() == null||userId.isEmpty()){
            model.addAttribute("message", "Name, amount and paid by can not be empty");
            return "desktop-add-expense";
        }
        if (groupId != null && !userIds.isEmpty()) {
            expenseService.saveExpense(expenseDto, userId, groupId, userIds);
        }
        else if (groupId != null){
            expenseService.saveExpense(expenseDto, userId, groupId);
            }
        else
            return "desktop-add-expense";
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
