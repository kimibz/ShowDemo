package com.xigua.model;

import java.io.Serializable;

public class vlanEdit implements Serializable{

    /**
     * XIGUA(vlan-dev-c600:ifVlan)
     */
    private static final long serialVersionUID = -6397019583398112572L;
    
    private String if_index;
    
    private String if_sub_index;
    
    private String mode;
    
    private String vlan_info;

    public String getIf_index() {
        return if_index;
    }

    public void setIf_index(String if_index) {
        this.if_index = if_index;
    }

    public String getIf_sub_index() {
        return if_sub_index;
    }

    public void setIf_sub_index(String if_sub_index) {
        this.if_sub_index = if_sub_index;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getVlan_info() {
        return vlan_info;
    }

    public void setVlan_info(String vlan_info) {
        this.vlan_info = vlan_info;
    }
    
}
