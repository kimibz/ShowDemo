package com.xigua.serviceImp;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xigua.JSONTemplate.JSONTemplate;
import com.xigua.model.SpawnInfo;
import com.xigua.model.SysConfig;
import com.xigua.service.SysInfoService;
import com.xigua.util.HttpRequestUtil;
import com.xigua.util.Util;

@Service
public class SysInfoServiceImpl implements SysInfoService{
    @Override
    public SysConfig getConfig(String device) {
        // TODO Auto-generated method stub
        String url = "http://localhost:8181/restconf/config/"
                + "opendaylight-inventory:nodes/node/"
                + device +"/yang-ext:mount/zxr10-pm-sys:configuration/sys/";
        String result = HttpRequestUtil.Get(url);
        SysConfig config  = new SysConfig();
        if(result != null){
            JSONObject object = JSON.parseObject(result);
            JSONObject sys = (JSONObject) object.get("sys");
            config.setContact(sys.getString("contact"));
            config.setLocation(sys.getString("location"));
            config.setCpuIisolate(sys.getString("cpu-isolate"));
            config.setHostname(sys.getString("hostname"));
            config.setLoadMmode(sys.getString("load-mode"));
            return config;
        }else{
            return config;
        }
    }

    @Override
    public void SpawnNewDevice(SpawnInfo info) {
        // TODO Auto-generated method stub
        String template = JSONTemplate.SpawnDeviceJSON;
        JSONObject object = JSON.parseObject(template);
        JSONArray arr =object.getJSONArray("node");
        JSONObject device_obj = JSON.parseObject(arr.get(0)+"");
        device_obj.put("netconf-node-topology:host", info.getAddress());
        device_obj.put("netconf-node-topology:keepalive-delay", info.getAliveTime());
        device_obj.put("node-id", info.getNode_id());
        device_obj.put("host-tracker-service:id", info.getHost_id());
        device_obj.put("netconf-node-topology:password", info.getPassword());
        device_obj.put("netconf-node-topology:username", info.getUsername());
        device_obj.put("netconf-node-topology:tcp-only", info.getTcp());
        device_obj.put("netconf-node-topology:port", info.getPort());
        arr.clear();
        arr.add(0, device_obj);
        object.put("node", arr);
        String url = "http://localhost:8181/restconf/config/network-topology:network-topology/"
                + "topology/topology-netconf/node/"+info.getNode_id();
        HttpRequestUtil.Put(url, object.toJSONString());
    }

}
