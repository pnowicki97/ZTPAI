package nowicki.piotr.spring_boot_docker.repository;

import nowicki.piotr.spring_boot_docker.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
    List<Event> findByUser_Id(String userId);
    List<Event> findByGroup_Id(String groupId);
}
