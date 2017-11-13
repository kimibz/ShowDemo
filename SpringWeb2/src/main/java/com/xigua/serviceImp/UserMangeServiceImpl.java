package com.xigua.serviceImp;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xigua.JSONTemplate.ControllerSettings;
import com.xigua.dao.ManageVirtualUsrDao;
import com.xigua.model.ManageVirtualUsr;
import com.xigua.model.Port;
import com.xigua.model.usrOLTManageModel;
import com.xigua.service.UserMangeService;
import com.xigua.util.HttpRequestUtil;
@Service
public class UserMangeServiceImpl implements UserMangeService{
    
    private static final Logger LOG = LoggerFactory.getLogger(UserMangeServiceImpl.class);
    @Autowired
    private ManageVirtualUsrDao Dao;
    
    private String Ipaddress = ControllerSettings.ip;
    
    @Override
    public List<ManageVirtualUsr> getAllDevice(String username) {
        // TODO Auto-generated method stub
        String url = Ipaddress+"/restconf/operational/"
                + "network-topology:network-topology/topology/topology-netconf/node/";
        List<ManageVirtualUsr> list = Dao.getVirtualList(username);
        for (ManageVirtualUsr usr : list) {
            String status;
            String nodeId = "vDevice_"+usr.getOltId()+"_"+usr.getVirtualName();
            LOG.info("寻找虚拟切片:"+nodeId);
            String checkUrl = url + nodeId;
            String jsonStr = HttpRequestUtil.Get(checkUrl);
            JSONObject object = JSON.parseObject(jsonStr).getJSONArray("node").getJSONObject(0);
            if( object.getString("netconf-node-topology:connection-status").equals("connected")) {
                status ="在线";
            }else {
                status ="离线";
            }
            usr.setStatus(status);
        }
        return list;
    }

    @Override
    public usrOLTManageModel getMangeInfo(String oltId) {
        // TODO Auto-generated method stub
        usrOLTManageModel model = new usrOLTManageModel();
        String url = Ipaddress+"/restconf/operational/"
                + "network-topology:network-topology/topology/topology-netconf/node/"
                + oltId +"/yang-ext:mount/zxr10-pm-sys:state/sys/system";
        String result = HttpRequestUtil.Get(url);
        JSONObject obj =  JSON.parseObject(result);
        String device_type = obj.getJSONObject("system").getString("device-type");
        String device_version = obj.getJSONObject("system").getString("device-version");
        String system_version = obj.getJSONObject("system").getString("system-version");
        model.setDevice_type(device_type);
        model.setDevice_version(device_version);
        model.setSystem_version(system_version);
        String portUrl = Ipaddress + "/restconf/operational/"
                + "network-topology:network-topology/topology/topology-netconf/node/"
                + oltId +"/yang-ext:mount/zxr10-pm-sys:state/sys/ports";
        JSONObject portObj =  JSON.parseObject(HttpRequestUtil.Get(portUrl));
        JSONArray arr = portObj.getJSONObject("ports").getJSONArray("port");
        List <Port> Port = JSON.parseObject(arr.toJSONString(),new TypeReference<List<Port>>(){}); 
        //根据slot&&portNo排序
        Collections.sort(Port,new Comparator<Port>(){
            public int compare(Port arg0, Port arg1) {
                int hits0 = arg0.getSlot();  
                int hits1 = arg1.getSlot();
                int portNo0 = arg0.getPortno();
                int portNo1 = arg1.getPortno();
                if (hits1 > hits0) {    
                    return -1;  
                } else if ((hits1 == hits0) && (portNo1 > portNo0)) {  
                    return -1;
                }  else if(hits1 == hits0) {
                    return 0;
                }  else {  
                    return 1;  
                }  
            }
        });
        model.setPortList(Port);
        return model;
    }

}
