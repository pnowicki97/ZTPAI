package nowicki.piotr.spring_boot_docker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
public record UserDto(@NotEmpty(message = "Name should not be empty") String name,
                      @NotEmpty(message = "Password should not be empty") String password,
                      String email,
                      String photoUrl) {
}
