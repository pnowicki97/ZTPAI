package nowicki.piotr.spring_boot_docker.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nowicki.piotr.spring_boot_docker.model.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DutyExportDto {
    private String name;
    private Double value;
    private String userId;
    private String photoUrl;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;

}
