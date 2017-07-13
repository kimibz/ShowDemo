package com.xigua.model;

import java.io.Serializable;

public class SpawnInfo implements Serializable{

    /**
     * 2017-7-12 XIGUA
     */
    private static final long serialVersionUID = -5381864842193605781L;
    public SpawnInfo(){
        
    }
    /*
     * "netconf-node-topology:host"
     */
    private String address;
    /*
     * netconf-node-topology:keepalive-delay
     */
    private String aliveTime;
    /*
     * host-tracker-service:id
     */
    private String host_id;
    /*
     * node-id
     */
    private String node_id;
    /*
     * netconf-node-topology:tcp-only
     */
    private String tcp;
    /*
     * netconf-node-topology:username
     */
    private String username;
    /*
     * netconf-node-topology:password
     */
    private String password;
    /*
     * netconf-node-topology:port
     */
    private String port;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAliveTime() {
        return aliveTime;
    }
    public void setAliveTime(String aliveTime) {
        this.aliveTime = aliveTime;
    }
    public String getHost_id() {
        return host_id;
    }
    public void setHost_id(String host_id) {
        this.host_id = host_id;
    }
    public String getNode_id() {
        return node_id;
    }
    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }
    public String getTcp() {
        return tcp;
    }
    public void setTcp(String tcp) {
        this.tcp = tcp;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    
}
