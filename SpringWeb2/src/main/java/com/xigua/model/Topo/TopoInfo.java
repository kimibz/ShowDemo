package com.xigua.model.Topo;

import java.io.Serializable;
import java.util.List;

public class TopoInfo implements Serializable{

    /**
     * XIGUA 单个设备的拓扑信息 
     */
    private static final long serialVersionUID = -3785217107153569724L;
    
    //oltId
    private String name;
    
    private List<VOLT> volt;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<VOLT> getVolt() {
        return volt;
    }
    public void setVolt(List<VOLT> volt) {
        this.volt = volt;
    }
    
}
