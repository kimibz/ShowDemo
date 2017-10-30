package com.xigua.constant;

import com.xigua.serviceImp.vlanServiceImpl;

public class TestMethod {
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        String vndName = "vDevice_zte_vnd001";
        vlanServiceImpl impl = new vlanServiceImpl();
        impl.saveStats(vndName);
    }
} 
