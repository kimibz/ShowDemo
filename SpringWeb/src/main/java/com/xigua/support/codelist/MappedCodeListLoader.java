package com.xigua.support.codelist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

public class MappedCodeListLoader implements CodeListLoader {

	private Map<String, String> codeListMap = null;
	
	private List<CodeBean> codeLists = null;

	public Map<String, String> getCodeListMap() {
        return this.codeListMap;
    }
	
    public void setCodeListMap(Map<String, String> codeListMap) {
        this.codeListMap = codeListMap;
    }
 
	@Override
	public void load() {

		if (this.codeLists != null) {
            return;
        }
        if (this.codeListMap == null) {
            this.codeLists = Collections.unmodifiableList(new ArrayList<CodeBean>());
            return;
        }
        List<CodeBean> list = new ArrayList<CodeBean>();
        Iterator<String> it = codeListMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = codeListMap.get(key);
            CodeBean cb = new CodeBean();
            cb.setId(key);
            cb.setName(value);
            list.add(cb);
        }
        this.codeLists = Collections.unmodifiableList(list);
	}

	@Override
	public CodeBean[] getCodeBeans() {

		if (this.codeLists == null) {
            return new CodeBean[0];
        }

        CodeBean[] cb = new CodeBean[codeLists.size()];
        return this.codeLists.toArray(cb);
	}

}
