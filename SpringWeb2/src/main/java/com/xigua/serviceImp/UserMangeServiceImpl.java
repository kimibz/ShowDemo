package com.xigua.serviceImp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xigua.JSONTemplate.ControllerSettings;
import com.xigua.dao.ManageVirtualUsrDao;
import com.xigua.model.ManageVirtualUsr;
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

}
