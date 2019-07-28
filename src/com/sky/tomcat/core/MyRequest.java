package com.sky.tomcat.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

public class MyRequest implements HttpServletRequest {

	private BufferedReader bufferedReader;
	private InputStream inputStream;

	private String uri;
	private String method;
	 
	private Map<String, Object> attribute;
	
	private String characterEncoding;

	private Map<String, String[]> parameterMap = new HashMap<String, String[]>();

	public MyRequest(String method, String uri, InputStream inputStream, BufferedReader bufferedReader)
			throws IOException {
		this.method = method;
		this.bufferedReader = bufferedReader;
		this.inputStream = inputStream;
		this.uri = uri;
		parse();
	}
	private void parse() throws IOException {
		if (uri.contains("?")) {
			String[] strs = uri.split("\\?");
			if (strs.length >= 2) {
				uri = strs[0];
				String data = strs[1];
				//if (data.contains("&")) {
					String[] nameAndValues = data.split("&");
					for (String nameAndValue : nameAndValues) {
						String[] nameAndValueArr = nameAndValue.split("=");
						if (parameterMap.containsKey(nameAndValueArr[0])) {
							String[] values = parameterMap.get(nameAndValueArr[0]);
							String[] newValues = new String[values.length + 1];
							System.arraycopy(values, 0, newValues, 0, values.length);
							if (nameAndValueArr.length > 1) {
								newValues[newValues.length - 1] = nameAndValueArr[1];
							} else {
								newValues[newValues.length - 1] = "";
							}
							parameterMap.put(nameAndValueArr[0], newValues);
						} else {
							if (nameAndValueArr.length > 1) {
								parameterMap.put(nameAndValueArr[0], new String[] { nameAndValueArr[1] });
							} else {
								parameterMap.put(nameAndValueArr[0], new String[] { "" });
							}
						}
					}
				/*}else {
					String[] nameAndValueArr=data.split("=");
					if(nameAndValueArr.length>1) {
						parameterMap.put(nameAndValueArr[0], new String[] {nameAndValueArr[1]});
					}else {
						parameterMap.put(nameAndValueArr[0], new String[] {""});
					}
				}*/
			}

		}
		/*String str = null;
		while (true) {
			str = bufferedReader.readLine();
			if (str == null || str == "\r\n")
				break;
		}*/
	}

	@Override
	public String getParameter(String name) {
		String[] values=parameterMap.get(name);
		return values!=null ? values[0]:null;
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(parameterMap.keySet());
	}

	@Override
	public String[] getParameterValues(String name) {
		return parameterMap.get(name);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return parameterMap;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return bufferedReader;
	}

	@Override
	public String getMethod() {
		return method;
	}
	
	@Override
	public Object getAttribute(String name) {
		if(attribute==null)
			return null;
		else
			return attribute.get(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return Collections.enumeration(attribute.keySet());
	}
	
	@Override
	public void setAttribute(String name, Object value) {
		if(attribute==null)
			attribute=new HashMap<String,Object>();
		attribute.put(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		if(attribute!=null) 
			attribute.remove(name);
	}
	
	@Override
	public String getCharacterEncoding() {
		return characterEncoding;
	}

	@Override
	public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
		this.characterEncoding=env;
	}

	@Override
	public int getContentLength() {
		return 0;
	}

	@Override
	public long getContentLengthLong() {
		return 0;
	}

	@Override
	public String getContentType() {
		return null;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return null;
	}

	@Override
	public String getProtocol() {
		return null;
	}

	@Override
	public String getScheme() {
		return null;
	}

	@Override
	public String getServerName() {
		return null;
	}

	@Override
	public int getServerPort() {
		return 0;
	}

	@Override
	public String getRemoteAddr() {
		return null;
	}

	@Override
	public String getRemoteHost() {
		return null;
	}

	

	@Override
	public Locale getLocale() {
		return null;
	}

	@Override
	public Enumeration<Locale> getLocales() {
		return null;
	}

	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		return null;
	}

	@Override
	public String getRealPath(String path) {
		return null;
	}

	@Override
	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLocalPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AsyncContext startAsync() throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
			throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAsyncStarted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAsyncSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AsyncContext getAsyncContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DispatcherType getDispatcherType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAuthType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cookie[] getCookies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getDateHeader(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getHeader(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIntHeader(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	

	@Override
	public String getPathInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPathTranslated() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContextPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQueryString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUserInRole(String role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Principal getUserPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestedSessionId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuffer getRequestURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession getSession(boolean create) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String changeSessionId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void login(String username, String password) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void logout() throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Part> getParts() throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Part getPart(String name) throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends HttpUpgradeHandler> T upgrade(Class<T> httpUpgradeHandlerClass)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}

}
