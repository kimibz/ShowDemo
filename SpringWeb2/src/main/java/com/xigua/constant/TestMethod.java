package com.xigua.constant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xigua.JSONTemplate.JSONTemplate;
import com.xigua.util.HttpRequestUtil;

public class TestMethod {
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        String ifIndex = "285282569";
        String ifSubIndex = "0";
        String jsonStr = JSONTemplate.trunkPort;
        String url = "http://localhost:8181/restconf/config/network-topology:network-topology/topology"
                + "/topology-netconf/node/vDevice_zte_vnd001/yang-ext:mount/"
                + "zxr10-vlan-dev-c600:configuration/switchvlan/if-attr/if-mode/"
                + ifIndex+ "/"+ ifSubIndex;
        JSONObject object = JSON.parseObject(jsonStr);
        JSONObject port = object.getJSONArray("zxr10-vlan-dev-c600:if-mode").getJSONObject(0);
        port.put("zxr10-vlan-dev-c600:if-index", ifIndex);
        port.put("zxr10-vlan-dev-c600:if-sub-index", ifSubIndex);
        HttpRequestUtil.Put(url, object.toJSONString());
    }
} 
