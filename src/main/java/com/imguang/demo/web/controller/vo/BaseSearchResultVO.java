package com.imguang.demo.web.controller.vo;

import java.util.List;

public class BaseSearchResultVO {
	private List<String> searchRelations;
	private List<BaseEntity> diseaseRelations;
	private List<BaseEntity> symptomRelations;
	private List<BaseEntity> medicineRelations;
	public List<String> getSearchRelations() {
		return searchRelations;
	}
	public void setSearchRelations(List<String> searchRelations) {
		this.searchRelations = searchRelations;
	}
	public List<BaseEntity> getDiseaseRelations() {
		return diseaseRelations;
	}
	public void setDiseaseRelations(List<BaseEntity> diseaseRelations) {
		this.diseaseRelations = diseaseRelations;
	}
	public List<BaseEntity> getSymptomRelations() {
		return symptomRelations;
	}
	public void setSymptomRelations(List<BaseEntity> symptomRelations) {
		this.symptomRelations = symptomRelations;
	}
	public List<BaseEntity> getMedicineRelations() {
		return medicineRelations;
	}
	public void setMedicineRelations(List<BaseEntity> medicineRelations) {
		this.medicineRelations = medicineRelations;
	}
	
	
}
