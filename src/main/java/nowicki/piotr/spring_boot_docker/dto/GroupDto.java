package nowicki.piotr.spring_boot_docker.dto;

import jakarta.validation.constraints.NotEmpty;
import nowicki.piotr.spring_boot_docker.model.User;

import java.util.List;
import java.util.Set;

public record GroupDto(@NotEmpty(message = "Name should not be empty") String name, String photoUrl, String id) {
}
