package nowicki.piotr.spring_boot_docker.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    private String name;
    private String password;
//    private String email;
}
