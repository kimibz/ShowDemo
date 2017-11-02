package com.xigua.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xigua.model.PortHistoryModel;

public interface PortHistoryDao {
    //插入PORT历史数据
    void insertPortHistory(PortHistoryModel model);
    //获取历史数据
    List<PortHistoryModel> getPortHistory(@Param("oltId") String oltId , @Param("vndName")String vndName);
}
