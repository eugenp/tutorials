package com.spring4all.entity;

import lombok.Data;

@Data
public class UserEntity {

    private Long id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 权限
     */
    private String roles;
}
