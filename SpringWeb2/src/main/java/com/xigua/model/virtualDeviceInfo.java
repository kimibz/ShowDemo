package com.xigua.model;

import java.io.Serializable;
import java.util.List;

public class virtualDeviceInfo implements Serializable{

    /**
     * XIGUA
     */
    private static final long serialVersionUID = -138092182054902L;
    
    private String vndName;
    private String description;
    private List<String> deploy_mpu;
    private List<PortInfo> assigned_interface ;
    private String status;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<String> getDeploy_mpu() {
        return deploy_mpu;
    }
    public void setDeploy_mpu(List<String> deploy_mpu) {
        this.deploy_mpu = deploy_mpu;
    }
    public List<PortInfo> getAssigned_interface() {
        return assigned_interface;
    }
    public void setAssigned_interface(List<PortInfo> assigned_interface) {
        this.assigned_interface = assigned_interface;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getVndName() {
        return vndName;
    }
    public void setVndName(String vndName) {
        this.vndName = vndName;
    }
    
 }
