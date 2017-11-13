package com.xigua.serviceImp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xigua.JSONTemplate.ControllerSettings;
import com.xigua.JSONTemplate.JSONTemplate;
import com.xigua.model.OnuSpawnModel;
import com.xigua.model.Topo.Onu;
import com.xigua.service.UserOnuService;
import com.xigua.util.HttpRequestUtil;
import com.xigua.util.Util;
@Service
public class UserOnuServiceImpl implements UserOnuService{
    
    private String Ipaddress = ControllerSettings.ip;
    
    private static final Logger LOG = LoggerFactory.getLogger(UserOnuServiceImpl.class); 
    
    @Override
    public void addOnu(String vndName, OnuSpawnModel OnuModel) {
        // TODO Auto-generated method stub
        Util util = new Util();
        int ifIndex = util.getIndex(OnuModel.getIfIndex());
        String url = Ipaddress+"/restconf/config/"
                + "network-topology:network-topology/"
                + "topology/topology-netconf/node/"+vndName
                + "/yang-ext:mount/zxr10-test:configuration";
        JSONObject onu = JSON.parseObject(JSONTemplate.addOnu);
        JSONObject onuList = onu.getJSONArray("zxr10-test:onulist")
                .getJSONObject(0);
        onuList.put("zxr10-test:zxAnGponOnuMgmtTypeName",OnuModel.getOnuType());
        onuList.put("zxr10-test:zxAnGponOnuMgmtRegMode", "regModeSn");
        onuList.put("zxr10-test:ifIndex", ""+ifIndex);
        onuList.put("zxr10-test:zxAnPonOnuIndex", OnuModel.getOnuId());
        onuList.put("zxr10-test:zxAnGponOnuMgmtSn",OnuModel.getOnuMac());
        HttpRequestUtil.Post(url, onu.toString());
    }

    @Override
    public List<Onu> getOnu(String vndName, String interfaceName) {
        // TODO Auto-generated method stub
        String url = "http://localhost:8181/restconf/"
                + "operational/network-topology:network-topology/"
                + "topology/topology-netconf/node/" + vndName
                + "/yang-ext:mount/zxr10-test:state";
        String result = HttpRequestUtil.Get(url);
        String JSON_ARRAY_STR = JSON.parseObject(result).
                getJSONObject("state").getJSONArray("onuStatus").toJSONString();
//        JSONArray arr = JSON.parseObject(result).getJSONObject("state")
//                .getJSONArray("onuStatus");
        ArrayList<Onu> onuList = JSON.parseObject(JSON_ARRAY_STR, new TypeReference<ArrayList<Onu>>() {});
        ArrayList<Onu> onuOfPon = new ArrayList<Onu>();
        Util util = new Util();
        int ifIndex = util.getIndex(interfaceName);
        for(Onu onu : onuList) {
            if(onu.getZxAnGponSrvOnuPhaseStatus().equals("offline")) {
                onu.setZxAnGponSrvOnuPhaseStatus("离线");
            }else {
                onu.setZxAnGponSrvOnuPhaseStatus("在线");
            }
            if(onu.getIfIndex() == ifIndex) {
                onuOfPon.add(onu);
            }
        }
        Collections.sort(onuOfPon,new Comparator<Onu>(){
            public int compare(Onu arg0, Onu arg1) {
                int hits0 = arg0.getZxAnPonOnuIndex();  
                int hits1 = arg1.getZxAnPonOnuIndex();  
                if (hits1 > hits0) {  
                    return -1;  
                } else if (hits1 == hits0) {  
                    return 0;  
                } else {  
                    return 1;  
                } 
            }
        });
        return onuOfPon;
    }

    @Override
    public void deleteOnu(String vndName, String interfaceName) {
        // TODO Auto-generated method stub
        String []a = interfaceName.split("_");
        String ifIndex = a[0];
        String OnuIndex = a[1];
        String url = Ipaddress+"/restconf/config/network-topology:network-topology/"
                + "topology/topology-netconf/node/" + vndName
                + "/yang-ext:mount/zxr10-test:configuration/onulist/"+ifIndex+"/"+OnuIndex;
        HttpRequestUtil.Delete(url);
    }
    
}
