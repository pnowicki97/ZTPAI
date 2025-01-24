package nowicki.piotr.spring_boot_docker.controller;

import jakarta.validation.Valid;
import nowicki.piotr.spring_boot_docker.dto.ExpenseDto;
import nowicki.piotr.spring_boot_docker.dto.GroupDto;
import nowicki.piotr.spring_boot_docker.model.Expense;
import nowicki.piotr.spring_boot_docker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public List<ExpenseDto> findAllExpenses(){
        return expenseService.findAllExpenses();
    }
    @PostMapping
    public ExpenseDto saveExpense(@RequestBody @Valid ExpenseDto dto) {
        return expenseService.saveExpense(dto);
    }
    @GetMapping("/{expense-id}")
    public ExpenseDto findExpenseById(@PathVariable("expense-id") String id){
        return expenseService.findById(id);
    }
    @DeleteMapping("/{expense-id}")
    public void deleteExpense(@PathVariable("expense-id") String id){
        expenseService.deleteById(id);
    }

}
