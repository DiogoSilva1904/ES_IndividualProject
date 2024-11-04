package es._7.todolist.repositories;

import es._7.todolist.models.Task;
import es._7.todolist.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByPriority(String priority);

    List<Task> findByUserAndPriority(AppUser user, String priority);
}
