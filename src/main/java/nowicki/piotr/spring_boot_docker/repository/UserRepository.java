package nowicki.piotr.spring_boot_docker.repository;

import nowicki.piotr.spring_boot_docker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByNameLike(String name);
}
