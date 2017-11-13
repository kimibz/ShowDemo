package com.xigua.model.Topo;

import java.io.Serializable;

public class Onu implements Serializable{

    /**
     * XIGUA
     */
    private static final long serialVersionUID = 1453007632491134054L;

    private int ifIndex ;
    
    private int zxAnPonOnuIndex;

    private String zxAnGponSrvOnuPhaseStatus;

    public int getIfIndex() {
        return ifIndex;
    }

    public void setIfIndex(int ifIndex) {
        this.ifIndex = ifIndex;
    }

    public int getZxAnPonOnuIndex() {
        return zxAnPonOnuIndex;
    }

    public void setZxAnPonOnuIndex(int zxAnPonOnuIndex) {
        this.zxAnPonOnuIndex = zxAnPonOnuIndex;
    }

    public String getZxAnGponSrvOnuPhaseStatus() {
        return zxAnGponSrvOnuPhaseStatus;
    }

    public void setZxAnGponSrvOnuPhaseStatus(String zxAnGponSrvOnuPhaseStatus) {
        this.zxAnGponSrvOnuPhaseStatus = zxAnGponSrvOnuPhaseStatus;
    }
    
    
    
}
