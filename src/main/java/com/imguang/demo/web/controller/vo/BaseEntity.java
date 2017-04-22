package com.imguang.demo.web.controller.vo;

public class BaseEntity {
	private Long graphId;
	private String id;
	private String name;
	private String ImgUrl;
	public Long getGraphId() {
		return graphId;
	}
	public void setGraphId(Long graphId) {
		this.graphId = graphId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgUrl() {
		return ImgUrl;
	}
	public void setImgUrl(String imgUrl) {
		ImgUrl = imgUrl;
	}
	@Override
	public String toString() {
		return "BaseEntity [graphId=" + graphId + ", id=" + id + ", name=" + name + ", ImgUrl=" + ImgUrl + "]";
	}
	
	
	
}
