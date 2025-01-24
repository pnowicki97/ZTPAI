package nowicki.piotr.spring_boot_docker.repository;

import nowicki.piotr.spring_boot_docker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {
    List<Expense> findByUser_Id(String userId);
    List<Expense> findByGroup_Id(String groupId);
}
