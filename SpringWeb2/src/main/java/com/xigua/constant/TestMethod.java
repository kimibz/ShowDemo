package com.xigua.constant;

import com.alibaba.fastjson.JSON;
import com.xigua.util.HttpRequestUtil;

public class TestMethod {
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        Boolean ifConnected = false ;
        String device_name = "zte";
        String url = "http://localhost:8181"+"/restconf/operational/network-topology:network-topology/topology/"
                + "topology-netconf/node/"+device_name;
        // 创建HttpClient实例
        // get请求返回结果  
        String result = HttpRequestUtil.Get(url);
        String status = JSON.parseObject(result).getJSONArray("node").getJSONObject(0)
                .getString("netconf-node-topology:connection-status");
        System.out.println(status);
    }
} 
