package com.xigua.service;

import java.text.ParseException;
import java.util.List;

import com.xigua.exception.ServiceException;
import com.xigua.model.User;

public interface UserService {
	public User getUserById(int id);

	public User findUserByLoginName(String username);
	
	public void insertNewUser (User user);
	
	public void processActivate(String email , String validateCode) throws ParseException, ServiceException;

	//获取role=?的用户
	public List<String> getAllUser(int role);
}
