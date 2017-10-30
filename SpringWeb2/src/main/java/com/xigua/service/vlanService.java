package com.xigua.service;

import com.xigua.model.rateStats;
import com.xigua.model.vlanEdit;

public interface vlanService {
    //修改上联口VLAN(添加)
    void editVlan(String vndName,String interfaceName,String vlan);
    //获取VLAN INFO
    vlanEdit getVlan(String vndName,String interfaceName);
    //获取上联口性能统计数据
    rateStats getStats(String vndName,String interfaceName);
    //存入性能统计数据
    void saveStats(String vndName);
}
