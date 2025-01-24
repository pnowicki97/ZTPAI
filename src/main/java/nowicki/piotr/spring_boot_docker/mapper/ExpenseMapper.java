package nowicki.piotr.spring_boot_docker.mapper;

import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.dto.ExpenseDto;
import nowicki.piotr.spring_boot_docker.dto.GroupDto;
import nowicki.piotr.spring_boot_docker.model.Expense;
import nowicki.piotr.spring_boot_docker.model.Group;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseMapper {
    private UserMapper userMapper;
    public Expense toExpense(ExpenseDto dto){
        var expense = new Expense();
        expense.name = dto.name();
        expense.amount = dto.amount();
        expense.setUser(dto.paidBy());
        return expense;
    }

    public ExpenseDto toExpenseDto(Expense expense){
        return new ExpenseDto(expense.name, expense.amount, expense.getUser());
    }
}
