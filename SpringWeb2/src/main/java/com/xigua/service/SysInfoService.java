package com.xigua.service;

import java.util.List;

import com.xigua.model.Port;
import com.xigua.model.SpawnInfo;
import com.xigua.model.SysConfig;

public interface SysInfoService {
    //获取config
    SysConfig getConfig(String device);
    //新增一个新的设备
    void SpawnNewDevice(SpawnInfo info);
    //获取设备端口的信息
    List <Port> getPortList(String oltId);
    //获取单个端口的信息
    Port getPort(String oltId,String shelf,String slot ,String subslot, String portno);
    
}
