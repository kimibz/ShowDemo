package com.xigua.jsonserialize;

import java.io.IOException;

import org.springframework.web.context.ContextLoader;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.xigua.support.codelist.MappedCodeListLoader;


public class JsonSerializeS001CodeList extends JsonSerializer<Object> {
    
//  private static String _code_value = "auditOpinionCodeList";
    
    @Override
    public void serialize(Object value, JsonGenerator jgen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException {
        
        MappedCodeListLoader codeListMap = (MappedCodeListLoader)ContextLoader.getCurrentWebApplicationContext().getBean("s001CodeList");
//      jgen.writeString(ContextUtils.getBean(_code_value, MappedCodeListLoader.class).getCodeListMap().get(value.toString()));
        jgen.writeString(codeListMap.getCodeListMap().get(value.toString()));
    }
}
