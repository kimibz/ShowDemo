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

import com.xigua.model.ManageVirtualUsr;
import com.xigua.model.PortHistoryModel;
import com.xigua.model.rateStats;
import com.xigua.model.usrOLTManageModel;
import com.xigua.model.vlanEdit;
import com.xigua.service.UserMangeService;
import com.xigua.service.vlanService;
@Controller
public class RestUserManageController {
    @Autowired
    private UserMangeService service ; 
    @Autowired
    private vlanService vlanService;
    private static final Logger LOG = LoggerFactory.getLogger(RestUserManageController.class);
    
    
    /*
     * 返回该用户下设备及状态
     */
    @RequestMapping(value = "/rest/showDeviceByUser/{username}.json", method = RequestMethod.GET)
    @ResponseBody
    public List<ManageVirtualUsr> getVirtualList(@PathVariable String username){
        List<ManageVirtualUsr> list = new ArrayList<ManageVirtualUsr>();
        list = service.getAllDevice(username);
        return list;
    }
    /*
     * 返回该用户所选的虚拟设备的状态信息
     */
    @RequestMapping(value = "/rest/oltUsrManagement/{oltId}.json", method = RequestMethod.GET)
    @ResponseBody
    public  usrOLTManageModel getDeviceInfo(@PathVariable String oltId){
        usrOLTManageModel model = service.getMangeInfo(oltId);
        return model;
    }
    /*
     * 修改VLAN
     */
    @RequestMapping(value = "/rest/{vndName}/{interfaceName}.json", method = RequestMethod.PUT)
    public void editVlan(@PathVariable String vndName,@PathVariable String interfaceName,@RequestBody String vlan){
        String interfaceFormat = interfaceName.replaceAll("_", "/");
        vlanService.editVlan(vndName, interfaceFormat,vlan);
    }
    /*
     * 获取VLAN
     */
    @RequestMapping(value = "/rest/vlan/{vndName}/{interfaceName}.json", method = RequestMethod.GET)
    @ResponseBody
    public vlanEdit getVlan(@PathVariable String vndName,@PathVariable String interfaceName){
        String interfaceFormat = interfaceName.replaceAll("_", "/");
        vlanEdit edit = vlanService.getVlan(vndName, interfaceFormat);
        return edit;
    }
    /*
     * 获取性能统计数据
     */
    @RequestMapping(value = "/rest/PortStats/{vndName}/{interfaceName}.json", method = RequestMethod.GET)
    @ResponseBody
    public rateStats getStats(@PathVariable String vndName,@PathVariable String interfaceName){
        String interfaceFormat = interfaceName.replaceAll("_", "/");
        rateStats stats = vlanService.getStats(vndName, interfaceFormat);
        return stats;
    }
    /*
     * 存端口性能数据
     */
    @RequestMapping(value = "/rest/PortStatsHistory/{vndName}.json", method = RequestMethod.GET)
    public void saveStats(@PathVariable String vndName){
        vlanService.saveStats("vDevice_zte_vnd001");
    }
    /*
     * 返回端口历史性能数据
     */
    @RequestMapping(value = "/rest/history/{oltId}/{vndName}.json", method = RequestMethod.GET)
    @ResponseBody
    public List<PortHistoryModel> getHistoryStats(@PathVariable String oltId,@PathVariable String vndName){
        List<PortHistoryModel> list =vlanService.getHistoryList(oltId, vndName);
        return list;
    }
}
