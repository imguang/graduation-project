package com.imguang.demo.spider.type;

/**
 * @author 书生
 * 病因抓取枚举类
 */
public enum SymptomType {
	//http://zzk.xywy.com/5513_jieshao.html
	//http://zzk.xywy.com/5513_yuanyin.html
	//http://zzk.xywy.com/5513_yufang.html
	ABSTRACTS("http://zzk.xywy.com/{id}_jieshao.html"),
	CAUSE("http://zzk.xywy.com/{id}_yuanyin.html"),
	PREVENT("http://zzk.xywy.com/{id}_yufang.html"),
	IMG("http://zzk.xywy.com/{id}_gaishu.html");
	
	String url;
	SymptomType(String url){
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
