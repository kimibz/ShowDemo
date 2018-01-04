package com.xigua.constant;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.xigua.JSONTemplate.JSONTemplate;
import com.xigua.util.HttpRequestUtil;

public class TestMethod {
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/shirotest?user=root&password=123456";
        String user = "root";
        String password = "admin";
        int endTime = 199;
        int startTime = 99;
        int length = 100;
        try {
            Class.forName(driver);
            Connection conn = (Connection) DriverManager.getConnection(url, user, password);
            Statement statement = (Statement) conn.createStatement();
            String sql = "insert into 100packagetest(time) values ("+ (endTime-startTime)+")";
            statement.executeUpdate(sql);
            statement.close();
            conn.close();}
            catch(ClassNotFoundException e) {
                e.printStackTrace();
                } catch(SQLException e) {
                e.printStackTrace();
                } catch(Exception e) {
                    e.printStackTrace();
                }
    }
} 
