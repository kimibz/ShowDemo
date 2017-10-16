package com.xigua.serviceImp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xigua.JSONTemplate.ControllerSettings;
import com.xigua.JSONTemplate.JSONTemplate;
import com.xigua.model.Port;
import com.xigua.model.SpawnInfo;
import com.xigua.model.SysConfig;
import com.xigua.service.SysInfoService;
import com.xigua.util.HttpRequestUtil;

@Service
public class SysInfoServiceImpl implements SysInfoService{
    
    private String Ipaddress = ControllerSettings.ip;
    private static final Logger LOG = LoggerFactory.getLogger(SysInfoServiceImpl.class);
    
    @Override
    public SysConfig getConfig(String device) {
        // TODO Auto-generated method stub
//        String url = Ipaddress+"/restconf/config/"
//                + "opendaylight-inventory:nodes/node/"
//                + device +"/yang-ext:mount/zxr10-pm-sys:configuration/sys/";
        String url = Ipaddress+"/restconf/config/network-topology:network-topology"
                + "/topology/topology-netconf/node/"
                + device +"/yang-ext:mount/zxr10-pm-sys:configuration/sys/";
        String result = HttpRequestUtil.Get(url);
        SysConfig config  = new SysConfig();
        if(result != null){
            JSONObject object = JSON.parseObject(result);
            JSONObject sys = (JSONObject) object.get("sys");
//            config.setContact(sys.getString("contact"));
//            config.setLocation(sys.getString("location"));
            config.setCpuIisolate(sys.getString("cpu-isolate"));
//            config.setHostname(sys.getString("hostname"));
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
        String url = Ipaddress+"/restconf/config/network-topology:network-topology/"
                + "topology/topology-netconf/node/"+info.getNode_id();
        HttpRequestUtil.Put(url, object.toJSONString());
    }

    @Override
    public List<Port> getPortList(String oltId) {
        // TODO Auto-generated method stub
        String url =Ipaddress+"/restconf/operational/network-"
                + "topology:network-topology/topology/topology-netconf/node/"
                +oltId+"/yang-ext:mount/zxr10-pm-sys:state/sys/ports";
        String jsonStr = HttpRequestUtil.Get(url);
        JSONObject object = JSON.parseObject(jsonStr);
        JSONObject jsonPort = object.getJSONObject("ports");
        List <Port> port = JSON.parseArray(jsonPort.get("port").
                toString(),Port.class);
        LOG.info(port.get(0).getPortname());
        return port;
    }

    @Override
    public Port getPort(String oltId, String shelf, String slot, String subslot, String portno) {
        // TODO Auto-generated method stub
        String url =Ipaddress+"/restconf/operational/network-topology:network-topology"
                + "/topology/topology-netconf/node/"
                + oltId +"/yang-ext:mount/zxr10-pm-sys:state/sys/ports/"
                        + "port/"+shelf+"/"+slot+"/"+subslot+"/"+portno;
        String jsonStr = HttpRequestUtil.Get(url);
        JSONObject object = JSON.parseObject(jsonStr);
        List <Port> portlist = JSON.parseArray(object.get("port").
                toString(),Port.class);
        Port port = portlist.get(0);
        return port;
    }

   

}
