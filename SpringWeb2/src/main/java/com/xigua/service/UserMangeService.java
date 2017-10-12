package com.xigua.service;

import java.util.List;

import com.xigua.model.ManageVirtualUsr;

public interface UserMangeService {
    
    //获取该户下所有设备信息
    public List<ManageVirtualUsr> getAllDevice(String username);
}
