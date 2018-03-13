package cn.nonocast.api.vm;

import cn.nonocast.model.ModelBase;
import cn.nonocast.model.User;
import com.fasterxml.jackson.annotation.JsonView;

public class UserSummary {
	@JsonView(ModelBase.API.class)
	private Long id;
	@JsonView(ModelBase.API.class)
	private String email;
	@JsonView(ModelBase.API.class)
	private String name;
	@JsonView(ModelBase.API.class)
	private String avatar;
	@JsonView(ModelBase.API.class)
	private String location;
	@JsonView(ModelBase.API.class)
	private String mobile;
	@JsonView(ModelBase.API.class)
	private String wechatid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWechatid() {
		return wechatid;
	}

	public void setWechatid(String wechatid) {
		this.wechatid = wechatid;
	}

	public UserSummary() {

	}

	public UserSummary(User user) {
		this.id = user.getId();
		this.avatar = user.getAvatar();
		this.name = user.getName();
		this.email = user.getEmail();
		this.location = user.getLocation();
		this.wechatid = user.getWechatid();
		this.mobile = user.getMobile();
	}
}
