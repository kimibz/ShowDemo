package com.xigua.TimeJob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xigua.service.vlanService;

public class TestTimer {
    @Autowired
    private vlanService vlanService;
    private static final Logger LOG = LoggerFactory.getLogger(TestTimer.class);
    
    /**  
     * 业务逻辑定时处理  
     */  
    public void execute() {  
        // 执行业务逻辑  
        // ........  
        //LOG.info("定时任务.......");//为什么打不出LOG
        //System.out.println("定时任务");
        vlanService.saveStats("vDevice_zte_vnd001");
    } 

}
