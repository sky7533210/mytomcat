package com.sky.tomcat.core;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ServerParser {
	public static int getPort() {
		int port=8080;
		try {
			SAXReader saxReader=new SAXReader();
			Document document= saxReader.read("conf/server.xml");
			String xPath="/server/service/connector";
			Element connector=(Element) document.selectSingleNode(xPath);
			Attribute attribute=connector.attribute("port");
			port=Integer.parseInt(attribute.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return port;
	}
}
