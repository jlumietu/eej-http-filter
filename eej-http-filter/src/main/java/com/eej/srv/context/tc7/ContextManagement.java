package com.eej.srv.context.tc7;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import com.eej.srv.context.model.SystemStatus;

public interface ContextManagement {

	public abstract Context getContext(String contextName);

	public abstract List<DataSource> getContextDataSources(String contextName);

	public abstract List<DataSource> getContextDataSources(
			HttpServletRequest request);
	
	public SystemStatus getSystemStatus(HttpServletRequest req);
	
	public abstract String getAppBase();
	
	public abstract String getServletContextBase(String contextPath);

}