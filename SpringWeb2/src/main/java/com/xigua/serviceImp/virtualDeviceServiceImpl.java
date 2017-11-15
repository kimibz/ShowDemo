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
import com.xigua.JSONTemplate.ControllerSettings;
import com.xigua.JSONTemplate.JSONTemplate;
import com.xigua.dao.ManageVirtualUsrDao;
import com.xigua.model.Port;
import com.xigua.model.PortInfo;
import com.xigua.model.SpawnInfo;
import com.xigua.model.SpawnNewVirDevice;
import com.xigua.model.virtualDevice;
import com.xigua.model.virtualDeviceInfo;
import com.xigua.model.Topo.slot;
import com.xigua.service.virtualDeviceService;
import com.xigua.util.HttpRequestUtil;

@Service
public class virtualDeviceServiceImpl implements virtualDeviceService{
    
    private static final Logger LOG = LoggerFactory.getLogger(virtualDeviceServiceImpl.class);
    @Autowired
    private ManageVirtualUsrDao manageDao;
    private String Ipaddress = ControllerSettings.ip;
            
    @Override
    public List<virtualDevice> getVirtualDeviceList(String oltId) {
        // TODO Auto-generated method stub
        String url =Ipaddress+"/restconf/operational/network-topology:network-topology/"
                + "topology/topology-netconf/node/"+oltId
                + "/yang-ext:mount/zxr10-pm-lr:state/virtual-network-device/show-virtual-network-device";
        String strResult = HttpRequestUtil.Get(url);
        List<virtualDevice> list = new ArrayList<virtualDevice>();
        JSONObject object = JSON.parseObject(strResult);
        JSONObject showDevice = object.getJSONObject("show-virtual-network-device");
        JSONArray deviceList = JSON.parseArray(showDevice.
                get("show-virtual-network-device-list").toString());
        //获取nodes分支
        System.out.println(deviceList);
        for(int i=0; i < deviceList.size() ; i++){
            JSONObject deviceJSON = JSON.parseObject(deviceList.get(i)+"");
            virtualDevice virDevice = new virtualDevice();
            List<String> assign = new ArrayList<String>();
            String vnd_name = deviceJSON.get("vnd-name").toString();
            //vnd0是管理切片不用显示
            if(!vnd_name.equals("vnd0")){
                virDevice.setVnd_id(Integer.valueOf(deviceJSON.get("vnd-id").toString()));
                virDevice.setVnd_name(vnd_name);
                virDevice.setVnd_status(deviceJSON.get("vnd-status").toString());
                //查表找到虚拟切片所属用户
                String belongTo = manageDao.getVirtualUsr(oltId, vnd_name);
                virDevice.setBelongTo(belongTo);
                list.add(virDevice);
                LOG.info(virDevice.getVnd_id()+":"+virDevice.getVnd_status());
            }
        }
       //根据vnd_id正序排列list
        Collections.sort(list,new Comparator<virtualDevice>(){
            public int compare(virtualDevice arg0, virtualDevice arg1) {
                int hits0 = arg0.getVnd_id();  
                int hits1 = arg1.getVnd_id();  
                if (hits1 > hits0) {  
                    return -1;  
                } else if (hits1 == hits0) {  
                    return 0;  
                } else {  
                    return 1;  
                } 
            }
        });
        return list;
    }

    @Override
    public virtualDeviceInfo getInfo(String oltId,String vndName) {
        // TODO Auto-generated method stub
        virtualDeviceInfo info = new virtualDeviceInfo();
        String url =Ipaddress+"/restconf/config/network-topology:"
                + "network-topology/topology/topology-netconf/node/"
                + oltId +"/yang-ext:mount"
                + "/zxr10-pm-lr:configuration/virtual-network-device/"
                + "virtual-network-device-config-vndx/"+vndName;
        String jsonStr = HttpRequestUtil.Get(url);
        JSONObject object = JSON.parseObject(jsonStr);
        JSONArray virtualDeviceList = JSON.parseArray(object.
                get("virtual-network-device-config-vndx").toString());
        JSONObject virtualDevice = JSON.parseObject(virtualDeviceList.get(0)+"");
        if(jsonStr.contains("vndx-attribute")){
            String status = virtualDevice.getJSONObject("vndx-attribute").getString("status");
            String description = virtualDevice.getJSONObject("vndx-attribute").getString("description");
            if(description == null){
                description = "无描述";
            }
            info.setDescription(description);
            info.setStatus(status);
        }
        if(jsonStr.contains("deploy-mpu")){
            List<String> mpuList = new ArrayList<String>();
            JSONArray deploy_mpu = virtualDevice.getJSONArray("deploy-mpu");
            for(int i=0 ; i<deploy_mpu.size() ; i++){
                mpuList.add(deploy_mpu.getJSONObject(i).get("mpu").toString());
            }
            info.setDeploy_mpu(mpuList);
        }
        if(jsonStr.contains("assign-interface")){
            List<PortInfo> portList = new ArrayList<PortInfo>();
            JSONArray assign_interface = virtualDevice.getJSONArray("assign-interface");
            LOG.info(assign_interface.toJSONString());
            for(int j=0; j<assign_interface.size() ; j++){
                String interfaceName = assign_interface.
                        getJSONObject(j).get("interface-name").toString();
                PortInfo port = new PortInfo();
//                String type = interfaceName.substring(0, interfaceName.indexOf("-"));
                String shelf =interfaceName.substring(interfaceName.indexOf("-")+1, interfaceName.indexOf("/"));
                String slot =interfaceName.substring(interfaceName.indexOf("/")+1, interfaceName.lastIndexOf("/"));
                if(slot.equals("17")) {
                    port.setSpeed("10000");
                    port.setType("上联口");
                }else {
                    port.setSpeed("0");
                    port.setType("PON口");
                }
                if(interfaceName.substring(0,5).equals("vport")) {
                    port.setType("vPort");
                }
                String portNo = interfaceName.substring(interfaceName.lastIndexOf("/")+1, interfaceName.length());
                port.setPortNum(portNo);
                port.setShelf(shelf);
                port.setSlot(slot);
                port.setInterfaceName(interfaceName);
                portList.add(port);
            }
            info.setVndName(vndName);
           //根据shelf&&portNumber正序排列portList
            Collections.sort(portList,new Comparator<PortInfo>(){
                public int compare(PortInfo arg0, PortInfo arg1) {
                    int hits0 = Integer.parseInt(arg0.getSlot());  
                    int hits1 = Integer.parseInt(arg1.getSlot());
                    String poNum1 = arg0.getPortNum();
                    String poNum2 = arg1.getPortNum();
                    if(poNum1.contains(":")) {
                        poNum1 = poNum1.substring(0,1);
                    }
                    if(poNum2.contains(":")) {
                        poNum2 = poNum2.substring(0,1);
                    }
                    int portNo0 = Integer.parseInt(poNum1);
                    int portNo1 = Integer.parseInt(poNum2);
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
            info.setAssigned_interface(portList);
        }
        return info;
    }

    @Override
    public void deleteVirtual(String oltId,String vndName) {
        // TODO Auto-generated method stub
        LOG.info(vndName);
        String url = Ipaddress+"/restconf/config/"
                + "network-topology:network-topology/topology/topology-netconf"
                + "/node/"+oltId
                + "/yang-ext:mount/zxr10-pm-lr:configuration/virtual-network-device/virtual-network-device-config-vndx/"
                + vndName;
        HttpRequestUtil.Delete(url);
        //删除数据库数据
        manageDao.deleteVirtualByUsr(manageDao.getId(oltId, vndName));
        //删除控制器数据
        String cUrl = Ipaddress + "/restconf/config/network-topology:network-topology/"
                + "topology/topology-netconf/node/";
        String nodeId =  "vDevice_" + oltId +"_"+vndName;
        LOG.info(nodeId);
        HttpRequestUtil.Delete(cUrl+nodeId);
    }

    @Override
    public List<Port> getInterfaceList(String oltId) {
        // TODO Auto-generated method stub
        String url = Ipaddress+"/restconf/operational/network-topology:network-topology"
                + "/topology/topology-netconf/node/"+oltId+"/yang-ext:mount/"
                        + "zxr10-pm-sys:state/sys/ports";
        String jsonResult = HttpRequestUtil.Get(url);
        LOG.info(jsonResult);
        JSONObject Object = JSON.parseObject(jsonResult).getJSONObject("ports");
        JSONArray portArray = Object.getJSONArray("port");
        System.out.println(portArray.toJSONString());
        List<Port> port = JSON.parseArray(portArray.toJSONString(), Port.class);
        //根据shelf&&portNumber正序排列portList
        Collections.sort(port,new Comparator<Port>(){
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
        return port;
    }

    @Override
    public void spawnNewSlice(String oltId,SpawnNewVirDevice info) {
        // TODO Auto-generated method stub
        String VirJson = JSONTemplate.SpawnNewVirtualJson;
        String AssignInterface =JSONTemplate.AssignInterface;
        String deployMpu = JSONTemplate.deployMpu;
        JSONObject OBJ = JSON.parseObject(VirJson);
        JSONArray arr = OBJ.getJSONArray("zxr10-pm-lr:virtual-network-device-config-vndx");
        JSONObject VirDevice = arr.getJSONObject(0);
        VirDevice.put("zxr10-pm-lr:vndx-name",info.getVnd_name());
        JSONObject VirDeviceAttr = VirDevice.getJSONObject("zxr10-pm-lr:vndx-attribute");
        VirDeviceAttr.put("zxr10-pm-lr:description", info.getDescription());
        VirDeviceAttr.put("zxr10-pm-lr:status", info.getVnd_status());
        JSONArray VirResourceList = VirDevice.getJSONArray("zxr10-pm-lr:assign-interface");
        String[] assignInterface = info.getAssign_interface();
        if(assignInterface != null){
            for(int i=0; i<assignInterface.length ; i++){
                JSONObject InterfaceObj = JSON.parseObject(AssignInterface);
                InterfaceObj.put("zxr10-pm-lr:interface-name",assignInterface[i]);
                VirResourceList.add(InterfaceObj);
            }
        }
        JSONArray VirMPUList = VirDevice.getJSONArray("zxr10-pm-lr:deploy-mpu");
        String[] mpuList = info.getDepoly_mpu();
        if(mpuList != null){
            for(int i=0; i<mpuList.length ; i++){
                JSONObject MpuObj = JSON.parseObject(deployMpu);
                MpuObj.put("zxr10-pm-lr:mpu", mpuList[i]);
                VirMPUList.add(MpuObj);
            }
        }
        LOG.info(OBJ.toJSONString());
        //将切片以及用户信息存入数据库
        String user = info.getBelongTo();
        String vndName = info.getVnd_name();
        manageDao.SetVirtualToUser(oltId, vndName, user);
        String url = Ipaddress+"/restconf/config/"
                + "network-topology:network-topology/topology/topology-netconf/node/"
                + oltId +"/yang-ext:mount/zxr10-pm-lr:configuration/virtual-network-device";
        HttpRequestUtil.Post(url, OBJ.toJSONString());
       //向控制器发送建立连接请求
        spawnNewConnect(oltId,info.getVnd_name());
    }

    @Override
    public void deletePONinVirtual(String oltId, String vndName, String interfaceName) {
        // TODO Auto-generated method stub
        String url =Ipaddress+"/restconf/config/network-topology:network-topology/"
                + "topology/topology-netconf/node/"+oltId+"/yang-ext:mount/zxr10-pm-lr:configuration/"
                        + "virtual-network-device/virtual-network-device-config-vndx/"
                        +vndName+ "/assign-interface/"+interfaceName;
        LOG.info(url);
        HttpRequestUtil.Delete(url);
    }

    @Override
    public void changeStatus(String oltId, String vndName, String statusChange) {
        // TODO Auto-generated method stub
        String url =Ipaddress+"/restconf/config/network-topology:network-topology/topology"
                + "/topology-netconf/node/"+oltId+"/yang-ext:mount/zxr10-pm-lr:configuration/"
                        + "virtual-network-device/virtual-network-device-config-vndx/"
                        + vndName+"/vndx-attribute";
        String Vnd_Attribute = JSONTemplate.Vnd_Attribute;
        JSONObject OBJ = JSON.parseObject(Vnd_Attribute);
        JSONObject status =OBJ.getJSONObject("zxr10-pm-lr:vndx-attribute");
        if(statusChange.equals("1")){
            status.put("zxr10-pm-lr:status","enable");
        }else{
            status.put("zxr10-pm-lr:status","disable");
        }
        //entity有点问题
        LOG.info(OBJ.toJSONString());
        HttpRequestUtil.Put(url, OBJ.toJSONString());
    }

    @Override
    public void addResource(String oltId,String vndName, String[] PortName) {
        // TODO Auto-generated method stub
        String url = Ipaddress +"/restconf/config/network-topology:network-topology/topology/"
                + "topology-netconf/node/"+oltId+"/yang-ext:mount/zxr10-pm-lr:configuration/"
                        + "virtual-network-device/virtual-network-device-config-vndx/"+vndName;
        String AddResouces = JSONTemplate.Vnd_addResources;
        String AssignInterface =JSONTemplate.AssignInterface;
        JSONObject OBJ = JSON.parseObject(AddResouces);
        JSONArray arr = OBJ.getJSONArray("zxr10-pm-lr:virtual-network-device-config-vndx");
        JSONObject VirDevice = arr.getJSONObject(0);
        VirDevice.put("zxr10-pm-lr:vndx-name",vndName);
        JSONArray VirResourceList = VirDevice.getJSONArray("zxr10-pm-lr:assign-interface");
        for(int i=0; i<PortName.length ; i++){
            JSONObject InterfaceObj = JSON.parseObject(AssignInterface);
            InterfaceObj.put("zxr10-pm-lr:interface-name",PortName[i]);
            VirResourceList.add(InterfaceObj);
        }
        String entity = OBJ.toString();
        LOG.info("添加切片端口资源:"+OBJ.toJSONString());
        HttpRequestUtil.Put(url, entity);
    }

    @Override
    public List<String> getCpuList(String oltId) {
        // TODO Auto-generated method stub
        String url = Ipaddress+"/restconf/operational/network-topology:network-topology"
                + "/topology/topology-netconf/node/"+oltId+"/yang-ext:mount/zxr10-pm-sys:state/sys/cpus";
        String jsonStr = HttpRequestUtil.Get(url);
        JSONObject obj = JSON.parseObject(jsonStr);
        JSONArray arr = obj.getJSONObject("cpus").getJSONArray("cpu");
        List<String> cpu = new ArrayList<String>();
        for(int i=0; i<arr.size() ; i++) {
            cpu.add(arr.getJSONObject(i).getString("cpuname"));
        }
        return cpu;
    }
    /*
     * nodeId = vDevice_{oltId}_{vndName}
     * 与控制器建立新的连接
     */
    void spawnNewConnect(String oltId,String vndName) {
        String url = Ipaddress+"/restconf/config/network-topology:network-topology/"
                + "topology/topology-netconf/node/";
        String device_name = "vDevice_" + oltId +"_"+vndName;
        String e_url = url + device_name;
        String deviceJson = JSONTemplate.SpawnDeviceJSON;
        JSONObject object = JSON.parseObject(deviceJson);
        JSONArray arr =object.getJSONArray("node");
        JSONObject device_obj = JSON.parseObject(arr.get(0)+"");
        //添加新数据
        boolean ifGetIp = false;
        ifGetIp = ifGetIp("116.228.53.163");
        if(!ifGetIp) {
            device_obj.put("netconf-node-topology:host", "116.228.53.163");
        }else {
            device_obj.put("netconf-node-topology:host", "116.228.53.164");
        }
        device_obj.put("node-id", device_name);
        device_obj.put("password", "zte");
        device_obj.put("netconf-node-topology:username", "zte");
        device_obj.put("netconf-node-topology:port", 830);
        device_obj.put("netconf-node-topology:tcp-only", false);
        device_obj.put("netconf-node-topology:keepalive-delay", 0);
        device_obj.put("host-tracker-service:id", device_name);
        arr.clear();
        arr.add(0, device_obj);
        object.put("node", arr);
        String entity = object.toJSONString();
        HttpRequestUtil.Put(e_url, entity);
    }
    
    //判断是否有IP
    Boolean ifGetIp(String ip) {
        String url = "http://localhost:8181/restconf/operational/"
                + "network-topology:network-topology/topology/topology-netconf/";
        String result = HttpRequestUtil.Get(url);
        System.out.println(result);
        JSONArray arr = JSON.parseObject(result).getJSONArray("topology").
                getJSONObject(0).getJSONArray("node");
        boolean ifGetIp = false ;
        for(int i=0; i<arr.size() ; i++) {
            JSONObject obj = arr.getJSONObject(i);
            String ipGet = obj.get("netconf-node-topology:host").toString();
            if(ipGet.equals(ip)) {
                ifGetIp = true;
            }
        }
        return ifGetIp;
        
    }

}

