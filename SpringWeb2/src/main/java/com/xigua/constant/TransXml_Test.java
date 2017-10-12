package com.xigua.constant;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.ElementCSSInlineStyle;

import com.xigua.model.eventNotification;

public class TransXml_Test {
	private static final Logger LOG = LoggerFactory.getLogger(TransXml_Test.class);
	public static void main(String args[]) throws DocumentException{
		String xmlStr = "<notification xmlns=\"urn:ietf:params:xml:ns:"
				+ "netmod:notification:1.0\"><eventTime>2017-06-14T14:39:18+08:00"
				+ "</eventTime><event xmlns=\"urn:opendaylight:test\">"
				+ "<event-class>fault</event-class><reporting-entity>"
				+ "<card>Ethernet0</card></reporting-entity>"
				+ "<severity>major</severity></event></notification> ";
		Document document = DocumentHelper.parseText(xmlStr);
		Document NameSpaceDoc = DocumentHelper.parseText(xmlStr);  

		eventNotification event = new eventNotification();
		Element root = document.getRootElement();
//		event.setEvent_class(root.elements("severtiy"));
		List<Element> childElements = root.elements();

		Map<String,Object> mapEle = new HashMap<String, Object>();
		//遍历子节点
		mapEle = getAllElements(childElements,mapEle);
		event.setEvent_class(mapEle.get("event-class").toString());
		System.out.println(event.getEvent_class());
		System.out.println(xmlStr);
		System.out.println(NameSpaceDoc.asXML());

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
