package com.xigua.demo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xigua.model.Port;
import com.xigua.model.PortAndUserInfo;
import com.xigua.model.SpawnNewVirDevice;
import com.xigua.model.virtualDevice;
import com.xigua.model.virtualDeviceInfo;
import com.xigua.service.UserService;
import com.xigua.service.virtualDeviceService;
import com.xigua.serviceImp.virtualDeviceServiceImpl;

@Controller
public class RestVirtualController {
    
    @Autowired
    private virtualDeviceService service;
    @Autowired
    private UserService UserService;
    
    private static final Logger LOG = LoggerFactory.getLogger(RestVirtualController.class);
    
    /*
     * 返回该OLT下的虚拟切片
     */
    @RequestMapping(value = "/rest/showVirtualDeviceList/{oltId}.json", method = RequestMethod.GET)
    @ResponseBody
    public List<virtualDevice> getVirtualList(@PathVariable String oltId){
        List<virtualDevice> list = new ArrayList<virtualDevice>();
        list = service.getVirtualDeviceList(oltId);
        return list;
    }
    /*
     * 返回该切片下的PON口信息等
     */
    @RequestMapping(value = "/rest/{oltId}/VirtualInfo/{vnd_name}.json", method = RequestMethod.GET)
    @ResponseBody
    public virtualDeviceInfo getVirtualInfo(@PathVariable String oltId,@PathVariable String vnd_name){
        virtualDeviceInfo info = new virtualDeviceInfo();
        info = service.getInfo(oltId,vnd_name);
        return info;
    }
    /*
     * 删除切片 
     */
    @RequestMapping(value = "/rest/{oltId}/VirtualInfo/{vnd_name}.json", method = RequestMethod.DELETE, produces = "text/plain;charset=UTF-8")
    public void deleteVirtualDevice(@PathVariable String oltId,@PathVariable String vnd_name){
        service.deleteVirtual(oltId,vnd_name);
    }
    /*
     * 返回该OLT下可用的PON口信息和控制器下用户等
     */
    @RequestMapping(value = "/rest/OLTResource/{oltId}.json", method = RequestMethod.GET)
    @ResponseBody
    public PortAndUserInfo getInterfaceList(@PathVariable String oltId){
        List<Port> interfaceList = service.getInterfaceList(oltId);
        List<String> userList = UserService.getAllUser(0);//获取role=0的用户
        List<String> portList = new ArrayList<String>();
        LOG.info(""+userList.size());
        for(Port port : interfaceList){
            portList.add(port.getPortname());
        }
        PortAndUserInfo info = new PortAndUserInfo();
        info.setPort(portList);
        info.setUser(userList);
        return info;
    }
    /** 在oltId下新增新的虚拟切片 */
    @RequestMapping(value="/rest/{oltId}/spawnNewVirtualDevice.json", method=RequestMethod.POST)
    public void spawnNewVirslice(@PathVariable String oltId,@RequestBody SpawnNewVirDevice virDevice) {
        service.spawnNewSlice(oltId, virDevice);
    }
    /*
     * 删除切片下的某个PON口 
     */
    @RequestMapping(value = "/rest/{oltId}/{vndName}/ponInVirtual.json", method = RequestMethod.DELETE, produces = "text/plain;charset=UTF-8")
    public void deletePON(@PathVariable String oltId,@PathVariable String vndName,@RequestBody String pon){
        String interfaceName = pon.replaceAll("\"", "");
        LOG.info(interfaceName);
        service.deletePONinVirtual(oltId, vndName, interfaceName);
    }
    /*
     * 改变某个切片的状态（必须配备MPU才能开启）
     */
    @RequestMapping(value = "/rest/{oltId}/changeStatus/{vnd_name}.json", method = RequestMethod.PUT)
    public void changeStatus(@PathVariable String oltId,@PathVariable String vnd_name,@RequestBody String statusChange){
        LOG.info("改变切片"+vnd_name+"的运行状态");
        service.changeStatus(oltId, vnd_name, statusChange);
    }
    /*
     * 为某个VND增加资源
     */
    @RequestMapping(value = "/rest/{oltId}/addVndResource/{vndName}.json", method = RequestMethod.PUT)
    public void addResouces(@PathVariable String oltId,@PathVariable String vndName,@RequestBody String[] assign_interface){
        service.addResource(oltId, vndName, assign_interface);
    }
}
