package cn.nonocast.api.form;

import cn.nonocast.base.FormBase;
import cn.nonocast.model.Task;

import javax.validation.constraints.Size;

public class TaskForm extends FormBase {
	private Long id = 0L;

	@Size(min=1, max=200, message="内容最多200字")
	private String content;

	private Task.TaskStatus status = Task.TaskStatus.OPEN;

	private Task.TaskCategory category = Task.TaskCategory.DAILY;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Task.TaskStatus getStatus() {
		return status;
	}

	public void setStatus(Task.TaskStatus status) {
		this.status = status;
	}

	public Task.TaskCategory getCategory() {
		return category;
	}

	public void setCategory(Task.TaskCategory category) {
		this.category = category;
	}

	public void pull(Task task) {
		this.id = task.getId();
		this.content = task.getContent();
		this.status = task.getStatus();
		this.category = task.getCategory();
	}

	public Task push(Task task) {
		task.setContent(this.content);
		task.setStatus(this.status);
		task.setCategory(this.category);
		return task;
	}
}
