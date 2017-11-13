package com.xigua.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xigua.JSONTemplate.ControllerSettings;
import com.xigua.JSONTemplate.JSONTemplate;
import com.xigua.dao.PortHistoryDao;
import com.xigua.model.PortHistoryModel;
import com.xigua.model.rateStats;
import com.xigua.model.vlanEdit;
import com.xigua.service.vlanService;
import com.xigua.util.HttpRequestUtil;
import com.xigua.util.Util;
@Service
public class vlanServiceImpl implements vlanService{
    @Autowired 
    private PortHistoryDao dao;
    
    private static final Logger LOG = LoggerFactory.getLogger(vlanServiceImpl.class);
    Util util = new Util();
    private String Ipaddress = ControllerSettings.ip;

    @Override
    public void editVlan(String vndName,String interfaceName,String vlan) {
        // TODO Auto-generated method stub
        String ifSubIndex = "0" ;
        String ifIndex = ""+util.getIndex(interfaceName);
        String url = Ipaddress+"/restconf/config/network-topology:network-topology/"
                + "topology/topology-netconf/node/"
                + vndName +"/yang-ext:mount/zxr10-vlan-dev-c600:configuration/switchvlan"
                        + "/if-vlan/if-vlan/"+ifIndex+"/"+ifSubIndex;
        vlanEdit edit = new vlanEdit();
        edit.setIf_index(""+util.getIndex(interfaceName));
        edit.setIf_sub_index("0");
        edit.setVlan_info(vlan);
        String EditVlan =JSONTemplate.createVlan;
        JSONObject OBJ = JSON.parseObject(EditVlan);
        JSONObject arr = OBJ.getJSONArray("zxr10-vlan-dev-c600:if-vlan").getJSONObject(0);
        arr.put("zxr10-vlan-dev-c600:if-sub-index", edit.getIf_sub_index());
        arr.put("zxr10-vlan-dev-c600:vlan-info", edit.getVlan_info());
        arr.put("zxr10-vlan-dev-c600:if-index", edit.getIf_index());
        arr.put("zxr10-vlan-dev-c600:mode", "tag");
        LOG.info("开始修改"+vndName+"上联口端口");
        HttpRequestUtil.Put(url, OBJ.toJSONString());
    }

    @Override
    public vlanEdit getVlan(String vndName, String interfaceName) {
        // TODO Auto-generated method stub
        String ifSubIndex = "0" ;
        String ifIndex = ""+util.getIndex(interfaceName);
        String url = Ipaddress + "/restconf/config/"
                + "network-topology:network-topology/topology/"
                + "topology-netconf/node/"
                + vndName +"/yang-ext:mount/zxr10-vlan-dev-c600:configuration/"
                + "switchvlan/if-vlan/if-vlan-info/"+ ifIndex +"/"+ ifSubIndex;
        String modeUrl = Ipaddress + "/restconf/config/network-topology:network-topology/"
                + "topology/topology-netconf/node/"
                + vndName +"/yang-ext:mount/zxr10-vlan-dev-c600:configuration/switchvlan/"
                        + "if-attr/if-mode/"+ ifIndex +"/"+ ifSubIndex;
        String result = HttpRequestUtil.Get(url);
        String modeResult = HttpRequestUtil.Get(modeUrl);
        vlanEdit edit = new vlanEdit();
        edit.setIf_index(ifIndex);
        edit.setIf_sub_index(ifSubIndex);
        String vlanTag = null ;
        if(result != null) {
            vlanTag = JSON.parseObject(result).
                    getJSONArray("if-vlan-info").getJSONObject(0).getString("vlan-tag-info");
        }else {
            vlanTag = null ;
        }
        edit.setVlan_info(vlanTag);
        String mode = null;
        if(modeResult != null) {
            mode = JSON.parseObject(modeResult).
                    getJSONArray("if-mode").getJSONObject(0).getString("mode");
        }else {
            mode = null ;
        }
        edit.setMode(mode);
        return edit;
    }

    @Override
    public rateStats getStats(String vndName, String interfaceName) {
        // TODO Auto-generated method stub
        String ifSubIndex = "0" ;
        String ifIndex = ""+util.getIndex(interfaceName);
        String url = Ipaddress+"/restconf/operational/network-topology:network-topology/"
                + "topology/topology-netconf/node/"+ vndName 
                + "/yang-ext:mount/zxr10-stats:state/zxr10-interface-stats/i"
                + "nterface-if-performance-objects/if-rate-stats-lists/"+ifIndex+"/"+ifSubIndex;
        String result = HttpRequestUtil.Get(url);
        JSONObject rate = JSON.parseObject(result).getJSONArray("if-rate-stats-lists").getJSONObject(0);
        rateStats stats = new rateStats();
        stats.setIfIndex(rate.getString("ifindex"));
        stats.setIfSubindex(rate.getString("ifsubindex"));
        stats.setOctetRx(rate.getString("if-curr-rx-octet-rate"));
        stats.setOctetTx(rate.getString("if-curr-tx-octet-rate"));
        stats.setPktRxRate(rate.getString("if-curr-rx-pkt-rate"));
        stats.setPktTxRate(rate.getString("if-curr-tx-pkt-rate"));
        stats.setOctetRxPeak(rate.getString("if-curr-rx-octet-peak-rate"));
        stats.setOctetTxPeak(rate.getString("if-curr-tx-octet-peak-rate"));
        return stats;
    }

    @Override
    public void saveStats(String vndName) {
        // TODO Auto-generated method stub
        String url = "http://localhost:8181/restconf/"
                + "operational/network-topology:network-topology/"
                + "topology/topology-netconf/node/" + vndName
                + "/yang-ext:mount/zxr10-stats:state/zxr10-interface-stats/"
                + "interface-if-performance-objects";
        String result = HttpRequestUtil.Get(url);
        JSONArray arr = JSON.parseObject(result).getJSONObject("interface-if-performance-objects")
                .getJSONArray("if-rate-stats-lists");
        for(int i=0 ; i<arr.size() ; i++) {
            PortHistoryModel model = new PortHistoryModel();
            JSONObject rate = arr.getJSONObject(i);
            String ifIndex = rate.getString("ifindex");
            String interfaceName = Util.ifIndexToInterface(ifIndex);
            String oltId = vndName.substring(vndName.indexOf("_")+1, vndName.lastIndexOf("_"));
            String vnd = vndName.substring(vndName.lastIndexOf("_")+1, vndName.length());;
            model.setOltId(oltId);
            model.setVndName(vnd);
            model.setInterfaceName(interfaceName);
            model.setOctetRx(rate.getString("if-curr-rx-octet-rate"));
            model.setOctetTx(rate.getString("if-curr-tx-octet-rate"));
            model.setPktRxRate(rate.getString("if-curr-rx-pkt-rate"));
            model.setPktTxRate(rate.getString("if-curr-tx-pkt-rate"));
            model.setOctetRxPeak(rate.getString("if-curr-rx-octet-peak-rate"));
            model.setOctetTxPeak(rate.getString("if-curr-tx-octet-peak-rate"));
            dao.insertPortHistory(model);
        }
    }

    @Override
    public List<PortHistoryModel> getHistoryList(String oltId,String vndName) {
        // TODO Auto-generated method stub
        List<PortHistoryModel> list = new ArrayList<PortHistoryModel>();
        list = dao.getPortHistory(oltId, vndName);
        return list;
    }

    @Override
    public void deleteVlan(String vndName, String interfaceName, String vlan) {
        // TODO Auto-generated method stub
        String ifSubIndex = "0" ;
        String ifIndex = ""+util.getIndex(interfaceName);
        String url = Ipaddress+"/restconf/config/network-topology:network-topology/"
                + "topology/topology-netconf/node/"
                + vndName +"/yang-ext:mount/zxr10-vlan-dev-c600:configuration/switchvlan"
                        + "/if-vlan/if-vlan-info/"+ifIndex+"/"+ifSubIndex;
        LOG.info("开始删除"+vndName+"上联口端口");
        HttpRequestUtil.Delete(url);
    }

}
