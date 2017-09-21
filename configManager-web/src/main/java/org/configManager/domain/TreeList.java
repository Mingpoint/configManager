package org.configManager.domain;

import java.io.Serializable;

public class TreeList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pStr;//父节点
	private String str;//节点
	private Integer id;
	private Integer pid;
	public String getpStr() {
		return pStr;
	}
	public void setpStr(String pStr) {
		this.pStr = pStr;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}

}
