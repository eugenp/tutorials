package cn.nonocast.admin.form;

import javax.validation.constraints.Size;

public class LoginForm {
    @Size(min=5, max=30, message="请输入正确的邮箱地址")
    private String email;

    @Size(min=6, max=30, message="密码不少于6位")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
