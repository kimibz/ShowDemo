package com.xigua.serviceImp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xigua.JSONTemplate.ControllerSettings;
import com.xigua.JSONTemplate.JSONTemplate;
import com.xigua.service.NetconfService;
import com.xigua.util.Util;


@Service
public class NetconfServiceImp implements NetconfService{
    
    private String Ipaddress = ControllerSettings.ip;
    private static final Logger LOG = LoggerFactory.getLogger(NetconfServiceImp.class);
	
	public void changeDeviceName(String olt_oldname, String new_name) {
		// TODO Auto-generated method stub
		String userName = "admin";
		String password = "admin";
		//设备配置URL
		String url = Ipaddress+"/restconf/config/network-topology:network-topology/"
				+ "topology/topology-netconf/node/"+olt_oldname;
		Util util = new Util();
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
        // 发送put请求  
        HttpPut request = new HttpPut(url);  
        // 设置请求和传输超时时间  
        RequestConfig requestConfig = RequestConfig.custom()  
                .setSocketTimeout(2000).setConnectTimeout(2000).build();  
        request.setConfig(requestConfig); 
        JSONObject data = util.getJSONObj(url);
        JSONArray device = data.getJSONArray("node");
        JSONObject device_obj = JSON.parseObject(device.get(0)+"");
        Map<String,String> value = JSON.parseObject(device_obj.toJSONString(),Map.class);
        //修改设备名称
        value.put("host-tracker-service:id", new_name);
//        value.put("netconf-node-topology:keepalive-delay", "0");
        String entity = "{\"node\":["+JSON.toJSONString(value)+"]}";
        try {
        	StringEntity params =new StringEntity(entity);
        	request.setHeader("Content-type", "application/json");
        	request.setEntity(params);
            CloseableHttpResponse response = client.execute(request,context);  
            //请求发送成功，并得到响应  
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                LOG.info("修改成功");
            } else {
                LOG.error("get请求提交失败:" + url);  
            }  
        } catch (IOException e) {  
            LOG.error("get请求提交失败:" + url + e);  
        } finally {  
            request.releaseConnection();  
        }  
	}

	public Map<String, String> getAll() {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		String userName = "admin";
		String password = "admin";
		String url = Ipaddress+"/restconf/operational/"
                + "network-topology:network-topology/topology/topology-netconf/";
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
                //获取nodes分支
                JSONArray jsonObj = object.getJSONArray("topology");
                JSONObject ControllerObj = JSON.parseObject(jsonObj.get(0)+"");
                JSONArray deviceArr = JSON.parseArray(ControllerObj.get("node").toString()); 
                for(int i=0; i < deviceArr.size() ; i++){
                    JSONObject netconfDevice = new JSONObject();
                    netconfDevice = JSON.parseObject(deviceArr.get(i)+"");
                    String id ="";
                    id = (String) netconfDevice.get("node-id");
                    String ifConnected = "离线" ;
                    if(netconfDevice.get("netconf-node-topology:connection-status").
                            equals("connected")){
                        ifConnected = "在线";
                    };
                    //不输出controller-config的内容,因为这是原有控制器的有的能力。
                    if(!(id.equals("controller-config")))
                        map.put(id, ifConnected);
                }
            }
        } catch (IOException e) {  
            LOG.error("get请求提交失败:" + url + e);  
        } finally {  
            request.releaseConnection();  
        }  
		return map;
	}

	public Boolean getStatus(String device_name) {
		// TODO Auto-generated method stub
		String userName = "admin";
		String password = "admin";
		Boolean ifConnected = false ;
		String url = Ipaddress+"/restconf/operational/"
				+ "opendaylight-inventory:nodes/node/"
				+ device_name +"/yang-ext:mount/";
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
                LOG.info("设备："+device_name+",状态：在线");
                ifConnected = true ;
            } else {
                LOG.info("设备："+device_name+"状态：离线");
            }  
        } catch (IOException e) {  
            LOG.error("get请求提交失败:" + url + e);  
        } finally {  
            request.releaseConnection();  
        }  
		return ifConnected;
	}

	public void spawnNewDevice(int StartPort,int endPort) {
		// TODO Auto-generated method stub
		String userName = "admin";
		String password = "admin";
		//设备配置URL
		String url = Ipaddress+"/restconf/config/network-topology:network-topology/"
				+ "topology/topology-netconf/node/";
		String name = "device" ;
//		Util util = new Util();
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
        for(int i = StartPort ; i <= endPort ; i++){
        	String device_name = name + i;
        	String e_url = url + device_name;
        	String deviceJson = JSONTemplate.SpawnDeviceJSON;
        	JSONObject object = JSON.parseObject(deviceJson);
    		JSONArray arr =object.getJSONArray("node");
    		JSONObject device_obj = JSON.parseObject(arr.get(0)+"");
    		//添加新数据
    		device_obj.put("netconf-node-topology:host", "127.0.0.1");
    		device_obj.put("node-id", device_name);
    		device_obj.put("password", "admin");
    		device_obj.put("netconf-node-topology:username", "admin");
    		device_obj.put("netconf-node-topology:port", i);
    		device_obj.put("netconf-node-topology:tcp-only", false);
    		device_obj.put("netconf-node-topology:keepalive-delay", 0);
    		device_obj.put("host-tracker-service:id", device_name);
    		arr.clear();
    		arr.add(0, device_obj);
    		object.put("node", arr);
            String entity = object.toJSONString();
            // 发送put请求  
            HttpPut request = new HttpPut(e_url);  
            // 设置请求和传输超时时间  
            RequestConfig requestConfig = RequestConfig.custom()  
                    .setSocketTimeout(2000).setConnectTimeout(2000).build();  
            request.setConfig(requestConfig); 
            try {
            	StringEntity params =new StringEntity(entity);
            	request.setHeader("Content-type", "application/json");
            	request.setEntity(params);
                CloseableHttpResponse response = client.execute(request,context);  
                //请求发送成功，并得到响应  
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                    LOG.info("修改成功");
                } else {
                    LOG.error("get请求提交失败:" + url);  
                }  
            } catch (IOException e) {  
                LOG.error("get请求提交失败:" + url + e);  
            } finally {  
                request.releaseConnection();  
            } 
        }
        //修改设备名称
	}

	public void deleteDevice(int StartPort, int endPort) {
		// TODO Auto-generated method stub
		String userName = "admin";
		String password = "admin";
		//设备配置URL
		String url = Ipaddress+"/restconf/config/network-topology:network-topology/"
				+ "topology/topology-netconf/node/";
		String name = "device" ;
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
        for(int i = StartPort ; i <= endPort ; i++){
        	String device_name = name + i;
        	String e_url = url + device_name;;
            // 发送put请求  
            HttpDelete request = new HttpDelete(e_url);  
            // 设置请求和传输超时时间  
            RequestConfig requestConfig = RequestConfig.custom()  
                    .setSocketTimeout(2000).setConnectTimeout(2000).build();  
            request.setConfig(requestConfig); 
            try {
                CloseableHttpResponse response = client.execute(request,context);  
                //请求发送成功，并得到响应  
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                    System.out.println("删除成功");
                } else {
                    LOG.error("get请求提交失败:" + url);  
                }  
            } catch (IOException e) {  
                LOG.error("get请求提交失败:" + url + e);  
            } finally {  
                request.releaseConnection();  
            } 
        }
	}

	@Override
	public void deleteOneDevice(String device_nodeId) {
		// TODO Auto-generated method stub
		String userName = "admin";
		String password = "admin";
		//设备配置URL
		String url = Ipaddress+"/restconf/config/network-topology:network-topology/"
				+ "topology/topology-netconf/node/";
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
        	String e_url = url + device_nodeId;;
            // 发送put请求  
            HttpDelete request = new HttpDelete(e_url);  
            // 设置请求和传输超时时间  
            RequestConfig requestConfig = RequestConfig.custom()  
                    .setSocketTimeout(2000).setConnectTimeout(2000).build();  
            request.setConfig(requestConfig); 
            try {
                CloseableHttpResponse response = client.execute(request,context);  
                //请求发送成功，并得到响应   
            } catch (IOException e) {  
                LOG.error("get请求提交失败:" + url + e);  
            } finally {  
                request.releaseConnection();  
            } 
	}
}
