/**
 * 
 */
package com.eej.srv.context.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author jlumietu - Mikel Ibiricu Alfaro
 *
 */
public class DataSourceGroupStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String contextName;
	
	private List<DataSourceStatus> list;
	
	private double usage;

	/**
	 * @return the contextName
	 */
	public String getContextName() {
		return contextName;
	}

	/**
	 * @param contextName the contextName to set
	 */
	public void setContextName(String contextName) {
		this.contextName = contextName;
	}

	/**
	 * @return the list
	 */
	public List<DataSourceStatus> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<DataSourceStatus> list) {
		this.list = list;
	}

	/**
	 * @return the usage
	 */
	public double getUsage() {
		return usage;
	}

	/**
	 * @param usage the usage to set
	 */
	public void setUsage(double usage) {
		this.usage = usage;
	}	

}
