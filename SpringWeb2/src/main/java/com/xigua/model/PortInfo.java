package com.xigua.model;

import java.io.Serializable;

public class PortInfo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 9041955952709098060L;
    
    private String shelf;
    private String slot;
    private String portNum;
    private String type;
    private String speed;
    private String interfaceName;
    public String getShelf() {
        return shelf;
    }
    public void setShelf(String shelf) {
        this.shelf = shelf;
    }
    public String getSlot() {
        return slot;
    }
    public void setSlot(String slot) {
        this.slot = slot;
    }
    public String getPortNum() {
        return portNum;
    }
    public void setPortNum(String portNum) {
        this.portNum = portNum;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getSpeed() {
        return speed;
    }
    public void setSpeed(String speed) {
        this.speed = speed;
    }
    public String getInterfaceName() {
        return interfaceName;
    }
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
    
}
