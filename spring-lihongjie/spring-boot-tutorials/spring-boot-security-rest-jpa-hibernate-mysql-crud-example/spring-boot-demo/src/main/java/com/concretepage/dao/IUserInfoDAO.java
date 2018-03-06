package com.concretepage.dao;
import com.concretepage.entity.UserInfo;
public interface IUserInfoDAO {
	UserInfo getActiveUser(String userName);
}