package com.xigua.service;

import java.util.List;

import com.xigua.model.Port;
import com.xigua.model.SpawnNewVirDevice;
import com.xigua.model.virtualDevice;
import com.xigua.model.virtualDeviceInfo;

public interface virtualDeviceService {
    //获取oltID下的切片
    public List<virtualDevice> getVirtualDeviceList(String oltId); 
    //获取vnd上的端信息
    virtualDeviceInfo getInfo(String oltId,String vndName);
    //删除oltId下切片
    void deleteVirtual(String oltId,String vndName);
    //获取可用PORT资源列表
    List<Port> getInterfaceList(String oltId);
    //在OLTID下新增新的切片
    void spawnNewSlice (String oltId,SpawnNewVirDevice info);
    //删除虚拟切片下的某个PON口资源
    void deletePONinVirtual(String oltId,String vndName,String interfaceName);
    //改变状态
    void changeStatus(String oltId,String vndName,String statusChange);
    //为VND添加端口资源
    void addResource(String oltId,String vndName,String[] PortName);
}
