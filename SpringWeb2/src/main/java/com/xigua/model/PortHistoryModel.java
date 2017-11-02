package com.xigua.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class PortHistoryModel extends rateStats{

    /**
     * XIGUA
     */
    private static final long serialVersionUID = -4164978533401716054L;
    
    private Integer id;
    
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date time;
    
    private String oltId;
    
    private String vndName;
    
    private String interfaceName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getOltId() {
        return oltId;
    }

    public void setOltId(String oltId) {
        this.oltId = oltId;
    }

    public String getVndName() {
        return vndName;
    }

    public void setVndName(String vndName) {
        this.vndName = vndName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
    
}
