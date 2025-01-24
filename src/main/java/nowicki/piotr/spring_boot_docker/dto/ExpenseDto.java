package nowicki.piotr.spring_boot_docker.dto;

import jakarta.validation.constraints.NotEmpty;
import nowicki.piotr.spring_boot_docker.model.User;

public record ExpenseDto(@NotEmpty(message = "Name should not be empty") String name, Double amount, User paidBy) {
}
