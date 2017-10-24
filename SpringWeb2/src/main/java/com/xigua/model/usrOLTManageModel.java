package com.xigua.model;

import java.io.Serializable;
import java.util.List;

public class usrOLTManageModel implements Serializable{

    /**
     * XIGUA
     */
    private static final long serialVersionUID = -8529158019286332430L;
    
    private String device_type;
    
    private String device_version;
    
    private String system_version;
    
    private List<Port> portList ;

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_version() {
        return device_version;
    }

    public void setDevice_version(String device_version) {
        this.device_version = device_version;
    }

    public String getSystem_version() {
        return system_version;
    }

    public void setSystem_version(String system_version) {
        this.system_version = system_version;
    }

    public List<Port> getPortList() {
        return portList;
    }

    public void setPortList(List<Port> portList) {
        this.portList = portList;
    }
    
    

}
