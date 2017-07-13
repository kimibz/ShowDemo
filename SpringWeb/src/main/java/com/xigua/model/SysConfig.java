package com.xigua.model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xigua.jsonserialize.JsonSerializeS001CodeList;

public class SysConfig implements Serializable{
    
    /**
     * 2017-07-10 xigua
     */
    private static final long serialVersionUID = -2605119310837771894L;

    private String hostname;
    
    private String contact;
    
    private String location;
    
    @JsonSerialize(using = JsonSerializeS001CodeList.class)
    private String cpuIisolate;
    
    private String loadMmode;
    
    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCpuIisolate() {
        return cpuIisolate;
    }

    public void setCpuIisolate(String cpuIisolate) {
        this.cpuIisolate = cpuIisolate;
    }

    public String getLoadMmode() {
        return loadMmode;
    }

    public void setLoadMmode(String loadMmode) {
        this.loadMmode = loadMmode;
    }
    
    
}
