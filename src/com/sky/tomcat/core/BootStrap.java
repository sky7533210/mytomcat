package com.sky.tomcat.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.dom4j.DocumentException;

import com.sky.tomcat.util.Logger;

/**
 * 
 * @author sky
 *我的tomcat启动入口
 *@version 1.0
 *@since 1.0
 */
public class BootStrap {

	/**
	 * 主函数
	 * @param args
	 */
	public static void main(String[] args) {
		start();
	}
	/**
	 * 启动
	 */
	public static void start(){
		long begin=System.currentTimeMillis();
		Logger.log("httpserver start");
		ServerSocket serverSocket=null;
		
		try {
			int port=ServerParser.getPort();
			Logger.log("http-port-"+port);
			serverSocket=new ServerSocket(port);
			
			WebParser.parse(new String[] {"oa"});
			
			long end=System.currentTimeMillis();
			Logger.log("httpserver started "+(end-begin)+"ms");
			while(true) {
				Socket socket=serverSocket.accept();
				 new Thread( new HandlerRequest(socket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
			Logger.error("server start faild maybe port has be used");
		}catch (DocumentException e) {
			e.printStackTrace();
			Logger.error("web.xml parse error");
		} finally {
			if(serverSocket!=null&&!serverSocket.isClosed()) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
