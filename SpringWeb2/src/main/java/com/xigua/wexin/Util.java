package com.xigua.wexin;

import okhttp3.*;
import org.bouncycastle.util.encoders.Base64;
import org.json.JSONObject;
 
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Util {
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    private static final String WXGAME_URL = "https://mp.weixin.qq.com/wxagame/wxagame_settlement";
    public static final String SESSIONID_ERROR = "SESSIONID有误，请检查";
 
    private static String getActionData(String sessionKey, String encryptedData, String iv) {
        byte[] sessionKeyBy = sessionKey.getBytes();
        byte[] en = new byte[0];
        try {
            en = encryptedData.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] ivBy = iv.getBytes();
        byte[] enc = Pkcs7Encoder.encryptOfDiyIV(en, sessionKeyBy, ivBy);
        return new String(Base64.toBase64String(enc));
    }
 
    public static String postData(String score, String times, String session_id) {
        String result = null;
        String content = "{\"score\": " + score + ", \"times\": " + times + "}";
        String AES_KEY = null;
        try {
            AES_KEY = session_id.substring(0, 16);
        } catch (Exception e) {
            return SESSIONID_ERROR;
        }
 
        String AES_IV = AES_KEY;
        OkHttpClient okHttpClient = new OkHttpClient();
        String actionData = Util.getActionData(AES_KEY, content, AES_IV);
        String json = "{\"base_req\":{\"session_id\":\"" + session_id + "\",\"fast\":1},\"action_data\":\"" + actionData + "\"}";
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(WXGAME_URL)
                .header("Accept","*/*")
                .header("Accept-Language","zh-cn")
                .header("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 10_3_1 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Mobile/14E304 MicroMessenger/6.6.1 NetType/WIFI Language/zh_CN")
                .header("Content-Length","680")
                .header("Content-Type","application/json")
                .header("Referer","https://servicewechat.com/wx7c8d593b2c3a7703/5/page-frame.html")
                .header("Host","mp.weixin.qq.com")
                .header("Connection","keep-alive")
                .post(requestBody)
                .build();
        ResponseBody responseBody = null;
        try {
            responseBody = okHttpClient.newCall(request).execute().body();
            result = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (responseBody != null) {
                responseBody.close();
            }
        }
        return result;
    }
}
