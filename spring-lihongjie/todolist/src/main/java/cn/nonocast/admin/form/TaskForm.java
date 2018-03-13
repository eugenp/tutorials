package cn.nonocast.admin.form;

import cn.nonocast.base.FormBase;
import cn.nonocast.model.Task;

import javax.validation.constraints.Size;

public class TaskForm extends FormBase {
	private Long id;

    @Size(min=1, max=200, message="内容最多200字")
    private String content;

    private Task.TaskStatus status = Task.TaskStatus.OPEN;
    private Task.TaskCategory category = Task.TaskCategory.DAILY;
    private Task.TaskPriority priority = Task.TaskPriority.NORMAL;

//    @NotNull
    @Size(min=1, message="请输入邮箱地址")
    private String belongsTo;

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

    public Task.TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(Task.TaskPriority priority) {
        this.priority = priority;
    }

    public String getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
    }

    public void pull(Task task) {
        this.op = "edit";
	    this.id = task.getId();
        this.content = task.getContent();
        this.category = task.getCategory();
        this.status = task.getStatus();
        this.priority = task.getPriority();
        if(task.getBelongsTo() != null) {
            this.belongsTo = task.getBelongsTo().getEmail();
        }
    }

    public Task push(Task task) {
        task.setCategory(this.category);
        task.setStatus(this.status);
        task.setContent(this.content);
        task.setPriority(this.priority);
        return task;
    }
}
