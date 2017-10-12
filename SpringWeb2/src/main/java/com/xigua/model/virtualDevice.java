package com.xigua.model;

import java.io.Serializable;
import java.util.List;

public class virtualDevice implements Serializable{

    /**
     * XIGUA
     */
    private static final long serialVersionUID = -8537848918534701717L;
    
    private int vnd_id;
    private String vnd_name;
    private String vnd_status;
    private List<String> assign_interface;
    private List<String> depoly_mpu;
    private String belongTo ;
    private String description;
    
    public int getVnd_id() {
        return vnd_id;
    }
    public void setVnd_id(int vnd_id) {
        this.vnd_id = vnd_id;
    }
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
    public List<String> getAssign_interface() {
        return assign_interface;
    }
    public void setAssign_interface(List<String> assign_interface) {
        this.assign_interface = assign_interface;
    }
    public List<String> getDepoly_mpu() {
        return depoly_mpu;
    }
    public void setDepoly_mpu(List<String> depoly_mpu) {
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
