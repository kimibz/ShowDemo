package com.xigua.serviceImp;

import java.util.ArrayList;
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
import com.xigua.model.PortInfo;
import com.xigua.model.Topo.Onu;
import com.xigua.model.Topo.TopoInfo;
import com.xigua.model.Topo.VOLT;
import com.xigua.model.Topo.pon;
import com.xigua.model.Topo.slot;
import com.xigua.service.TopoService;
import com.xigua.service.virtualDeviceService;
import com.xigua.util.HttpRequestUtil;
import com.xigua.util.Util;
@Service
public class TopoServiceImpl implements TopoService{
    @Autowired
    private ManageVirtualUsrDao dao ;
    @Autowired
    private virtualDeviceService service;
    
    private String Ipaddress = ControllerSettings.ip;
    private static final Logger LOG = LoggerFactory.getLogger(TopoServiceImpl.class);
    
    @Override
    public List<TopoInfo> getInfo(String username) {
        // TODO Auto-generated method stub
        List<TopoInfo> result = new ArrayList<TopoInfo>();
        List<String> oltList = getAllOltId();
        List<ManageVirtualUsr> list = new ArrayList<ManageVirtualUsr>();
        //获取该用户下的设备列表
        //用户为ADMIN
        if(username.equals("admin")) {
            ManageVirtualUsr usr = new ManageVirtualUsr();
            usr.setOltId("zte");//现在只有zte一台设备所以list里只有zte
            list.add(usr);
            LOG.info(""+list.size());
            for(String oltId : oltList){
                TopoInfo topo = new TopoInfo();
//                String oltId = "zte";
                String url =Ipaddress+"/restconf/config/network-topology:network-topology"
                        + "/topology/topology-netconf/node/"+oltId+"/yang-ext:mount/"
                                + "zxr10-pm-lr:configuration/virtual-network-device";
                String jsonResult = HttpRequestUtil.Get(url);
                LOG.info(jsonResult);
                if(jsonResult == null) {
                    topo.setName(oltId);
                }
                else {
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
//                        String type = interfaceName.substring(0, interfaceName.indexOf("-"));
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
                        //根据slotId正序排列slotList
                        Collections.sort(slotList,new Comparator<slot>(){
                            public int compare(slot arg0, slot arg1) {
                                int hits0 = Integer.parseInt(arg0.getId().substring(1));  
                                int hits1 = Integer.parseInt(arg1.getId().substring(1));  
                                if (hits1 > hits0) {  
                                    return -1;  
                                } else if (hits1 == hits0) {  
                                    return 0;  
                                } else {  
                                    return 1;  
                                } 
                            }
                        });
                        volt.setSlot(slotList);
                        voltList.add(volt);
                    }
                    topo.setVolt(voltList);
                    topo.setName(oltId);
                }
                result.add(topo);
            }
        }
        //非ADMIN用户
        //name 为控制器   volt对应 olt 逻辑按照下面的来
        else {
            TopoInfo topo = new TopoInfo();
            list = dao.getVirtualList(username);
            List<VOLT> voltList = new ArrayList<VOLT>();
            //寻找用户下的一个虚拟OLT设备
            for(ManageVirtualUsr usr : list) {
                List<Port> portList = new ArrayList<Port>();
                VOLT volt = new VOLT();
                List<slot> slotList = new ArrayList<slot>();
                List<PortInfo> ponList = new ArrayList<PortInfo>();
                volt.setId(usr.getVirtualName());
                String vDeviceName = "vDevice_" + usr.getOltId() +"_"+usr.getVirtualName();
                portList = service.getInterfaceList(vDeviceName);
                for(Port port : portList) {
                    slot slotOBJ =new slot();
                    PortInfo portInfo = new PortInfo();
                    String slot = port.getSlot()+"";
                    String portNo = port.getPortno()+"";
                    String slotId = "槽"+slot;
                    portInfo.setInterfaceName(port.getPortname());
                    portInfo.setSlot(slotId);
                    portInfo.setPortNum(portNo);
                    ponList.add(portInfo);
                    slotOBJ.setId(slotId);
                    if(ifNotExistSlot(slotList,slotId))
                        slotList.add(slotOBJ);
                }
                slotList = dividePort(ponList,slotList);
                //ONU
                slotList = giveOnu(vDeviceName, slotList);
                //根据slotId正序排列slotList
                Collections.sort(slotList,new Comparator<slot>(){
                    public int compare(slot arg0, slot arg1) {
                        int hits0 = Integer.parseInt(arg0.getId().substring(1));  
                        int hits1 = Integer.parseInt(arg1.getId().substring(1));  
                        if (hits1 > hits0) {    
                            return -1;  
                        } else if (hits1 == hits0) {  
                            return 0;  
                        } else {  
                            return 1;  
                        } 
                    }
                });
                volt.setSlot(slotList);
                voltList.add(volt);
            }
            topo.setVolt(voltList);
            topo.setName("控制器-controller");//我把oltId假设成为控制器
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
                    if(slot.getId().equals("槽17")){
                        pon.setId("上联"+port.getPortNum());
                    }else {
                        pon.setId("端口"+port.getPortNum());
                    }
                    List<Onu> onuSave = new ArrayList<Onu>();
                    pon.setOnu(onuSave);
                    ponSave.add(pon);
                }
            }
            slot.setPon(ponSave);
        }
        return slotList;
    }
    //将onu分配岛相应的PON口下
    static List<slot> giveOnu(String vDeviceName,List<slot> slotList){
        String url = "http://localhost:8181/restconf/"
                + "operational/network-topology:network-topology/"
                + "topology/topology-netconf/node/" + vDeviceName
                + "/yang-ext:mount/zxr10-test:state";
        String result = HttpRequestUtil.Get(url);
        System.out.println(result);
        if(result != null) {
            String JSON_ARRAY_STR = JSON.parseObject(result).
                    getJSONObject("state").getJSONArray("onuStatus").toJSONString();
//            JSONArray arr = JSON.parseObject(result).getJSONObject("state")
//                    .getJSONArray("onuStatus");
            ArrayList<Onu> onuList = JSON.parseObject(JSON_ARRAY_STR, new TypeReference<ArrayList<Onu>>() {});
            for(Onu onu : onuList) {
                int ifIndex = onu.getIfIndex();
                String interfaceName = ""+Util.ifIndexToInterface(""+ifIndex);
                String shelf = interfaceName.substring(interfaceName.indexOf("/")+1, interfaceName.lastIndexOf("/")); ;
                String pon = interfaceName.substring(interfaceName.lastIndexOf("/")+1, interfaceName.length());
                for(slot slot : slotList) {
                    if(shelf.equals(slot.getId().substring(1))) {
                        List<pon> ponList = slot.getPon();
                        for(pon ponObject : ponList) {
                            if(pon.equals(ponObject.getId().substring(2))) {
                                List<Onu> onuSave = ponObject.getOnu();
                                onuSave.add(onu);
                                ponObject.setOnu(onuSave);
                            }
                        }
                    }
                }
            }
        }
        return slotList;
        
    }

}
