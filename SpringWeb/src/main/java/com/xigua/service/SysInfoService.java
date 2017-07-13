package com.xigua.service;

import com.xigua.model.SpawnInfo;
import com.xigua.model.SysConfig;

public interface SysInfoService {
    SysConfig getConfig(String device);
    void SpawnNewDevice(SpawnInfo info);
}
