package com.xigua.dao;


import java.util.List;

import com.xigua.model.User;
public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	User findUserByLoginName(String username);
	
	User selectByCode(String code);
	
	//获取role=?的用户
	List<String> getAllUser(Integer role);
}