package nowicki.piotr.spring_boot_docker.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nowicki.piotr.spring_boot_docker.dto.*;
import nowicki.piotr.spring_boot_docker.mapper.UserMapper;
import nowicki.piotr.spring_boot_docker.model.Balance;
import nowicki.piotr.spring_boot_docker.model.Group;
import nowicki.piotr.spring_boot_docker.model.User;
import nowicki.piotr.spring_boot_docker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CalculationsService {

    private final ExpenseService expenseService;
    private final GroupService groupService;
    private final DutyService dutyService;
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public CalculationsService(ExpenseService expenseService, GroupService groupService, DutyService dutyService, UserService userService, UserMapper userMapper) {
        this.expenseService = expenseService;
        this.groupService = groupService;
        this.dutyService = dutyService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public Double getExpensesSum(String groupId){
        Double suma = 0.;
        List<ExpenseDto> expenses = expenseService.findAllByGroupId(groupId);
        for(ExpenseDto expense:expenses)
            suma += expense.amount();
        return suma;
    }

    public List<Balance> getExpensesBalances(String groupId){
        List<Balance> balances = new ArrayList<>();

        List<UserResponseDto> users = userService.findByGroupId(groupId);

        List<ExpenseDto> expenses = expenseService.findAllByGroupId(groupId);

        for (UserResponseDto user: users){
            balances.add(new Balance(user,0.));
        }

        for (ExpenseDto expense: expenses){
            double mean = expense.amount() / expense.users().size();
            Balance bal = balances.stream().filter(balance -> balance.getLender().equals(userMapper.toUserResponseDto(expense.paidBy()))).findFirst().get();
            bal.setAmount(bal.getAmount() + expense.amount());

            for (User user: expense.users()){
                Balance bala = balances.stream().filter(balance -> balance.getLender().equals(userMapper.toUserResponseDto(user))).findFirst().get();
                bala.setAmount(bala.getAmount() - mean);
            }
        }
        return simplifyExpensesBalances(balances);
    }
    public List<Balance> simplifyExpensesBalances(List<Balance> balances){

        List<Balance> simplifiedBalances = new ArrayList<>();
        List<UserResponseDto> lenders = new ArrayList<>();
        List<UserResponseDto> debtors = new ArrayList<>();

        for (Balance balance: balances){
            if(balance.getAmount() > 0)
                lenders.add(balance.getLender());
            else if (balance.getAmount() < 0) {
                debtors.add(balance.getLender());
            }
        }

        System.out.println("lenders: " + lenders);
        System.out.println("debtors: " + debtors);

        for (UserResponseDto lender: lenders){
            for (UserResponseDto debtor: debtors){
                double amount = Math.min(balances.stream().filter(balance -> balance.getLender().equals(lender)).findFirst().get().getAmount(), Math.abs(balances.stream().filter(balance -> balance.getLender().equals(debtor)).findFirst().get().getAmount()));
                if(amount > 0){
                    simplifiedBalances.add(new Balance(lender, amount, debtor));
                    Balance bal = balances.stream().filter(balance -> balance.getLender().equals(lender)).findFirst().get();
                    bal.setAmount(bal.getAmount() - amount);
                    bal = balances.stream().filter(balance -> balance.getLender().equals(debtor)).findFirst().get();
                    bal.setAmount(bal.getAmount() + amount);
                }
            }
        }
        System.out.println("simplifiedBalances: " + simplifiedBalances);

        for(Balance balance: simplifiedBalances){
            System.out.println(balance.getLender() + " " + balance.getAmount() + " " + balance.getDebtor());
        }
        return simplifiedBalances;
    }

    public List<Balance> getDutiesBalances(String groupId){
        List<Balance> balances = new ArrayList<>();

        List<UserResponseDto> users = userService.findByGroupId(groupId);

        List<DutyDto> duties = dutyService.findAllByGroupId(groupId);

        for (UserResponseDto user: users){
            balances.add(new Balance(user,0.));
        }

        for (DutyDto duty: duties){
            Balance bal = balances.stream().filter(balance -> balance.getLender().equals(userMapper.toUserResponseDto(duty.user()))).findFirst().get();
            bal.setAmount(bal.getAmount() + duty.value());
        }
        return balances;
    }
}
