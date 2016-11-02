/**
 * 
 */
package com.eej.srv.context.model;

import java.io.Serializable;
import com.eej.srv.ApplicationVersion;



/**
 * @author jlumietu - Mikel Ibiricu Alfaro
 *
 */
public class DataSourceStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = ApplicationVersion.APP_VERSION;
	
	private String resourceName;
	
	private int total;
	
	private int idle;
	
	private int active;
	
	private double usage;

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return the idle
	 */
	public int getIdle() {
		return idle;
	}

	/**
	 * @param idle the idle to set
	 */
	public void setIdle(int idle) {
		this.idle = idle;
	}

	/**
	 * @return the active
	 */
	public int getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(int active) {
		this.active = active;
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
