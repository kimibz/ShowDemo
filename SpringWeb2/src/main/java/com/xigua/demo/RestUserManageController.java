package com.xigua.demo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xigua.model.ManageVirtualUsr;
import com.xigua.model.usrOLTManageModel;
import com.xigua.service.UserMangeService;
@Controller
public class RestUserManageController {
    @Autowired
    private UserMangeService service ; 
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
}
