package com.xigua.service;

import java.util.List;

import com.xigua.model.OnuSpawnModel;
import com.xigua.model.Topo.Onu;

public interface UserOnuService {
    
    //添加onu
    void addOnu(String vndName , OnuSpawnModel OnuModel);
    //查看端口下ONU
    List<Onu> getOnu(String vndName,String interfaceName);
    //删除端口下ONU
    void deleteOnu(String vndName, String interfaceName);
}
