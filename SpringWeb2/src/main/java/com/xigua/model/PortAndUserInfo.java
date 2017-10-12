package com.xigua.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PortAndUserInfo implements Serializable{

    /**
     * XIGUA
     */
    private static final long serialVersionUID = -7212754925938206527L;
    
    private List<String> port = new ArrayList<String>();
    private List<String> user = new ArrayList<String>();
    public List<String> getPort() {
        return port;
    }
    public void setPort(List<String> port) {
        this.port = port;
    }
    public List<String> getUser() {
        return user;
    }
    public void setUser(List<String> user) {
        this.user = user;
    }
    
    

}
