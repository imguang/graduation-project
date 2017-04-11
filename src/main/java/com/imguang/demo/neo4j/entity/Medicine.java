package com.imguang.demo.neo4j.entity;

public class Medicine {
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
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	@Override
	public String toString() {
		return "Medicine [id=" + id + ", name=" + name + ", function=" + function + ", usageDosage=" + usageDosage
				+ ", untowardEffect=" + untowardEffect + ", contraindication=" + contraindication
				+ ", mattersNeedAttention=" + mattersNeedAttention + ", conponent=" + conponent + ", interaction="
				+ interaction + ", price=" + price + ", imgUrl=" + imgUrl + "]";
	}
	
	
}
