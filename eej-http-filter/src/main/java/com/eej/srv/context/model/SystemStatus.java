/**
 * 
 */
package com.eej.srv.context.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.eej.srv.ApplicationVersion;



/**
 * @author jlumietu
 *
 */
public class SystemStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = ApplicationVersion.APP_VERSION;
	
	private boolean generalStatus = false;
	
	private boolean composerStatus = false;
	
	private boolean dataStatus = false;
	
	private double poolLoad = 0.0; 
	
	private Map<String, Double> poolDetail = new HashMap<String,Double>();

	/**
	 * @return the generalStatus
	 */
	public boolean isGeneralStatus() {
		return generalStatus;
	}

	/**
	 * @param generalStatus the generalStatus to set
	 */
	public void setGeneralStatus(boolean generalStatus) {
		this.generalStatus = generalStatus;
	}

	/**
	 * @return the componserStatus
	 */
	public boolean isComposerStatus() {
		return composerStatus;
	}

	/**
	 * @param componserStatus the componserStatus to set
	 */
	public void setComposerStatus(boolean composerStatus) {
		this.composerStatus = composerStatus;
	}

	/**
	 * @return the dataStatus
	 */
	public boolean isDataStatus() {
		return dataStatus;
	}

	/**
	 * @param dataStatus the dataStatus to set
	 */
	public void setDataStatus(boolean dataStatus) {
		this.dataStatus = dataStatus;
	}

	/**
	 * @return the poolLoad
	 */
	public double getPoolLoad() {
		return poolLoad;
	}

	/**
	 * @param poolLoad the poolLoad to set
	 */
	public void setPoolLoad(double poolLoad) {
		this.poolLoad = poolLoad;
	}

	/**
	 * @return the poolDetail
	 */
	public Map<String, Double> getPoolDetail() {
		return poolDetail;
	}

	/**
	 * @param poolDetail the poolDetail to set
	 */
	public void setPoolDetail(Map<String, Double> poolDetail) {
		this.poolDetail = poolDetail;
	}
	
	
}
