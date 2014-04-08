package models;

import java.util.List;

public class VMModelON extends VMModel {
	private VirtualNetworkON virtualNetworkInfo;
	private List<VirtualDiskON> virtualDisks;
	private double requestedCPU;
	private double requestedCores;
	private int requestedMemory;
	private String vmTemplateInfo;
	private String hostServerHostname;

	public VMModelON(int id, String name) {
		super(id, name);

	}

	/**
	 * @return the virtualNetworkInfo
	 */
	public VirtualNetworkON getVirtualNetworkInfo() {
		return virtualNetworkInfo;
	}

	/**
	 * @param virtualNetworkInfo
	 *            the virtualNetworkInfo to set
	 */
	public void setVirtualNetworkInfo(VirtualNetworkON virtualNetworkInfo) {
		this.virtualNetworkInfo = virtualNetworkInfo;
	}

	/**
	 * @return the requestedCPU
	 */
	public double getRequestedCPU() {
		return requestedCPU;
	}

	/**
	 * @param requestedCPU
	 *            the requestedCPU to set
	 */
	public void setRequestedCPU(double requestedCPU) {
		this.requestedCPU = requestedCPU;
	}

	/**
	 * @return the requestedCores
	 */
	public double getRequestedCores() {
		return requestedCores;
	}

	/**
	 * @param requestedCores
	 *            the requestedCores to set
	 */
	public void setRequestedCores(double requestedCores) {
		this.requestedCores = requestedCores;
	}

	/**
	 * @return the requestedMemory
	 */
	public int getRequestedMemory() {
		return requestedMemory;
	}

	/**
	 * @param requestedMemory
	 *            the requestedMemory to set
	 */
	public void setRequestedMemory(int requestedMemory) {
		this.requestedMemory = requestedMemory;
	}

	/**
	 * @return the vmTemplateInfo
	 */
	public String getVmTemplateInfo() {
		return vmTemplateInfo;
	}

	/**
	 * @param vmTemplateInfo
	 *            the vmTemplateInfo to set
	 */
	public void setVmTemplateInfo(String vmTemplateInfo) {
		this.vmTemplateInfo = vmTemplateInfo;
	}

	/**
	 * @return the hostServerHostname
	 */
	public String getHostServerHostname() {
		return hostServerHostname;
	}

	/**
	 * @param hostServerHostname
	 *            the hostServerHostname to set
	 */
	public void setHostServerHostname(String hostServerHostname) {
		this.hostServerHostname = hostServerHostname;
	}

	/**
	 * @return the virtualDisks
	 */
	public List<VirtualDiskON> getVirtualDisks() {
		return virtualDisks;
	}

	/**
	 * @param virtualDisks
	 *            the virtualDisks to set
	 */
	public void setVirtualDisks(List<VirtualDiskON> virtualDisks) {
		this.virtualDisks = virtualDisks;
	}

}
