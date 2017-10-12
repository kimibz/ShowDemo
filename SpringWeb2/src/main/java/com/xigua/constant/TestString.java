package com.xigua.constant;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class TestString {
        public static void main (String[] args) throws DocumentException{
            String xml = "<edit-config><target>candidate"
                    + "</target><default-operation>merge"
                    + "</default-operation><error-option>rollback-on-error"
                    + "</error-option><config>"
                    + "<pm-sys:configuration xmlns:pm-sys=\"http://www.zte.com.cn/zxr10"
                    + "/netconf/schema/rosng/pm-sys\"><pm-sys:sys><pm-sys:hostname>"
                    + "C600hostname20170624</pm-sys:hostname></pm-sys:sys>"
                    + "</pm-sys:configuration></config></edit-config>";
            String str = "abc<icon>def</icon>deftfh<icon>a</icon>";
            Pattern p=Pattern.compile("<icon>(\\w)</icon>");
            Pattern pp=Pattern.compile("<error-option>(\\w+)</<error-option>");
            Matcher mm = pp.matcher(xml);
            Matcher m=p.matcher(str);
            System.out.println(mm.find());
            System.out.println(m.find());
            while(m.find()){
                System.out.println(m.group(1));
            }
            Document document = DocumentHelper.parseText(xml);
            System.out.println(document.asXML());
            Element element = (Element) document.selectSingleNode("/configuration");
            Element root = document.getRootElement();
            List<Element> childElements = root.elements();
            Map<String,Object> mapEle = new HashMap<String, Object>();
            //遍历子节点
            mapEle = getAllElements(childElements,mapEle);
            System.out.println(document.getPath()+element);
            System.out.println(mapEle.get("target").toString());
            Set set = mapEle.entrySet();
            Iterator it = set.iterator();
            while(it.hasNext()){
                Entry entry = (Entry) it.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                System.out.println(key + "=" + value);
            }
        }
        private static Map<String, Object> getAllElements(List<Element> childElements,Map<String,Object> mapEle) {
            for (Element ele : childElements) {
                mapEle.put(ele.getName(), ele.getText());
                if(ele.elements().size()>0){
                    mapEle = getAllElements(ele.elements(), mapEle);
                }
            }
            return mapEle;
        }
}
