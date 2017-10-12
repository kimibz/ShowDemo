package com.xigua.model;

import java.io.Serializable;
import java.util.List;

public class SpawnNewVirDevice implements Serializable{

    /**
     * XIGUA
     */
    private static final long serialVersionUID = 1403425906120861056L;
    private String vnd_name;
    private String vnd_status;
    private String[] assign_interface;
    private String[] depoly_mpu;
    private String belongTo ;
    private String description;
    
//    public SpawnNewVirDevice(String vnd_name,String vnd_status,String[] assign_interface,
//            String[] depoly_mpu,String belongTo,String description){
//        this.vnd_name = vnd_name;
//        this.vnd_status = vnd_status;
//        this.assign_interface = assign_interface;
//        this.depoly_mpu = depoly_mpu;
//        this.belongTo = belongTo;
//        this.description = description;
//    }
    
    public String getVnd_name() {
        return vnd_name;
    }
    public void setVnd_name(String vnd_name) {
        this.vnd_name = vnd_name;
    }
    public String getVnd_status() {
        return vnd_status;
    }
    public void setVnd_status(String vnd_status) {
        this.vnd_status = vnd_status;
    }
    public String[] getAssign_interface() {
        return assign_interface;
    }
    public void setAssign_interface(String[] assign_interface) {
        this.assign_interface = assign_interface;
    }
    public String[] getDepoly_mpu() {
        return depoly_mpu;
    }
    public void setDepoly_mpu(String[] depoly_mpu) {
        this.depoly_mpu = depoly_mpu;
    }
    public String getBelongTo() {
        return belongTo;
    }
    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
}
