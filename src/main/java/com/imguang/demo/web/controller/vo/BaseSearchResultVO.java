package com.imguang.demo.web.controller.vo;

import java.util.List;

import com.imguang.demo.neo4j.entity.Paper;

public class BaseSearchResultVO {
	private String flag;
	private List<String> searchRelations;
	private List<BaseEntity> diseaseRelations;
	private List<BaseEntity> symptomRelations;
	private List<BaseEntity> medicineRelations;
	private List<Paper> papers;
	private List<String> words;
	public static final String DISEASE = "disease";
	public static final String SYMPTOM = "symptom";
	public static final String MEDICINE = "medicine";
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
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
	public List<Paper> getPapers() {
		return papers;
	}
	public void setPapers(List<Paper> papers) {
		this.papers = papers;
	}
	public List<String> getWords() {
		return words;
	}
	public void setWords(List<String> words) {
		this.words = words;
	}
	
}
