package com.imguang.demo.web.controller.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.imguang.demo.neo4j.entity.Disease;
import com.imguang.demo.neo4j.entity.Symptom;

public class SymptomSearchResultVO extends BaseSearchResultVO {

	private Long graphId;
	private String id;
	private String name;
	private String abstractContent;
	private String cause;
	private String prevent;
	private String imgUrl;
	
	public SymptomSearchResultVO() {
		super();
		super.setFlag(BaseSearchResultVO.SYMPTOM);
	}
	
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
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public void setSymptom(Symptom symptom){
		this.graphId = symptom.getGraphId();
		this.id = symptom.getId();
		this.name = symptom.getName();
		this.abstractContent = symptom.getAbstractContent();
		this.cause = symptom.getCause();
		this.prevent = symptom.getPrevent();
		this.imgUrl = symptom.getImgUrl();
		super.setDiseaseRelations(new ArrayList<>());
		Set<Disease> diseases = symptom.getDiseases();
		if(diseases != null){
			for (Disease disease : diseases) {
				BaseEntity baseEntity = new BaseEntity();
				super.getDiseaseRelations().add(baseEntity);
				baseEntity.setGraphId(disease.getGraphId());
				baseEntity.setId(disease.getId());
				baseEntity.setImgUrl(disease.getImgUrl());
				baseEntity.setName(disease.getDiseaseName());
			}
		}
	}
	
	public void setSymptom(Symptom symptom,List<BaseEntity> diseases){
		setSymptom(symptom);
		super.setDiseaseRelations(diseases);
	}
	
}
