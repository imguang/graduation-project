package com.imguang.demo.spider.type;

public enum DiseaseType {
//	public static final String prevent = "http://jib.xywy.com/il_sii/prevent/{id}.htm";
//	public static final String neopathy = "http://jib.xywy.com/il_sii/neopathy/{id}.htm";
//	public static final String symptom = "http://jib.xywy.com/il_sii/symptom/{id}.htm";
//	public static final String treat = "http://jib.xywy.com/il_sii/treat/{id}.htm";
//	public static final String nursing = "http://jib.xywy.com/il_sii/nursing/{id}.htm";

	ABSTRACTS("http://jib.xywy.com/il_sii/gaishu/{id}.htm"),
	CAUSE("http://jib.xywy.com/il_sii/cause/{id}.htm"),
	PREVENT("http://jib.xywy.com/il_sii/prevent/{id}.htm"),
	NEOPATHY("http://jib.xywy.com/il_sii/neopathy/{id}.htm"),
	SYMPTOM("http://jib.xywy.com/il_sii/symptom/{id}.htm"),
	TREAT("http://jib.xywy.com/il_sii/treat/{id}.htm"),
	NURSING("http://jib.xywy.com/il_sii/nursing/{id}.htm");
	String url ;
	DiseaseType(String url){
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
