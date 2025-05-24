package nowicki.piotr.spring_boot_docker.dto;

import jakarta.validation.constraints.NotEmpty;
import nowicki.piotr.spring_boot_docker.model.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record DutyDto(@NotEmpty(message = "Name should not be empty") String name,
                        Double value,
                        User user,
                        String photoUrl,
                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
                        LocalDateTime beginDate,
                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
                        LocalDateTime endDate) {
}
