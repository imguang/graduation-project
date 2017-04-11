package com.imguang.demo.neo4j.entity;

public class Disease {

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

	public String getPrevent() {
		return prevent;
	}

	public void setPrevent(String prevent) {
		this.prevent = prevent;
	}

	public String getNursing() {
		return nursing;
	}

	public void setNursing(String nursing) {
		this.nursing = nursing;
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

	@Override
	public String toString() {
		return "Disease [id=" + id + ", diseaseName=" + diseaseName + ", abstractContent=" + abstractContent
				+ ", isInsurance=" + isInsurance + ", sicknessRate=" + sicknessRate + ", recoveryRate=" + recoveryRate
				+ ", etiology=" + etiology + ", treatmentDetail=" + treatmentDetail + ", treatmentMethod="
				+ treatmentMethod + ", treatmentCycle=" + treatmentCycle + ", departments=" + departments
				+ ", infectionMode=" + infectionMode + ", susceptiblePopulation=" + susceptiblePopulation
				+ ", treatmentCost=" + treatmentCost + ", nursing=" + nursing + ", prevent=" + prevent + "]";
	}

}
