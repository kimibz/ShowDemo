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

import com.xigua.model.Topo.TopoInfo;
import com.xigua.service.TopoService;

@Controller
public class RestTopoController {

    @Autowired
    private TopoService service;
    
    private static final Logger LOG = LoggerFactory.getLogger(RestTopoController.class);
    
    /*
     * 返回拓扑信息
     */
    @RequestMapping(value = "/rest/getTopoInfo/{username}.json", method = RequestMethod.GET)
    @ResponseBody
    public List<TopoInfo> getTopoInfo(@PathVariable String username){
        List<TopoInfo> list = new ArrayList<TopoInfo>();
        list = service.getInfo(username);
        LOG.info("有"+list.size()+"台OLT");
        return list;
    }
}
