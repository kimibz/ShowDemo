package com.xigua.constant;

public class TestMethod {
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        String vndName = "gpon/olt/1/1/2";
        if(vndName.contains("gpon/olt")){
            vndName = vndName.replaceAll("gpon/","");
            System.out.println("123");
        }
        System.out.println(vndName);
    }
} 
