package com.xigua.service;

import java.util.List;

import com.xigua.model.ManageVirtualUsr;
import com.xigua.model.usrOLTManageModel;

public interface UserMangeService {
    
    //获取该户下所有设备状态信息
    List<ManageVirtualUsr> getAllDevice(String username);
    //获取改VND的PORT信息和SYS信息
    usrOLTManageModel getMangeInfo(String oltId);
}
