package com.sky.tomcat.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.sky.tomcat.util.Logger;

public class HandlerRequest implements Runnable{

	private Socket clientSocket;
	public HandlerRequest(Socket clientSocket) {
		this.clientSocket=clientSocket;
	}
	@Override
	public void run() {
		Logger.log(Thread.currentThread().getName()+" handler request");
		OutputStream out=null;
		BufferedReader br=null;
		InputStream in=null;
		try {
			in=clientSocket.getInputStream();
			br=new BufferedReader(new InputStreamReader(in));
			String requestLine=br.readLine();
			String[] strs=requestLine.split(" ");
			String method=strs[0];
			String requestURI=strs[1];
			Logger.log(requestURI);
			out=clientSocket.getOutputStream();
			if(requestURI.endsWith(".ico")) {
				out.write("HTTP/1.1 200 OK\r\n".getBytes());
				out.write("Content-Type:text/html;charset=UTF-8\r\n".getBytes());
				out.write("\r\n".getBytes());
			}else if(requestURI.endsWith(".html")||requestURI.endsWith(".htm")) {
				responseStaticPage(requestURI,new PrintWriter(out));
			}else {
				String servletPath=requestURI;
				if(servletPath.contains("?")) {
					servletPath=servletPath.split("\\?")[0];
				}
				String webAppName=servletPath.split("/")[1];
				Map<String, String> servletMap=WebParser.servletMaps.get(webAppName);
				String urlPattern=servletPath.substring(webAppName.length()+1);
				String servletClassName=servletMap.get(urlPattern);
				
				if(servletClassName!=null) {
					out.write("HTTP/1.1 200 OK\r\n".getBytes());
					out.write("Content-Type:text/html;charset=UTF-8\r\n".getBytes());
					out.write("\r\n".getBytes());
					Class c=Class.forName(servletClassName);
					Object obj=c.newInstance();
					Servlet servlet=(Servlet)obj;
					ServletRequest request=new MyRequest(method, requestURI, in, br);
					ServletResponse response=new MyResponse(out);
					servlet.service(request, response);
					response.flushBuffer();
				}else {
					response404(new PrintWriter(out));
				}
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(out!=null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(br!=null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private void responseStaticPage(String requestURI, PrintWriter out) {
		String htmlPath=requestURI.substring(1);
		BufferedReader br=null;	
		try {
			br=new BufferedReader(new FileReader(htmlPath));
			StringBuilder html=new StringBuilder();
			html.append("HTTP/1.1 200 OK\r\n");
			html.append("Content-Type:text/html;charset=UTF-8\r\n\r\n");
			String temp=null;
			while((temp=br.readLine())!=null) {
				html.append(temp);
			}
			out.print(html);
			out.flush();
		} catch (FileNotFoundException e) {//404
			response404(out);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(br!=null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out!=null) {
				out.close();
			}
		}
	}
	
	private void response404(PrintWriter out) {
		StringBuilder html=new StringBuilder();
		html.append("HTTP/1.1 404 NotFound\r\n");
		html.append("Content-Type:text/html;charset=UTF-8\r\n\r\n");
		html.append("<html>");
		html.append("<head><title>404-not found找不到</title></head>");
		html.append("<body><h1 align='center'><font color='red'>404-NotFound</font></h1></body>");
		html.append("<html>");
		out.print(html);
	}
	
}
