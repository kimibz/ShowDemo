package com.xigua.service;

import java.util.List;

import com.xigua.model.Topo.TopoInfo;

public interface TopoService {
    //获取Topo信息
    List<TopoInfo> getInfo(String username);
}
