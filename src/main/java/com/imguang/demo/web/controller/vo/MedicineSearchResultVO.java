package com.imguang.demo.web.controller.vo;

import java.util.List;

import com.imguang.demo.neo4j.entity.Medicine;

public class MedicineSearchResultVO extends BaseSearchResultVO {

	private Long graphId;
	private String id;
	private String name;
	private String function;
	private String usageDosage;
	private String untowardEffect;
	private String contraindication;
	private String mattersNeedAttention;
	private String conponent;
	private String interaction;
	private String price;
	private String imgUrl;

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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getUsageDosage() {
		return usageDosage;
	}

	public void setUsageDosage(String usageDosage) {
		this.usageDosage = usageDosage;
	}

	public String getUntowardEffect() {
		return untowardEffect;
	}

	public void setUntowardEffect(String untowardEffect) {
		this.untowardEffect = untowardEffect;
	}

	public String getContraindication() {
		return contraindication;
	}

	public void setContraindication(String contraindication) {
		this.contraindication = contraindication;
	}

	public String getMattersNeedAttention() {
		return mattersNeedAttention;
	}

	public void setMattersNeedAttention(String mattersNeedAttention) {
		this.mattersNeedAttention = mattersNeedAttention;
	}

	public String getConponent() {
		return conponent;
	}

	public void setConponent(String conponent) {
		this.conponent = conponent;
	}

	public String getInteraction() {
		return interaction;
	}

	public void setInteraction(String interaction) {
		this.interaction = interaction;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setMedicine(Medicine medicine) {
		this.graphId = medicine.getGraphId();
		this.id = medicine.getId();
		this.name = medicine.getName();
		this.imgUrl = medicine.getImgUrl();
		this.function = medicine.getFunction();
		this.usageDosage = medicine.getUsageDosage();
		this.untowardEffect = medicine.getUntowardEffect();
		this.contraindication = medicine.getContraindication();
		this.mattersNeedAttention = medicine.getMattersNeedAttention();
		this.conponent = medicine.getConponent();
		this.interaction = medicine.getInteraction();
		this.price = medicine.getPrice();
	}

	public void setMedicine(Medicine medicine, List<BaseEntity> diseases) {
		setMedicine(medicine);
		super.setDiseaseRelations(diseases);
	}

}
