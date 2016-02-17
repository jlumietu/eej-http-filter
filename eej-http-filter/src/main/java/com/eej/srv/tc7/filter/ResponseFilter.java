/**
 * 
 */
package com.eej.srv.tc7.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.log4j.Logger;

import com.eej.srv.http.ByteArrayPrintWriter;

/**
 * @author jlumietu
 *
 */
public class ResponseFilter implements Filter{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private boolean debugRequest = false;

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		logger.debug("request.getLocalPort " + request.getLocalPort());
		logger.debug("request.getProtocol " + request.getProtocol());
		logger.debug("request.getServerPort " + request.getServerPort());
		logger.debug("request.getServerName " + request.getServerName());
		
		if(this.debugRequest){
			System.out.println("request.getLocalPort " + request.getLocalPort());
			System.out.println("request.getProtocol " + request.getProtocol());
			System.out.println("request.getServerPort " + request.getServerPort());
			System.out.println("request.getServerName " + request.getServerName());
		
			if(request instanceof HttpServletRequest){
				System.out.println("HttpHeader debug");
				Enumeration<String> headers = ((HttpServletRequest) request).getHeaderNames();
				for(;headers.hasMoreElements();){
					String h = headers.nextElement();
					System.out.println("RequestHeader - " + h + " = " + ((HttpServletRequest) request).getHeader(h));
				}
			}
		}
		
		filterChain.doFilter(request, response);
		
		
		
		/* Post process */
		//response.
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("Response filter initialization " + filterConfig + " --> " + filterConfig.getInitParameter("debug"));
		if(filterConfig.getInitParameter("debug") != null){
			System.out.println("apply debug initializacion " + filterConfig.getInitParameter("debug"));
			try{
				this.debugRequest = Boolean.parseBoolean(filterConfig.getInitParameter("debug"));
				System.out.println(this.debugRequest + " value is applied to debugRequest property");
			}catch(Exception e){
				System.out.println("debug param badly initialized, must be true or false(default)");
			}
		}
	}
	
	

}
