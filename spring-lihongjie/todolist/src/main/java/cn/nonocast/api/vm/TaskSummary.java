package cn.nonocast.api.vm;

import cn.nonocast.model.ModelBase;
import cn.nonocast.model.Task;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

public class TaskSummary {
	@JsonView(ModelBase.API.class)
	private UserSummary user;

	@JsonView(ModelBase.API.class)
	private List<Task> active;

	@JsonView(ModelBase.API.class)
	private List<Task> completed;

	@JsonView(ModelBase.API.class)
	private long completedCount;

	public UserSummary getUser() {
		return user;
	}

	public void setUser(UserSummary user) {
		this.user = user;
	}

	public List<Task> getActive() {
		return active;
	}

	public void setActive(List<Task> active) {
		this.active = active;
	}

	public List<Task> getCompleted() {
		return completed;
	}

	public void setCompleted(List<Task> completed) {
		this.completed = completed;
	}

	public long getCompletedCount() {
		return completedCount;
	}

	public void setCompletedCount(long completedCount) {
		this.completedCount = completedCount;
	}
}
