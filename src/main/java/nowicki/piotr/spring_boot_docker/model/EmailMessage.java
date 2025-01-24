package nowicki.piotr.spring_boot_docker.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class EmailMessage {
    private String recipient;
    private String subject;
    private String body;
}