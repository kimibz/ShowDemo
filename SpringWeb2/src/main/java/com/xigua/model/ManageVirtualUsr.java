package com.xigua.model;

import java.io.Serializable;

public class ManageVirtualUsr implements Serializable{

    /**
     * XIGUA
     */
    private static final long serialVersionUID = 8421364830710958878L;
    
    private Integer id ;
    private String oltId;
    private String virtualName;
    private String user;
    private String status;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getOltId() {
        return oltId;
    }
    public void setOltId(String oltId) {
        this.oltId = oltId;
    }
    public String getVirtualName() {
        return virtualName;
    }
    public void setVirtualName(String virtualName) {
        this.virtualName = virtualName;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
}
