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
import com.xigua.dao.ManageVirtualUsrDao;
import com.xigua.model.ManageVirtualUsr;
import com.xigua.model.PortInfo;
import com.xigua.model.Topo.TopoInfo;
import com.xigua.model.Topo.VOLT;
import com.xigua.model.Topo.pon;
import com.xigua.model.Topo.slot;
import com.xigua.service.TopoService;
import com.xigua.service.UserMangeService;
import com.xigua.util.HttpRequestUtil;
@Service
public class TopoServiceImpl implements TopoService{
    @Autowired
    private ManageVirtualUsrDao dao ;
    
    private String Ipaddress = ControllerSettings.ip;
    private static final Logger LOG = LoggerFactory.getLogger(TopoServiceImpl.class);
    
    @Override
    public List<TopoInfo> getInfo(String username) {
        // TODO Auto-generated method stub
        List<TopoInfo> result = new ArrayList<TopoInfo>();
        List<String> oltList = getAllOltId();
        //获取该用户下的设备列表
        List<ManageVirtualUsr> list = dao.getVirtualList(username);
        LOG.info(""+list.size());
        for(String oltId : oltList){
            TopoInfo topo = new TopoInfo();
//            String oltId = "zte";
            String url =Ipaddress+"/restconf/config/network-topology:network-topology"
                    + "/topology/topology-netconf/node/"+oltId+"/yang-ext:mount/"
                            + "zxr10-pm-lr:configuration/virtual-network-device";
            String jsonResult = HttpRequestUtil.Get(url);
            JSONObject object = JSON.parseObject(jsonResult).getJSONObject("virtual-network-device");
            JSONArray virtualDeviceList = JSON.parseArray(object.
                    get("virtual-network-device-config-vndx").toString());
            List<VOLT> voltList = new ArrayList<VOLT>();
            for(int i=0; i < virtualDeviceList.size() ; i++){
                JSONObject deviceJSON = JSON.parseObject(virtualDeviceList.get(i)+"");
                LOG.info("收到JSON为:"+deviceJSON.toJSONString());
                VOLT volt = new VOLT();
                List<slot> slotList = new ArrayList<slot>();
                List<PortInfo> ponList = new ArrayList<PortInfo>();
                volt.setId(deviceJSON.getString("vndx-name"));
                JSONArray assign_interface = deviceJSON.getJSONArray("assign-interface");
                for(int j=0; j<assign_interface.size() ; j++){
                    slot slotOBJ =new slot();
                    PortInfo port = new PortInfo();
                    String interfaceName = assign_interface.
                            getJSONObject(j).get("interface-name").toString();
//                    String type = interfaceName.substring(0, interfaceName.indexOf("-"));
                    String slot =interfaceName.substring(interfaceName.indexOf("/")+1, interfaceName.lastIndexOf("/"));
                    String portNo = interfaceName.substring(interfaceName.lastIndexOf("/")+1, interfaceName.length()); 
                    String slotId = "槽"+slot;
                    port.setInterfaceName(interfaceName);
                    port.setSlot(slotId);
                    port.setPortNum(portNo);
                    ponList.add(port);
                    slotOBJ.setId(slotId);
                    if(ifNotExistSlot(slotList,slotId))
                    slotList.add(slotOBJ);
                }
                slotList = dividePort(ponList,slotList);
                volt.setSlot(slotList);
                voltList.add(volt);
            }
            topo.setVolt(voltList);
            topo.setName(oltId);
            result.add(topo);
        }
        return result;
    }

    //获取OLTid的列表(1、从数据库获取;2、从控制器获取)
    static List<String> getAllOltId() {
        // TODO Auto-generated method stub
        List<String> oltList = new ArrayList<String>();
        oltList.add("zte");
        return oltList;
    }
    
  //判断list里面是否有找个槽位
    static boolean ifNotExistSlot(List<slot> slotList , String slotId){
        boolean ifNotHave = true;
        for (slot slot : slotList) {  
            String slot_ID = slot.getId();
            if(slot_ID.equals(slotId)){
                ifNotHave = false;
            }
        }
        return ifNotHave;
    }
    //将PON分配到相应的槽位下
    static List<slot> dividePort(List<PortInfo> ponList,List<slot> slotList){
        for(slot slot : slotList){
            List<pon> ponSave = new ArrayList<pon>();
            for(PortInfo port : ponList){
                if(port.getSlot().equals(slot.getId())){
                    pon pon = new pon();
                    pon.setId(port.getInterfaceName());
                    ponSave.add(pon);
                }
            }
            slot.setPon(ponSave);
        }
        return slotList;
    }

}
