package com.xigua.model.Topo;

import java.io.Serializable;
import java.util.List;

public class slot implements Serializable{

    /**
     * XIGUA
     */
    private static final long serialVersionUID = -6729515590716113784L;
    
    private String id;
    private List<pon> pon;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<pon> getPon() {
        return pon;
    }
    public void setPon(List<pon> pon) {
        this.pon = pon;
    }
    
}
