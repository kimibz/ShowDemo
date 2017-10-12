package com.xigua.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xigua.model.ManageVirtualUsr;
import com.xigua.model.virtualDevice;

public interface ManageVirtualUsrDao {
    //获取虚拟切片的所属用户
    String getVirtualUsr(@Param("oltId") String oltId , @Param("virtualName")String virtualName);
    //获取用户所含的虚拟切片
    List<ManageVirtualUsr> getVirtualList(String username);
    //将虚拟切片分配给用户
    void SetVirtualToUser(@Param("oltId")String oltId,@Param("virtualName")String virtualName,@Param("user")String user);
}
