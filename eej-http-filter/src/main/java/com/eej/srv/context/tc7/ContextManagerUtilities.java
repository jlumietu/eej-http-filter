/**
 * 
 */
package com.eej.srv.context.tc7;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import com.eej.srv.context.HttpConnectorPort;
import com.eej.srv.context.model.DataSourceGroupStatus;
import com.eej.srv.context.model.DataSourceStatus;
import com.eej.srv.context.model.SystemStatus;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.deploy.ContextResource;
import org.apache.catalina.deploy.NamingResources;
import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;


/**
 * @author jlumietu
 *
 */
public class ContextManagerUtilities implements HttpConnectorPort, ContextManagement{
	
	public static final String CATALINA_SERVICE_NAME = "Catalina";
	
	public static final String CONNECTOR_HTTP_PROTOCOL_NAME = "HTTP/1.1"; 

	private Logger logger = Logger.getLogger(this.getClass());
	
	private StandardServer server;
	
	private Service catalinaService;
	
	private Connector httpConnector;	
	
	private Map<String, Context> contextMap;
	
	private Map<String, List<ContextResource>> contextResources; 
	
	private String appBase = null;
	
	/**
	 * 
	 */
	private ContextManagerUtilities() {
		super();
		this.contextMap = new HashMap<String, Context>();
		this.contextResources = new HashMap<String, List<ContextResource>>();
		this.server = this.getServerInstance();
		this.catalinaService = this.getCatalinaService();
		this.httpConnector = this.getHttpConnector();
	}

	/**
	 * 
	 * @return
	 */
	private StandardServer getServerInstance(){
		org.apache.catalina.core.StandardServer server = null; 
		try{
			MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
			server = (StandardServer)mbeanServer.getAttribute(
						new ObjectName("Catalina:type=Server"),
						"managedResource"
					);
			if(logger.isDebugEnabled()){
				logger.debug("Server found. Info: ");
				logger.debug(" - address          : " + server.getAddress());
				logger.debug(" - domain           : " + server.getDomain());
				logger.debug(" - info             : " + server.getInfo());
				logger.debug(" - shutdown port    : " + server.getPort());
				logger.debug(" - shutdown command : " + server.getShutdown());
				logger.debug(" - serverInfo       : " + server.getServerInfo());
				logger.debug(" - status           : " + server.getStateName());
				
			}
			
			if(logger.isDebugEnabled()){
				try{
					NamingResources nr = server.getGlobalNamingResources();
					for(ContextResource cr : nr.findResources()){
						logger.debug("ContextResource.name = " + cr.getName());
						NamingResources nr1 = cr.getNamingResources();
						logger.debug("ContextResource[" + cr.getName() + "].namingReources = " + nr1.getDomain());
						logger.debug("ContextResource[" + cr.getName() + "].namingReources = " + nr1.getStateName());
					}
				}catch(Exception e){
					logger.warn("Error accesing global NamingResources " + e.getMessage(), e);
				}
				
				for(Object o : System.getProperties().keySet()){
					logger.debug("System.getProperty(" + o.toString() + ")=" + System.getProperty(o.toString()));
				}
			}	
			
		}catch(Throwable t){
			logger.fatal("Fatal Error Recovering StandardServer from MBeanServer : " + t.getClass().getName() + ": " + t.getMessage(), t);
		}
		//server = (org.apache.catalina.core.StandardServer)org.apache.catalina.ServerFactory.getServer();
		return server;
	}
	
	/*
	 * 
	 */
	private Service getCatalinaService(){
		Service[] services = server.findServices();
		Service found = null;
		for(Service aService : services){
			if(logger.isDebugEnabled()){
				logger.debug("Service: " + aService.getName() + 
						", info: " + aService.getInfo() + 
						", state: " + aService.getStateName());
			}
			
			if(aService.getName().equalsIgnoreCase(CATALINA_SERVICE_NAME)){
				found = aService;
				for(Container c : aService.getContainer().findChildren()){
					logger.debug("Container " + c.getName() + ": " + c.getInfo());
					if(c instanceof Host){
						if(System.getProperties().containsKey("wtp.deploy")){
							this.appBase = System.getProperty("wtp.deploy");
						}else{
							String hostAppBase = ((Host) c).getAppBase();
							File base = new File(hostAppBase);
						    if (!base.isAbsolute()) {
						      base = new File(System.getProperty("catalina.base"), hostAppBase);
						    }
						    this.appBase = base.getAbsolutePath();
						}
						logger.debug("this.APPBASE=" + this.appBase);
						for(Container child : ((Host)c).findChildren()){
							if(child instanceof Context){
								//child.set
								this.contextMap.put(child.getName(), (Context)child);
								logger.debug("Context " + child.getName() + " " + child.getInfo());
								NamingResources nr = ((Context) child).getNamingResources();
								List<ContextResource> contextResources = new ArrayList<ContextResource>();
								for(ContextResource cr : nr.findResources()){
									logger.debug("Context[" + child.getName() + "] = " + cr.getName());
									if(cr instanceof DataSource){
										logger.debug("IS a dataSource ");
									}
									contextResources.add(cr);
								}
								if(contextResources != null && !contextResources.isEmpty()){
									this.contextResources.put(child.getName(), contextResources);
								}
							}
						}
					}
				}
				break;
			}
		}
		return found;
	}
	
	private Connector getHttpConnector() {
		Connector aHttpConnector = null;
		if(this.catalinaService.findConnectors() != null && this.catalinaService.findConnectors().length > 0){
			logger.debug("List of connectors: ");
			for(Connector aConnector : this.catalinaService.findConnectors()){
				if(logger.isDebugEnabled()){
					logger.debug("Connector.getProtocol: " + aConnector.getProtocol());
					logger.debug("Connector.getPort: " + aConnector.getPort());
					logger.debug("Connector.getInfo: " + aConnector.getInfo());
					logger.debug("Connector.getState: " + aConnector.getStateName());
				}
				if(aConnector.getProtocol().equalsIgnoreCase(CONNECTOR_HTTP_PROTOCOL_NAME)){
					aHttpConnector = aConnector;
					if(!logger.isDebugEnabled()){
						break;
					}
				}
			}
		}
		return aHttpConnector;
	}
	
	/* (non-Javadoc)
	 * @see net.cvDis.util.HttpConnectorPort#getHttpConnectorPort()
	 */
	@Override
	public Integer getHttpConnectorPort(){
		return this.httpConnector.getPort();
	}

	@Override
	public String getLocalPortConnectionHost() {
		return "http://localhost:".concat(String.valueOf(this.getHttpConnectorPort()));
	}
	
	
	/* (non-Javadoc)
	 * @see net.iberdok.util.ContextManagement#getContext(java.lang.String)
	 */
	@Override
	public Context getContext(String contextName){
		return this.contextMap.get(contextName);
	}
	
	/* (non-Javadoc)
	 * @see net.iberdok.util.ContextManagement#getContextDataSources(java.lang.String)
	 */
	@Override
	public List<DataSource> getContextDataSources(String contextName){
		List<ContextResource> contextResourceList = this.contextResources.get(contextName);
		if(contextResourceList == null || contextResourceList.isEmpty()){
			return null;
		}
		List<DataSource> lista = new ArrayList<DataSource>();
		try{
			javax.naming.Context init = new javax.naming.InitialContext();
			javax.naming.Context ctx =(javax.naming.Context) init.lookup("java:comp/env");
			for(ContextResource cr : contextResourceList){
				Object o = ctx.lookup(cr.getName());
				if(o instanceof DataSource){
					lista.add((DataSource)o);
				}
			}			
		}catch(Exception e){
			logger.warn("Error accessing DataSources from context " + contextName + ": " + e.getMessage(), e);
			return null;
		}
		return lista;
	}
	
	/* (non-Javadoc)
	 * @see net.iberdok.util.ContextManagement#getContextDataSources(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public List<DataSource> getContextDataSources(HttpServletRequest request){
		return  this.getContextDataSources(request.getContextPath());
	}
	
	public DataSourceGroupStatus getContextDataSourceGroupStatus(HttpServletRequest request){
		List<DataSource> lds = this.getContextDataSources(request);
		DataSourceGroupStatus status = new DataSourceGroupStatus();
		status.setContextName(request.getContextPath());
		if(lds == null || lds.isEmpty()){
			return status;
		}
		int total = 0;
		int idle = 0;
		int active = 0;
		List<DataSourceStatus> list = new ArrayList<DataSourceStatus>();
		for(DataSource ds : lds){
			BasicDataSource basic = (BasicDataSource)ds;
			total = total + basic.getMaxActive();
			idle = idle + basic.getNumIdle();
			active = active + basic.getNumActive();
			DataSourceStatus dss = new DataSourceStatus();
			dss.setTotal(basic.getMaxActive());
			dss.setIdle(basic.getNumIdle());
			dss.setActive(basic.getNumActive());
			dss.setUsage(this.calcUsagePercent(dss.getTotal(), dss.getActive() + dss.getIdle()));
			list.add(dss);
		}
		status.setList(list);
		status.setUsage(this.calcUsagePercent(total, idle + active));
		return status;
	}
	
	/**
	 * 
	 * @param max
	 * @param used
	 * @return
	 */
	private double calcUsagePercent(int max, int used){
		if(max == 0){
			return 0.0;
		}
		return ((double)used / (double)max)*100;
	}
	
	public SystemStatus getSystemStatus(HttpServletRequest req){
		logger.debug("Entrada");
		SystemStatus status = new SystemStatus();
		status.setGeneralStatus(true);
		logger.debug("checkpoint 1");
		status.setComposerStatus(true);
		
		logger.debug("checkpoint 2");
		status.setDataStatus(true);
		
		logger.debug("checkpoint 3");
		DataSourceGroupStatus groupStatus = this.getContextDataSourceGroupStatus(req);
		if(groupStatus.getList() != null && !groupStatus.getList().isEmpty()){
			for(DataSourceStatus dss : groupStatus.getList()){
				status.getPoolDetail().put(dss.getResourceName(), dss.getUsage());
			}
		}
		
		logger.debug("checkpoint 4");
		status.setPoolLoad(groupStatus.getUsage());
		
		logger.debug("checkpoint 5");
		return status;
	}

	@Override
	public String getAppBase() {
		return this.appBase;
	}

	@Override
	public String getServletContextBase(String contextPath) {
		logger.debug("ContextPath received = " + contextPath);
		if(contextPath.startsWith("/")){
			contextPath = contextPath.substring(1);
		}
		return this.appBase
					.concat(System.getProperty("file.separator"))
					.concat((contextPath.contains("/")?contextPath.replace("/", "#"):contextPath));
	}

	
}
