package com.xigua.model.Topo;

import java.io.Serializable;
import java.util.List;

public class VOLT implements Serializable{

    /**
     * XIGUA
     */
    private static final long serialVersionUID = 8758799225553620481L;
    
    private String id;
    
    private List<slot>slot;
    
    private Boolean ifOnline ;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<slot> getSlot() {
        return slot;
    }
    public void setSlot(List<slot> slot) {
        this.slot = slot;
    }
    public Boolean getIfOnline() {
        return ifOnline;
    }
    public void setIfOnline(Boolean ifOnline) {
        this.ifOnline = ifOnline;
    }
    
}
