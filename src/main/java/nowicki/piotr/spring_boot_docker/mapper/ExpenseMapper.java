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
    public Expense toExpense(ExpenseDto dto){
        Expense expense = new Expense();
        expense.name = dto.name();
        expense.amount = dto.amount();
        expense.setUser(dto.paidBy());
        expense.setUsers(dto.users());
        return expense;
    }

    public ExpenseDto toExpenseDto(Expense expense){
        return new ExpenseDto(expense.name, expense.amount, expense.getUser(), expense.getUsers());
    }
}
