package com.xigua.model;

import java.io.Serializable;

public class OnuSpawnModel implements Serializable{

    /**
     * XIGUA
     */
    private static final long serialVersionUID = -5482196713379452396L;
    
    private String onuId;
    
    private String OnuType;
    
    private String OnuMac;
    
    private String ifIndex;

    public String getOnuId() {
        return onuId;
    }

    public void setOnuId(String onuId) {
        this.onuId = onuId;
    }

    public String getOnuType() {
        return OnuType;
    }

    public void setOnuType(String onuType) {
        OnuType = onuType;
    }

    public String getOnuMac() {
        return OnuMac;
    }

    public void setOnuMac(String onuMac) {
        OnuMac = onuMac;
    }

    public String getIfIndex() {
        return ifIndex;
    }

    public void setIfIndex(String ifIndex) {
        this.ifIndex = ifIndex;
    }
    
}
