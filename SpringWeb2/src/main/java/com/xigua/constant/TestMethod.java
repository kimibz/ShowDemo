package com.xigua.constant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xigua.JSONTemplate.JSONTemplate;

public class TestMethod {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String EditVlan =JSONTemplate.editVlan;
        JSONObject OBJ = JSON.parseObject(EditVlan);
        System.out.println(OBJ.toJSONString());
        JSONObject arr = OBJ.getJSONObject("zxr10-vlan-dev-c600:if-vlan").
                getJSONArray("zxr10-vlan-dev-c600:if-vlan").getJSONObject(0);
        arr.put("zxr10-vlan-dev-c600:mode", "tag");
        arr.put("zxr10-vlan-dev-c600:if-sub-index", "0");
        arr.put("zxr10-vlan-dev-c600:vlan-info", "100");
        arr.put("zxr10-vlan-dev-c600:if-index", "285282818");
        System.out.println(OBJ.toJSONString());
    }
} 
