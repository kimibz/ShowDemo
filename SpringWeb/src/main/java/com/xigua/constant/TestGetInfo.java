package com.xigua.constant;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class TestGetInfo {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String userName = "admin";
        String password = "admin";
        Boolean ifConnected = false ;
        String url = "http://localhost:8181/restconf/config/opendaylight-inventory:nodes/"
                + "node/17830/yang-ext:mount/zxr10-pm-sys:configuration/";
        // 创建HttpClient实例
        // get请求返回结果  
        CloseableHttpClient client = HttpClients.createDefault();  
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        UsernamePasswordCredentials usernamePassword = new UsernamePasswordCredentials(
                userName, password);
        credsProvider.setCredentials(AuthScope.ANY, usernamePassword);
        //HttpClient上下文,保存用户名信息  
        HttpClientContext context = HttpClientContext.create(); 
        context.setCredentialsProvider(credsProvider); 
        // 发送get请求  
        HttpGet request = new HttpGet(url);  
        // 设置请求和传输超时时间  
        RequestConfig requestConfig = RequestConfig.custom()  
                .setSocketTimeout(2000).setConnectTimeout(2000).build();  
        request.setConfig(requestConfig); 
        try {  
            CloseableHttpResponse response = client.execute(request,context);  
            //请求发送成功，并得到响应  
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                //读取服务器返回过来的json字符串数据  
                HttpEntity entity = response.getEntity();  
                String strResult = EntityUtils.toString(entity, "utf-8");  
                //把json字符串转换成json对象  
                JSONObject object = JSON.parseObject(strResult);
                JSONObject sys = (JSONObject) object.get("configuration");
                JSONObject sys2 = (JSONObject) sys.get("sys");
                System.out.println(sys2.get("cpu-isolate"));
                System.out.println(strResult);
            }
        } catch (IOException e) {  
            System.err.print("get请求提交失败:" + url + e);  
        } finally {  
            request.releaseConnection();  
        }  
    }

}
