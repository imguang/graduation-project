package com.imguang.demo.search.entity;

import org.apache.lucene.document.Document;

public class HitsEntity implements Comparable<HitsEntity>{

	private String id;
	private Long graphId;
	private String name;
	private String flag;
	private Float score;
	
	public HitsEntity() {

	}
	
	public HitsEntity(Document document,Float score){
		setId(document.get("id"));
		setFlag(document.get("flag"));
		setGraphId(document.get("graphId"));
		setName(document.get("name"));
		setScore(score);
	}
	
	
	
	public String getId() {
		return id;
	}
	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public void setGraphId(Long graphId) {
		this.graphId = graphId;
	}

	public void setId(String id) {
		this.id = id;
	}
	public Long getGraphId() {
		return graphId;
	}
	public void setGraphId(String graphId) {
		this.graphId = Long.valueOf(graphId);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
	@Override
	public String toString() {
		return "\nHitsEntity [\nid=" + id + "\ngraphId=" + graphId + "\nname=" + name + "\nflag=" + flag + "\nscore="
				+ score + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((graphId == null) ? 0 : graphId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		HitsEntity other = (HitsEntity) obj;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
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
		return true;
	}

	@Override
	public int compareTo(HitsEntity o) {
		if(o.getScore() > this.getScore())
			return 1;
		if(o.getScore() < this.getScore())
			return -1;
		return 0;
	}
	
}
