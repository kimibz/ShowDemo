package com.xigua.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;

public class XmlTest {
  //要测试的xml对象,带有命名空间 
    static String str = "<rsiq to=\"378\" type=\"result\" from=\"12345678#hjh20120718150006@126.com/cti\">" + 
                "<query xmlns=\"http://te.com/resource/cti/getonlineseat\">" + 
                "<telephone>12345678</telephone>" + 
                "<session-id>20130328155409100477</session-id>" + 
                "<workgroup><groupname>66666</groupname><seats/></workgroup><workgroup><groupname>888</groupname><seats/></workgroup>" + 
                "<workgroup><groupname>test</groupname><seats>" + 
                "<seat><jid>378</jid><name>地方</name><seatnumber>378</seatnumber></seat>" + 
                "</seats></workgroup></query></rsiq>"; 
    //要测试的xml对象,没有命名空间 
    static String str1 = "<rsiq to=\"378\" type=\"result\" from=\"12345678#hjh20120718150006@126.com/cti\">" + 
                "<query >" + 
                "<telephone>12345678</telephone>" + 
                "<session-id>20130328155409100477</session-id>" + 
                "<workgroup><groupname>66666</groupname><seats/></workgroup><workgroup><groupname>888</groupname><seats/></workgroup>" + 
                "<workgroup><groupname>test</groupname><seats>" + 
                "<seat><jid>378</jid><name>地方</name><seatnumber>378</seatnumber></seat>" + 
                "</seats></workgroup></query></rsiq>";   
     
    //传统方法一步一步解析带有命名空间的XML文件 
    @SuppressWarnings("unchecked") 
    private static void test() throws DocumentException { 
        List<Map<String, Object>> seatlist = new ArrayList<Map<String,Object>>(); 
             
        Document document = DocumentHelper.parseText(str); 
        Element root = document.getRootElement(); 
        Element query = root.element("query"); 
        List<Element> workgroups = query.elements("workgroup"); 
        for(Element workgroup : workgroups) { 
            Element seatsElement = workgroup.element("seats"); 
            List<Element> seats = seatsElement.elements("seat"); 
            for(Element seat : seats) { 
                Map<String, Object> seatmap = new HashMap<String, Object>(); 
                String jid = seat.elementText("jid"); 
                String seatname = seat.elementText("name"); 
                String seatnumber = seat.elementText("seatnumber"); 
                String sessioncount = seat.elementText("sessioncount"); 
                seatmap.put("jid",jid); 
                seatmap.put("seatname",seatname); 
                seatmap.put("seatnumber",seatnumber); 
                seatmap.put("sessioncount",sessioncount); 
                seatlist.add(seatmap); 
                System.out.println(seatmap); 
            } 
        } 
        System.out.println(seatlist); 
    } 
     
    //Xpath方法解析没有命名空间的XML文件 
    @SuppressWarnings("unchecked") 
    private static void test1() throws DocumentException { 
        List<Map<String, Object>> seatlist = new ArrayList<Map<String,Object>>(); 
 
        Document document = DocumentHelper.parseText(str1); 
        List seats = document.selectNodes("//seat"); 
          for (int i = 0; i < seats.size(); i++) { 
            Map<String, Object> seatmap = new HashMap<String, Object>(); 
            Element seat = (Element) seats.get(i); 
            String jid = seat.elementText("jid"); 
            String seatname = seat.elementText("name"); 
            String seatnumber = seat.elementText("seatnumber"); 
            String sessioncount = seat.elementText("sessioncount"); 
            seatmap.put("jid",jid); 
            seatmap.put("seatname",seatname); 
            seatmap.put("seatnumber",seatnumber); 
            seatmap.put("sessioncount",sessioncount); 
            seatlist.add(seatmap); 
          } 
        System.out.println(seatlist); 
    } 
     
    //带有命名空间的XML文件，Xpath的解析方法 
    @SuppressWarnings("unchecked") 
    private static void test2() throws DocumentException { 
        List<Map<String, Object>> seatlist = new ArrayList<Map<String,Object>>(); 
         
        Map map = new HashMap(); 
        map.put("getonlineseat","http://te.com/resource/cti/getonlineseat"); 
        Document document = DocumentHelper.parseText(str); 
        XPath x = document.createXPath("//getonlineseat:seat"); 
        x.setNamespaceURIs(map); 
        List nodelist = x.selectNodes(document);    
          for (int i = 0; i < nodelist.size(); i++) { 
            Map<String, Object> seatmap = new HashMap<String, Object>(); 
            Element seat = (Element) nodelist.get(i); 
            String jid = seat.elementText("jid"); 
            String seatname = seat.elementText("name"); 
            String seatnumber = seat.elementText("seatnumber"); 
            String sessioncount = seat.elementText("sessioncount"); 
            seatmap.put("jid",jid); 
            seatmap.put("seatname",seatname); 
            seatmap.put("seatnumber",seatnumber); 
            seatmap.put("sessioncount",sessioncount); 
            seatlist.add(seatmap); 
          } 
        System.out.println(seatlist); 
    } 
 
    //主函数 
    public static void main(String args[]) throws DocumentException { 
        System.out.println("-----------test()方法------------"); 
        test(); 
        System.out.println("-----------test1()方法------------"); 
        test1(); 
        System.out.println("-----------test2()方法------------"); 
        test2(); 
    } 
}
