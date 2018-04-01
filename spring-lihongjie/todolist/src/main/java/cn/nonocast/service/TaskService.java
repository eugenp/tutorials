package cn.nonocast.service;

import cn.nonocast.api.vm.TaskSummary;
import cn.nonocast.api.vm.UserSummary;
import cn.nonocast.model.Task;
import cn.nonocast.model.User;
import cn.nonocast.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findByUser(User user) {
        List<Task> result;
        result = taskRepository.findByBelongsTo(user);
        return result;
    }

	@CacheEvict(cacheNames="tasks", key="#task.belongsTo.email")
	public Task save(Task task) {
		return taskRepository.save(task);
	}

	@CacheEvict(cacheNames="tasks", key="#task.belongsTo.email")
	public void delete(Task task) {
		taskRepository.delete(task);
	}

	public Task findOne(Long id) {
		return taskRepository.findOne(id);
	}

	public Page<Task> findByKeyword(Long id, Pageable pageable) {
		return taskRepository.findByKeyword(id, pageable);
	}

	public Page<Task> findByKeyword(String q, Pageable pageable) {
		return taskRepository.findByKeyword(q, pageable);
	}

	public Page<Task> findByUser(User user, Pageable pageable) {
		return taskRepository.findByBelongsTo(user, pageable);
	}

	public Page<Task> findAll(Pageable pageable) {
		return taskRepository.findAll(pageable);
	}

	public List<Task> findByIds(List<Long> ids) {
		return taskRepository.findByIds(ids);
	}

	@CacheEvict(cacheNames = "tasks", allEntries = true)
	public void delete(List<Task> tasks) {
		taskRepository.delete(tasks);
	}

	@Cacheable(cacheNames="tasks", key="#user.email")
	public TaskSummary findSummary(User user) {
		TaskSummary summary = new TaskSummary();
		summary.setUser(new UserSummary(user));
		summary.setActive(taskRepository.findByBelongsToAndStatusOrderByCreatedAtDesc(user, Task.TaskStatus.OPEN));

		Page<Task> completed = taskRepository.findByBelongsToAndStatusOrderByCreatedAtDesc(user, Task.TaskStatus.CLOSE, new PageRequest(0, 10));
		summary.setCompleted(completed.getContent());
		summary.setCompletedCount(completed.getTotalElements());
		return summary;
	}

	public List<Task> findCompleted(User user, Pageable pageable) {
		return taskRepository.findByBelongsToAndStatusOrderByCreatedAtDesc(user, Task.TaskStatus.CLOSE, pageable).getContent();
	}
}
