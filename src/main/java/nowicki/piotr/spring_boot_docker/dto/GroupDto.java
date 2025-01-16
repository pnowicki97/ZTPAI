package nowicki.piotr.spring_boot_docker.dto;

import jakarta.validation.constraints.NotEmpty;

public record GroupDto(@NotEmpty(message = "Name should not be empty") String name) {
}
