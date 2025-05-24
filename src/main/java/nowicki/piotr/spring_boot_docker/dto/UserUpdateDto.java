package nowicki.piotr.spring_boot_docker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserUpdateDto {

    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    public String currentPassword;
    public String newPassword;

}