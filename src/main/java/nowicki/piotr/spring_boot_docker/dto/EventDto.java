package nowicki.piotr.spring_boot_docker.dto;

import jakarta.validation.constraints.NotEmpty;
import nowicki.piotr.spring_boot_docker.model.Group;
import nowicki.piotr.spring_boot_docker.model.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

public record EventDto(@NotEmpty(message = "Name should not be empty") String title,
                       String photoUrl,
                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
                       LocalDateTime start,
                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
                       LocalDateTime end) {
}
