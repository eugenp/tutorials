package cn.nonocast.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
public class User extends ModelBase implements UserDetails {
    public interface WithPasswordView extends API {};

    @NotNull
    @JsonView(API.class)
    private String email;
    @JsonView(API.class)
    private String name;
	@JsonView(API.class)
    private String mobile;
	@JsonView(API.class)
    private String location;
    @JsonView(WithPasswordView.class)
    private String password;
	@JsonView(API.class)
    private String wechatid;
	@JsonView(API.class)
    private String avatar;
	@JsonView(API.class)
    private Boolean enabled = true;

	@JsonIgnore
    @OneToMany(mappedBy = "belongsTo", orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();

    @Enumerated(EnumType.ORDINAL)
    @JsonView(API.class)
    private Role role = Role.USER;

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

	@JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> result = new ArrayList<>();
        result.add(new SimpleGrantedAuthority("ROLE_USER"));
        if(Role.ADMIN.equals(this.role)) result.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return result;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

	@JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

	@JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

	@JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public User() {
    }

    public User(String email, String name, String password) {
        this();
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public enum Role {
        USER("普通用户"),
        ADMIN("管理员");

        private String name;

        Role(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
