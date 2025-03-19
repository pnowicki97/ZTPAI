package nowicki.piotr.spring_boot_docker.repository;

import nowicki.piotr.spring_boot_docker.model.Duty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DutyRepository extends JpaRepository<Duty, String> {
    List<Duty> findByUser_Id(String userId);
    List<Duty> findByGroup_Id(String groupId);
}
