package com.xigua.serviceImp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xigua.JSONTemplate.ControllerSettings;
import com.xigua.JSONTemplate.JSONTemplate;
import com.xigua.model.vlanEdit;
import com.xigua.service.vlanService;
import com.xigua.util.HttpRequestUtil;
@Service
public class vlanServiceImpl implements vlanService{
    
    private static final Logger LOG = LoggerFactory.getLogger(vlanServiceImpl.class);
    private String Ipaddress = ControllerSettings.ip;

    @Override
    public void editVlan(vlanEdit edit,String vndName) {
        // TODO Auto-generated method stub
        String url = Ipaddress + "/restconf/config/network-topology:network-topology/topology/"
                + "topology-netconf/node/"+vndName+"/yang-ext:mount/"
                        + "zxr10-vlan-dev-c600:configuration/switchvlan/if-vlan";
        String EditVlan =JSONTemplate.editVlan;
        JSONObject OBJ = JSON.parseObject(EditVlan);
        JSONObject arr = OBJ.getJSONObject("zxr10-vlan-dev-c600:if-vlan").
                getJSONArray("zxr10-vlan-dev-c600:if-vlan").getJSONObject(0);
        arr.put("zxr10-vlan-dev-c600:mode", edit.getMode());
        arr.put("zxr10-vlan-dev-c600:if-sub-index", edit.getIf_sub_index());
        arr.put("zxr10-vlan-dev-c600:vlan-info", edit.getVlan_info());
        arr.put("zxr10-vlan-dev-c600:if-index", edit.getIf_index());
        LOG.info("开始修改"+vndName+"上联口端口");
        HttpRequestUtil.Put(url, OBJ.toJSONString());
    }

}
