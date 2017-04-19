package com.imguang.demo.neo4j.entity;

import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Disease {

	@GraphId
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

	@Relationship(type="disease-medicine",direction=Relationship.UNDIRECTED)
	private Set<Medicine> medicines;

	@Relationship(type="disease-symptom",direction=Relationship.UNDIRECTED)
	private Set<Symptom> symptoms;
	
	@Relationship(type="neopathy",direction=Relationship.UNDIRECTED)
	private Set<Disease> Disease;
	
	

	public Set<Disease> getDisease() {
		return Disease;
	}

	public void setDisease(Set<Disease> disease) {
		Disease = disease;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abstractContent == null) ? 0 : abstractContent.hashCode());
		result = prime * result + ((departments == null) ? 0 : departments.hashCode());
		result = prime * result + ((diseaseName == null) ? 0 : diseaseName.hashCode());
		result = prime * result + ((etiology == null) ? 0 : etiology.hashCode());
		result = prime * result + ((graphId == null) ? 0 : graphId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((infectionMode == null) ? 0 : infectionMode.hashCode());
		result = prime * result + ((isInsurance == null) ? 0 : isInsurance.hashCode());
		result = prime * result + ((nursing == null) ? 0 : nursing.hashCode());
		result = prime * result + ((prevent == null) ? 0 : prevent.hashCode());
		result = prime * result + ((recoveryRate == null) ? 0 : recoveryRate.hashCode());
		result = prime * result + ((sicknessRate == null) ? 0 : sicknessRate.hashCode());
		result = prime * result + ((susceptiblePopulation == null) ? 0 : susceptiblePopulation.hashCode());
		result = prime * result + ((treatmentCost == null) ? 0 : treatmentCost.hashCode());
		result = prime * result + ((treatmentCycle == null) ? 0 : treatmentCycle.hashCode());
		result = prime * result + ((treatmentDetail == null) ? 0 : treatmentDetail.hashCode());
		result = prime * result + ((treatmentMethod == null) ? 0 : treatmentMethod.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disease other = (Disease) obj;
		
		if (graphId == null) {
			if (other.graphId != null)
				return false;
		} else if (!graphId.equals(other.graphId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getGraphId() {
		return graphId;
	}

	public void setGraphId(Long graphId) {
		this.graphId = graphId;
	}

	public Set<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(Set<Medicine> medicines) {
		this.medicines = medicines;
	}

	public Set<Symptom> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(Set<Symptom> symptoms) {
		this.symptoms = symptoms;
	}

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
		return "Disease [graphId=" + graphId + ", id=" + id + ", diseaseName=" + diseaseName + ", abstractContent="
				+ abstractContent + ", isInsurance=" + isInsurance + ", sicknessRate=" + sicknessRate
				+ ", recoveryRate=" + recoveryRate + ", etiology=" + etiology + ", treatmentDetail=" + treatmentDetail
				+ ", treatmentMethod=" + treatmentMethod + ", treatmentCycle=" + treatmentCycle + ", departments="
				+ departments + ", infectionMode=" + infectionMode + ", susceptiblePopulation=" + susceptiblePopulation
				+ ", treatmentCost=" + treatmentCost + ", nursing=" + nursing + ", prevent=" + prevent + ", medicines="
				+ medicines + ", symptoms=" + symptoms + ", Disease=" + Disease + "]";
	}

}
