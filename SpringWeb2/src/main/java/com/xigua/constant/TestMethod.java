package com.xigua.constant;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xigua.JSONTemplate.ControllerSettings;
import com.xigua.util.HttpRequestUtil;

public class TestMethod {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String Ipaddress = ControllerSettings.ip;
        String deviceId = "zte";
        String url = Ipaddress+"/restconf/operational/network-topology:network-topology"
                + "/topology/topology-netconf/node/"+deviceId+"/yang-ext:mount/zxr10-pm-sys:state/sys/cpus";
        String jsonStr = HttpRequestUtil.Get(url);
        JSONObject obj = JSON.parseObject(jsonStr);
        JSONArray arr = obj.getJSONObject("cpus").getJSONArray("cpu");
        List<String> cpu = new ArrayList<String>();
        for(int i=0; i<arr.size() ; i++) {
            cpu.add(arr.getJSONObject(0).getString("cpuname"));
        }
        System.out.println(cpu.get(1));
    }
} 
