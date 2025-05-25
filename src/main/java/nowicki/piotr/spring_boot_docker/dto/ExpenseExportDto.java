package nowicki.piotr.spring_boot_docker.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nowicki.piotr.spring_boot_docker.model.User;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseExportDto {
    private String name;
    private Double amount;
    private String paidById;
    private String usersIds;
    private String photoUrl;
}
