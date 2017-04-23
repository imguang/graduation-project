package com.imguang.demo.web.controller.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.imguang.demo.neo4j.entity.Disease;
import com.imguang.demo.neo4j.entity.Medicine;
import com.imguang.demo.neo4j.entity.Symptom;

public class DiseaseSearchResultVO extends BaseSearchResultVO {
	private Long graphId;
	private String id;
	private String diseaseName;
	private String abstractContent;
	private String isInsurance;
	private String sicknessRate;
	private String recoveryRate;
	private String etiology;
	private String treatmentDetail;
	private String treatmentMethod;
	private String treatmentCycle;
	private String departments;
	private String infectionMode;
	private String susceptiblePopulation;
	private String treatmentCost;
	private String nursing;
	private String prevent;
	private String imgUrl;
	
	public DiseaseSearchResultVO() {
		super();
		super.setFlag(BaseSearchResultVO.DISEASE);
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
	public String getDiseaseName() {
		return diseaseName;
	}
	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}
	public String getAbstractContent() {
		return abstractContent;
	}
	public void setAbstractContent(String abstractContent) {
		this.abstractContent = abstractContent;
	}
	public String getIsInsurance() {
		return isInsurance;
	}
	public void setIsInsurance(String isInsurance) {
		this.isInsurance = isInsurance;
	}
	public String getSicknessRate() {
		return sicknessRate;
	}
	public void setSicknessRate(String sicknessRate) {
		this.sicknessRate = sicknessRate;
	}
	public String getRecoveryRate() {
		return recoveryRate;
	}
	public void setRecoveryRate(String recoveryRate) {
		this.recoveryRate = recoveryRate;
	}
	public String getEtiology() {
		return etiology;
	}
	public void setEtiology(String etiology) {
		this.etiology = etiology;
	}
	public String getTreatmentDetail() {
		return treatmentDetail;
	}
	public void setTreatmentDetail(String treatmentDetail) {
		this.treatmentDetail = treatmentDetail;
	}
	public String getTreatmentMethod() {
		return treatmentMethod;
	}
	public void setTreatmentMethod(String treatmentMethod) {
		this.treatmentMethod = treatmentMethod;
	}
	public String getTreatmentCycle() {
		return treatmentCycle;
	}
	public void setTreatmentCycle(String treatmentCycle) {
		this.treatmentCycle = treatmentCycle;
	}
	public String getDepartments() {
		return departments;
	}
	public void setDepartments(String departments) {
		this.departments = departments;
	}
	public String getInfectionMode() {
		return infectionMode;
	}
	public void setInfectionMode(String infectionMode) {
		this.infectionMode = infectionMode;
	}
	public String getSusceptiblePopulation() {
		return susceptiblePopulation;
	}
	public void setSusceptiblePopulation(String susceptiblePopulation) {
		this.susceptiblePopulation = susceptiblePopulation;
	}
	public String getTreatmentCost() {
		return treatmentCost;
	}
	public void setTreatmentCost(String treatmentCost) {
		this.treatmentCost = treatmentCost;
	}
	public String getNursing() {
		return nursing;
	}
	public void setNursing(String nursing) {
		this.nursing = nursing;
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
	@Override
	public String toString() {
		return "BaseDiseaseSearchResultVO [graphId=" + graphId + ", id=" + id + ", diseaseName=" + diseaseName
				+ ", abstractContent=" + abstractContent + ", isInsurance=" + isInsurance + ", sicknessRate="
				+ sicknessRate + ", recoveryRate=" + recoveryRate + ", etiology=" + etiology + ", treatmentDetail="
				+ treatmentDetail + ", treatmentMethod=" + treatmentMethod + ", treatmentCycle=" + treatmentCycle
				+ ", departments=" + departments + ", infectionMode=" + infectionMode + ", susceptiblePopulation="
				+ susceptiblePopulation + ", treatmentCost=" + treatmentCost + ", nursing=" + nursing + ", prevent="
				+ prevent + ", imgUrl=" + imgUrl + "]";
	}
	
	public void setDisease(Disease disease){
		//基本信息转存
		graphId = disease.getGraphId();
		id = disease.getId();
		diseaseName = disease.getDiseaseName();
		abstractContent = disease.getAbstractContent();
		isInsurance = disease.getIsInsurance();
		sicknessRate = disease.getSicknessRate();
		recoveryRate = disease.getRecoveryRate();
		etiology = disease.getEtiology();
		treatmentDetail = disease.getTreatmentDetail();
		treatmentMethod = disease.getTreatmentMethod();
		treatmentCycle = disease.getTreatmentCycle();
		departments = disease.getDepartments();
		infectionMode = disease.getInfectionMode();
		susceptiblePopulation = disease.getSusceptiblePopulation();
		treatmentCost = disease.getTreatmentCost();
		nursing = disease.getNursing();
		prevent = disease.getPrevent();
		imgUrl = disease.getImgUrl();
		//关联信息
		Set<Disease> diseases = disease.getDisease();
		Set<Symptom> symptoms = disease.getSymptoms();
		Set<Medicine> medicines = disease.getMedicines();
		List<BaseEntity> diseaseRelations = new ArrayList<>();
		List<BaseEntity> symptomsRelations = new ArrayList<>();
		List<BaseEntity> medicineRelations = new ArrayList<>();
		super.setDiseaseRelations(diseaseRelations);
		super.setSymptomRelations(symptomsRelations);
		super.setMedicineRelations(medicineRelations);
		if(medicines != null){
			for (Medicine medicine : medicines) {
				BaseEntity baseEntity = new BaseEntity();
				baseEntity.setGraphId(medicine.getGraphId());
				baseEntity.setId(medicine.getId());
				baseEntity.setImgUrl(medicine.getImgUrl());
				baseEntity.setName(medicine.getName());
				medicineRelations.add(baseEntity);
			}
		}
		if(diseases != null){
			for (Disease temDisease : diseases) {
				BaseEntity baseEntity = new BaseEntity();
				baseEntity.setGraphId(temDisease.getGraphId());
				baseEntity.setId(temDisease.getId());
				baseEntity.setImgUrl(temDisease.getImgUrl());
				baseEntity.setName(temDisease.getDiseaseName());
				diseaseRelations.add(baseEntity);
			}
		}
		if(symptoms != null){
			for (Symptom symptom : symptoms) {
				BaseEntity baseEntity = new BaseEntity();
				baseEntity.setGraphId(symptom.getGraphId());
				baseEntity.setId(symptom.getId());
				baseEntity.setImgUrl(symptom.getImgUrl());
				baseEntity.setName(symptom.getName());
				symptomsRelations.add(baseEntity);
			}
		}
	}
	
	
}
