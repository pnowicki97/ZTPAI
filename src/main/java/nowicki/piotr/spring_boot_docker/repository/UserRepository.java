package nowicki.piotr.spring_boot_docker.repository;

import nowicki.piotr.spring_boot_docker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);

    List<User> findByGroups_Id(String groupId);

}
