package com.xigua.constant;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xigua.model.Port;
import com.xigua.util.HttpRequestUtil;

public class TestMethod {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String url = "http://localhost:8181/restconf/operational/"
                + "network-topology:network-topology/topology/"
                + "topology-netconf/node/vDevice_zte_vnd001/yang-ext:mount/"
                + "zxr10-pm-sys:state/sys/ports";
        String result = HttpRequestUtil.Get(url);
        JSONObject obj =  JSON.parseObject(result);
        JSONArray arr = obj.getJSONObject("ports").getJSONArray("port");
        List <Port> Port = JSON.parseObject(arr.toJSONString(),new TypeReference<List<Port>>(){}); 
        System.out.println(Port.get(0).getShelf());
    }
} 
