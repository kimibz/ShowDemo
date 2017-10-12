package com.xigua.model;

import java.io.Serializable;

public class Port implements Serializable{

    /**
     * XIGUA
     * "shelf": 1,
        "slot": 17,
        "subslot": 0,
        "portno": 2,
        "porttype": 132,
        "porteoproperty": 0,
        "portspeed": 10000,
        "portphystatus": 0,
        "portname": "xgei-1/17/2"
     */
    private static final long serialVersionUID = 6841872604520399522L;
    
    private int shelf;
    private int slot;
    private int sublot;
    private int portno;
    private int porttype;
    private int porteoproperty;
    private int portspeed;
    private int portphystatus;
    private String portname;
    
    public int getShelf() {
        return shelf;
    }
    public void setShelf(int shelf) {
        this.shelf = shelf;
    }
    public int getSublot() {
        return sublot;
    }
    public void setSublot(int sublot) {
        this.sublot = sublot;
    }
    public int getPortno() {
        return portno;
    }
    public void setPortno(int portno) {
        this.portno = portno;
    }
    public int getPorttype() {
        return porttype;
    }
    public void setPorttype(int porttype) {
        this.porttype = porttype;
    }
    public int getPorteoproperty() {
        return porteoproperty;
    }
    public void setPorteoproperty(int porteoproperty) {
        this.porteoproperty = porteoproperty;
    }
    public int getPortspeed() {
        return portspeed;
    }
    public void setPortspeed(int portspeed) {
        this.portspeed = portspeed;
    }
    public int getPortphystatus() {
        return portphystatus;
    }
    public void setPortphystatus(int portphystatus) {
        this.portphystatus = portphystatus;
    }
    public String getPortname() {
        return portname;
    }
    public void setPortname(String portname) {
        this.portname = portname;
    }
    public int getSlot() {
        return slot;
    }
    public void setSlot(int slot) {
        this.slot = slot;
    }
    
    
}
