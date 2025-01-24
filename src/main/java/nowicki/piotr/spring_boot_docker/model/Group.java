package nowicki.piotr.spring_boot_docker.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    @Column
    public String name;
    @Column
    public String photoUrl;
    @ManyToMany(mappedBy = "groups")
    private Set<User> users = new HashSet<>();
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<Expense > expenses = new HashSet<>();
}
