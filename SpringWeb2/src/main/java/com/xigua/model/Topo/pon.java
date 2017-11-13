package com.xigua.model.Topo;

import java.io.Serializable;
import java.util.List;

public class pon implements Serializable{

    /**
     * XIGUA
     */
    private static final long serialVersionUID = 4577507209272213766L;

    
    private String id;
    private List<Onu> onu;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<Onu> getOnu() {
        return onu;
    }
    public void setOnu(List<Onu> onu) {
        this.onu = onu;
    }
    
    
}
