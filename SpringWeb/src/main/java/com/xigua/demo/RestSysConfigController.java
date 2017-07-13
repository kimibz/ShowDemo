package com.xigua.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xigua.JSONTemplate.JSONTemplate;
import com.xigua.model.SpawnInfo;
import com.xigua.model.SysConfig;
import com.xigua.service.SysInfoService;

@Controller
public class RestSysConfigController {
    @Autowired
    private SysInfoService service;
    
    /*
     * 返回Sys config(contact,hostname,location,load-mode,cpu-isolate)
     */
    @RequestMapping(value = "/rest/deviceSysInfo/{device}.json", method = RequestMethod.GET)
    @ResponseBody
    public SysConfig getSysConfig(@PathVariable String device){
        SysConfig config = new SysConfig();
        config = service.getConfig(device);
        return config;
    }
    /** spawn NewDevice */
    @RequestMapping(value="/rest/spawnNewDevice.json", method=RequestMethod.POST)
    @ResponseBody
    public void spawnNewDevice(@RequestBody SpawnInfo info) {
//        System.out.println(info.getAddress());
        service.SpawnNewDevice(info);
    }
}
