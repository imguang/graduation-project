package com.imguang.demo.spider.entity;

public class Symptom {

	private String id;
	private String abstractContent;
	private String cause;
	private String prevent;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAbstractContent() {
		return abstractContent;
	}
	public void setAbstractContent(String abstractContent) {
		this.abstractContent = abstractContent;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getPrevent() {
		return prevent;
	}
	public void setPrevent(String prevent) {
		this.prevent = prevent;
	}
	@Override
	public String toString() {
		return "Symptom [id=" + id + ", abstractContent=" + abstractContent + ", cause=" + cause + ", prevent="
				+ prevent + "]";
	}
}
