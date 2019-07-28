package com.sky.tomcat.core;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class MyServletOutputStream extends ServletOutputStream{

	private OutputStream os;	
	public MyServletOutputStream(OutputStream os) {
		this.os=os;
	}
	
	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setWriteListener(WriteListener listener) {
		
	}

	@Override
	public void write(int b) throws IOException {
		os.write(b);
	}
	@Override
	public void write(byte[] b) throws IOException {
		os.write(b);
	}
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		os.write(b, off, len);
	}
	@Override
	public void flush() throws IOException {
		os.flush();
	}
	@Override
	public void close() throws IOException {
		
	}
}
