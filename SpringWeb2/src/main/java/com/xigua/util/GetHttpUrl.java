package com.xigua.util;
/*
 * 与控制器解接口，未来有可能是多控制器所以需要可变化
 */
public class GetHttpUrl {
    private static String hostAddress = "http://localhost:8181/";
    
    public String getPortUrl(String oltId){
        String url ="http://localhost:8181/restconf/operational/network-"
                + "topology:network-topology/topology/topology-netconf/node/"
                +oltId+"/yang-ext:mount/zxr10-pm-sys:state/sys/ports";
        return url;
    }
    
}
