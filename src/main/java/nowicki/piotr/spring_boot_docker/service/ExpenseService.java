package nowicki.piotr.spring_boot_docker.service;

import nowicki.piotr.spring_boot_docker.dto.ExpenseDto;
import nowicki.piotr.spring_boot_docker.dto.GroupDto;
import nowicki.piotr.spring_boot_docker.mapper.ExpenseMapper;
import nowicki.piotr.spring_boot_docker.model.Expense;
import nowicki.piotr.spring_boot_docker.model.Group;
import nowicki.piotr.spring_boot_docker.model.User;
import nowicki.piotr.spring_boot_docker.repository.ExpenseRepository;
import nowicki.piotr.spring_boot_docker.repository.GroupRepository;
import nowicki.piotr.spring_boot_docker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final ExpenseMapper expenseMapper;
    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository, GroupRepository groupRepository, ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.expenseMapper = expenseMapper;
    }
    public ExpenseDto saveExpense(ExpenseDto dto) {
        var expense = expenseMapper.toExpense(dto);
        expenseRepository.save(expense);
        return dto;
    }
    public ExpenseDto saveExpense(ExpenseDto dto, String userId, String groupId) {
        // Fetch the User and Group
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        var expense = expenseMapper.toExpense(dto);
        expense.setUser(user);
        expense.setGroup(group);
        expenseRepository.save(expense);

        return dto;
    }

    public List<ExpenseDto> findAllExpenses(){return expenseRepository.findAll().stream().map(expenseMapper::toExpenseDto).collect(Collectors.toList());}
    public List<ExpenseDto> findAllByUserId(String userId) {
        return expenseRepository.findByUser_Id(userId).stream().map(expenseMapper::toExpenseDto).collect(Collectors.toList());
    }

    public List<ExpenseDto> findAllByGroupId(String groupId) {
        return expenseRepository.findByGroup_Id(groupId).stream().map(expenseMapper::toExpenseDto).collect(Collectors.toList());
    }

    public ExpenseDto findById(@PathVariable("expense-id") String id){
        return expenseRepository.findById(id).map(expenseMapper::toExpenseDto).orElse(null);
    }
    public void deleteById(@PathVariable("expense-id") String id){
        expenseRepository.deleteById(id);
    }
}