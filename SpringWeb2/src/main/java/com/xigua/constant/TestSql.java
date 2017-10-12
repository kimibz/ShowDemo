package com.xigua.constant;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class TestSql {
    public static void main(String args[]){
        insertData("123","remotedevice17-830");
    }
    
    static void insertData(String notificationMsg,String id){
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/shirotest?user=root&password=123456";
        String user = "root";
        String password = "123456";
        String table_id = id.toString().replace("{", "").replace("}","").replace("-","");
        String msg = notificationMsg.replaceAll("\"", "\\\"");
                try {
                Class.forName(driver);
                Connection conn = (Connection) DriverManager.getConnection(url, user, password);
                   Statement statement = (Statement) conn.createStatement();
                   String sql = "insert into "+table_id+" (msg) values('"+msg+"')";
                   String createSql = "CREATE TABLE IF NOT EXISTS personS "
                           + "(id INT(11) NOT NULL AUTO_INCREMENT,msg VARCHAR(999),time TIMESTAMP,PRIMARY KEY ( id ));";
                   statement.executeUpdate(createSql);
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

 