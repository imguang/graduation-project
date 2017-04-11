package com.imguang.demo.mysql.model;

public class XywySymptomUrl {
    private Integer id;

    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

	@Override
	public String toString() {
		return "XywySymptomUrl [id=" + id + ", url=" + url + "]";
	}
    
}