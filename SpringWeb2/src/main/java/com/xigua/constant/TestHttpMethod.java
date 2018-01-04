package com.xigua.constant;

import com.xigua.serviceImp.NetconfServiceImp;

public class TestHttpMethod {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        NetconfServiceImp impl = new NetconfServiceImp();
        impl.spawnNewDevice(17830, 18230);
        //impl.deleteDevice(18030, 18330);
    }

}
