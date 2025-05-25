package nowicki.piotr.spring_boot_docker.mapper;

import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.dto.*;
import nowicki.piotr.spring_boot_docker.model.Expense;
import nowicki.piotr.spring_boot_docker.model.Group;
import nowicki.piotr.spring_boot_docker.model.User;
import nowicki.piotr.spring_boot_docker.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseMapper {

    private final UserMapper userMapper;
    private final UserService userService;
    public Expense toExpense(ExpenseDto dto){
        Expense expense = new Expense();
        expense.name = dto.name();
        expense.amount = dto.amount();
        expense.setUser(dto.paidBy());
        expense.setUsers(dto.users());
        expense.setPhoto_url(dto.photoUrl());
        return expense;
    }

    public ExpenseDto toExpenseDto(Expense expense){
        return new ExpenseDto(expense.name, expense.amount, expense.getUser(), expense.getUsers(), expense.getPhoto_url());
    }

    public ExpenseDto toExpenseDto(ExpenseExportDto expenseExportDto){
        Set<User> users = new HashSet<>();
        for (String userId : expenseExportDto.getUsersIds().split(","))
            users.add(userMapper.toUser(userService.findById(userId)));
        return new ExpenseDto(expenseExportDto.getName(), expenseExportDto.getAmount(), userMapper.toUser(userService.findById(expenseExportDto.getPaidById())), users, expenseExportDto.getPhotoUrl());
    }

    public ExpenseExportDto toExpenseExportDto(ExpenseDto expenseDto){
        ExpenseExportDto expenseExportDto = new ExpenseExportDto();
        expenseExportDto.setName(expenseDto.name());
        expenseExportDto.setPaidById(expenseDto.paidBy().getId());
        expenseExportDto.setAmount(expenseDto.amount());
        expenseExportDto.setPhotoUrl(expenseDto.photoUrl());
        if (!expenseDto.users().isEmpty()) {
            expenseExportDto.setUsersIds(expenseDto.users().stream()
                    .map(User::getId)
                    .collect(Collectors.joining(",")));
        } else {
            expenseExportDto.setUsersIds("");
        }
        return expenseExportDto;
    }
}
