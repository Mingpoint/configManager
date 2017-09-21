package org.configManager.domain;

import java.util.ArrayList;
import java.util.List;

public class TreeListDTO {
	private Integer id;
	private Integer pid;
	private String name;
	private List<TreeListDTO> children = new ArrayList<TreeListDTO>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TreeListDTO> getChildren() {
		return children;
	}
	public void setChildren(List<TreeListDTO> children) {
		this.children = children;
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
