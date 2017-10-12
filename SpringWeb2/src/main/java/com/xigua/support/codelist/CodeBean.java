package com.xigua.support.codelist;

import java.io.Serializable;

public class CodeBean implements Serializable {

	private static final long serialVersionUID = 4573331057217445439L;

	private String id = "";

    private String name = "";
    
    public CodeBean(){};
    
    public CodeBean(String id) {
		this.id = id;
		this.name = id;
	}
    
    public CodeBean(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public void setId(String id) {
        this.id = (id == null) ? "" : id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = (name == null) ? "" : name;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return id + ":" + name;
    }

    public String getCodeCommaName() {
        return id + "," + name;
    }
    
	@Override
	public boolean equals(Object obj) {
		if (obj == null 
				|| !(obj instanceof CodeBean) 
				|| null == this.id  
				|| null == this.name) {
			return false;
		}

		CodeBean codeBean = (CodeBean) obj;

		return this.id.equals(codeBean.getId())
				&& this.name.equals(codeBean.getName());
	}

	@Override
	public int hashCode() {
		return (this.id + this.name).hashCode();
	}
}
