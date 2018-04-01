package cn.nonocast.repository;

import cn.nonocast.model.Task;
import cn.nonocast.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT p FROM Task p WHERE p.id in (:selected)")
    List<Task> findByIds(@Param("selected") List<Long> selected);
    List<Task> findByBelongsTo(User user);
    Page<Task> findByBelongsTo(User user, Pageable pageable);

    @Query(value = "SELECT p FROM Task p WHERE LOWER(p.content) LIKE LOWER(concat('%',:q,'%'))")
    Page<Task> findByKeyword(@Param("q") String q, Pageable pageable);

    @Query(value = "SELECT p FROM Task p WHERE p.id=:q")
    Page<Task> findByKeyword(@Param("q") Long q, Pageable pageable);


	List<Task> findByBelongsToAndStatusOrderByCreatedAtDesc(User user, Task.TaskStatus status);
	Page<Task> findByBelongsToAndStatusOrderByCreatedAtDesc(User user, Task.TaskStatus status, Pageable pageable);
}
