package com.xigua.constant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xigua.model.SysConfig;
import com.xigua.util.HttpRequestUtil;

public class TestHttpMethod {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String url = "http://localhost:8181/restconf/config/"
                + "opendaylight-inventory:nodes/node/"
                + 17830 +"/yang-ext:mount/zxr10-pm-sys:configuration/sys/";
        String result = HttpRequestUtil.Get(url);
        JSONObject object = JSON.parseObject(result);
        JSONObject sys = (JSONObject) object.get("sys");
        SysConfig config  = new SysConfig();
        config.setContact(sys.getString("contact"));
        config.setLocation(sys.getString("location"));
        config.setCpuIisolate(sys.getString("cpu-isolate"));
        config.setHostname(sys.getString("hostname"));
        config.setLoadMmode(sys.getString("load-mode"));
        System.out.println(config.getContact());
    }

}
