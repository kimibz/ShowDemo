package com.xigua.constant;

public class TestGetIndex {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String interfaceName ="xgei-1/17/9";
        String shelf =interfaceName.substring(interfaceName.indexOf("-")+1, interfaceName.indexOf("/"));
        String slot =interfaceName.substring(interfaceName.indexOf("/")+1, interfaceName.lastIndexOf("/"));
        String portNo = interfaceName.substring(interfaceName.lastIndexOf("/")+1, interfaceName.length());
        String hexShelf = Integer.toHexString(Integer.parseInt(shelf));
        String hexSlot = Integer.toHexString(Integer.parseInt(slot));
        String hexPortNo = Integer.toHexString(Integer.parseInt(portNo));
        if(hexShelf.length()<2) {
            hexShelf = "0" + hexShelf ;
        }
        if(hexSlot.length()<2) {
            hexSlot = "0" + hexSlot ;
        }
        if(hexPortNo.length()<2) {
            hexPortNo = "0" + hexPortNo ;
        }
        String hexResult = "11" + hexShelf + hexSlot + hexPortNo;
        int ifIndex = Integer.parseInt(hexResult,16);
        System.out.println(hexResult);
        System.out.println(ifIndex);
    }
}
