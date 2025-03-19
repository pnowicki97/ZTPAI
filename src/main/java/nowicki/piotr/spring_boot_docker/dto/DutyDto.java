package nowicki.piotr.spring_boot_docker.dto;

import jakarta.validation.constraints.NotEmpty;
import nowicki.piotr.spring_boot_docker.model.User;

public record DutyDto(@NotEmpty(message = "Name should not be empty") String name, Double value, User user) {
}
