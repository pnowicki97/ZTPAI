package nowicki.piotr.spring_boot_docker.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column
    public String name;
    @Column
    public String password;
    @Column(unique = true)
    public String email;
    @Column
    public String photo_url;
}
