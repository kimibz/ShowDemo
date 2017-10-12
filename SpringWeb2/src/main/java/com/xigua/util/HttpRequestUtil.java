package com.xigua.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xigua.serviceImp.virtualDeviceServiceImpl;

public class HttpRequestUtil {
    private static final Logger LOG = LoggerFactory.getLogger(HttpRequestUtil.class);
    public static String Get(String url){
        String userName = "admin";
        String password = "admin";
        String result = null;
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
            LOG.info(response.toString());
            //请求发送成功，并得到响应  
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                //读取服务器返回过来的json字符串数据  
                HttpEntity entity = response.getEntity();  
                result = EntityUtils.toString(entity, "utf-8");  
//                JSONObject object = JSON.parseObject(result);
                //把json字符串转换成json对象  
//                System.out.println(object); 
            } else {  
                LOG.error("GET失败");  
            }  
        } catch (IOException e) {  
            LOG.error("GET失败"+url+e);
        } finally {  
            request.releaseConnection();  
        }  
        return result;
    }
    
    /**
     * @param url 方法链接  
     * @param entity jsonString
     */
    public static void Put(String url,String entity){
        String userName = "admin";
        String password = "admin";
        //设备配置URL
        CloseableHttpClient client = HttpClients.createDefault();  
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        UsernamePasswordCredentials usernamePassword = new UsernamePasswordCredentials(
                userName, password);
        credsProvider.setCredentials(AuthScope.ANY, usernamePassword);
        //HttpClient上下文,保存用户名信息  
        HttpClientContext context = HttpClientContext.create(); 
        context.setCredentialsProvider(credsProvider); 
        HttpPut request = new HttpPut(url);  
        // 设置请求和传输超时时间  
        RequestConfig requestConfig = RequestConfig.custom()  
                .setSocketTimeout(2000).setConnectTimeout(2000).build();  
        request.setConfig(requestConfig); 
        try {
            StringEntity params =new StringEntity(entity);
            request.setHeader("Content-type", "application/json");
            request.setEntity(params);
            CloseableHttpResponse response = client.execute(request,context);
            LOG.info(response.toString());
            //请求发送成功，并得到响应  
        } catch (IOException e) {  
            LOG.error("PUT失败"+url+e);  
        } finally {  
            request.releaseConnection();  
        }
    }
    
    public static void Delete(String url){
        String userName = "admin";
        String password = "admin";
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
        HttpDelete request = new HttpDelete(url);  
        // 设置请求和传输超时时间  
        RequestConfig requestConfig = RequestConfig.custom()  
                .setSocketTimeout(2000).setConnectTimeout(2000).build();  
        request.setConfig(requestConfig);  
        try {  
            CloseableHttpResponse response = client.execute(request,context);  
            LOG.info(response.toString());
            //请求发送成功，并得到响应    
        } catch (IOException e) {  
            LOG.error("DELETE失败"+url+e);  
        } finally {  
            request.releaseConnection();  
        }  
    }
    
    public static void Post (String url,String entity){
        String userName = "admin";
        String password = "admin";
        //设备配置URL
        CloseableHttpClient client = HttpClients.createDefault();  
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        UsernamePasswordCredentials usernamePassword = new UsernamePasswordCredentials(
                userName, password);
        credsProvider.setCredentials(AuthScope.ANY, usernamePassword);
        //HttpClient上下文,保存用户名信息  
        HttpClientContext context = HttpClientContext.create(); 
        context.setCredentialsProvider(credsProvider); 
        HttpPost request = new HttpPost(url);
     // 设置请求和传输超时时间  
        RequestConfig requestConfig = RequestConfig.custom()  
                .setSocketTimeout(2000).setConnectTimeout(2000).build();  
        request.setConfig(requestConfig); 
        try {
            StringEntity params =new StringEntity(entity);
            request.setHeader("Content-type", "application/json");
            request.setEntity(params);
            CloseableHttpResponse response = client.execute(request,context);  
            LOG.info(response.toString());
            //请求发送成功，并得到响应  
        } catch (IOException e) {  
            LOG.error("POST失败"+url+e);  
        } finally {  
            request.releaseConnection();  
        }
    }
}
