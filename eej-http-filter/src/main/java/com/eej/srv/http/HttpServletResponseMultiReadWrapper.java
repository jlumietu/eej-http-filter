package com.eej.srv.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class HttpServletResponseMultiReadWrapper extends HttpServletResponseWrapper{
	
	private ByteArrayOutputStream cachedBytes;
	
	public HttpServletResponseMultiReadWrapper(HttpServletResponse response) {
		super(response);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponseWrapper#getOutputStream()
	 */
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return super.getOutputStream();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponseWrapper#getResponse()
	 */
	@Override
	public ServletResponse getResponse() {
		// TODO Auto-generated method stub
		return super.getResponse();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponseWrapper#getWriter()
	 */
	@Override
	public PrintWriter getWriter() throws IOException {
		// TODO Auto-generated method stub
		return super.getWriter();
	}
	
	

}
