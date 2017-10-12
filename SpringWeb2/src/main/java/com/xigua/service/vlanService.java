package com.xigua.service;

import com.xigua.model.vlanEdit;

public interface vlanService {
    //修改上联口VLAN
    void editVlan(vlanEdit edit,String vndName);
}
