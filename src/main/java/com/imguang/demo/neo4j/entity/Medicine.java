package com.imguang.demo.neo4j.entity;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Medicine {
	
	@GraphId
	private Long graphId;
	
	private String id;
	private String name;
	private String function;
	private String usageDosage;
	private String untowardEffect;
	private String contraindication;
	public Long getGraphId() {
		return graphId;
	}
	public void setGraphId(Long graphId) {
		this.graphId = graphId;
	}
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conponent == null) ? 0 : conponent.hashCode());
		result = prime * result + ((contraindication == null) ? 0 : contraindication.hashCode());
		result = prime * result + ((function == null) ? 0 : function.hashCode());
		result = prime * result + ((graphId == null) ? 0 : graphId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imgUrl == null) ? 0 : imgUrl.hashCode());
		result = prime * result + ((interaction == null) ? 0 : interaction.hashCode());
		result = prime * result + ((mattersNeedAttention == null) ? 0 : mattersNeedAttention.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((untowardEffect == null) ? 0 : untowardEffect.hashCode());
		result = prime * result + ((usageDosage == null) ? 0 : usageDosage.hashCode());
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
		Medicine other = (Medicine) obj;
		if (conponent == null) {
			if (other.conponent != null)
				return false;
		} else if (!conponent.equals(other.conponent))
			return false;
		if (contraindication == null) {
			if (other.contraindication != null)
				return false;
		} else if (!contraindication.equals(other.contraindication))
			return false;
		if (function == null) {
			if (other.function != null)
				return false;
		} else if (!function.equals(other.function))
			return false;
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
		if (imgUrl == null) {
			if (other.imgUrl != null)
				return false;
		} else if (!imgUrl.equals(other.imgUrl))
			return false;
		if (interaction == null) {
			if (other.interaction != null)
				return false;
		} else if (!interaction.equals(other.interaction))
			return false;
		if (mattersNeedAttention == null) {
			if (other.mattersNeedAttention != null)
				return false;
		} else if (!mattersNeedAttention.equals(other.mattersNeedAttention))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (untowardEffect == null) {
			if (other.untowardEffect != null)
				return false;
		} else if (!untowardEffect.equals(other.untowardEffect))
			return false;
		if (usageDosage == null) {
			if (other.usageDosage != null)
				return false;
		} else if (!usageDosage.equals(other.usageDosage))
			return false;
		return true;
	}
	
	
	
}
