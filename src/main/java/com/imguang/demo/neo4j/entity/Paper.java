package com.imguang.demo.neo4j.entity;

import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Paper {

	@GraphId
	private Long graphId;
	
	private String author;
	private String authorEN;
	private String title;
	private String titleEN;
	private String publisher;
	private String publisherEN;
	private String abstracts;
	private String abstractsEN;
	private String keyWords;
	private String keyWordsEN;
	private String references;
	private String DOI;
	private Integer publishYear;
	private Integer beginPage;
	private Integer pageNum;
	private Integer citedNum;
	private Integer referencesNum;
	
	@Relationship(type="references",direction=Relationship.OUTGOING)
	private Set<Paper> papers;
	
	public Long getGraphId() {
		return graphId;
	}
	public void setGraphId(Long graphId) {
		this.graphId = graphId;
	}
	public Set<Paper> getPapers() {
		return papers;
	}
	public void setPapers(Set<Paper> papers) {
		this.papers = papers;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthorEN() {
		return authorEN;
	}
	public void setAuthorEN(String authorEN) {
		this.authorEN = authorEN;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitleEN() {
		return titleEN;
	}
	public void setTitleEN(String titleEN) {
		this.titleEN = titleEN;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getPublisherEN() {
		return publisherEN;
	}
	public void setPublisherEN(String publisherEN) {
		this.publisherEN = publisherEN;
	}
	public String getAbstracts() {
		return abstracts;
	}
	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}
	public String getAbstractsEN() {
		return abstractsEN;
	}
	public void setAbstractsEN(String abstractsEN) {
		this.abstractsEN = abstractsEN;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getKeyWordsEN() {
		return keyWordsEN;
	}
	public void setKeyWordsEN(String keyWordsEN) {
		this.keyWordsEN = keyWordsEN;
	}
	public String getReferences() {
		return references;
	}
	public void setReferences(String references) {
		this.references = references;
	}
	public String getDOI() {
		return DOI;
	}
	public void setDOI(String dOI) {
		DOI = dOI;
	}
	public Integer getPublishYear() {
		return publishYear;
	}
	public void setPublishYear(Integer publishYear) {
		this.publishYear = publishYear;
	}
	public Integer getBeginPage() {
		return beginPage;
	}
	public void setBeginPage(Integer beginPage) {
		this.beginPage = beginPage;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getCitedNum() {
		return citedNum;
	}
	public void setCitedNum(Integer citedNum) {
		this.citedNum = citedNum;
	}
	public Integer getReferencesNum() {
		return referencesNum;
	}
	public void setReferencesNum(Integer referencesNum) {
		this.referencesNum = referencesNum;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((DOI == null) ? 0 : DOI.hashCode());
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((beginPage == null) ? 0 : beginPage.hashCode());
		result = prime * result + ((pageNum == null) ? 0 : pageNum.hashCode());
		result = prime * result + ((publishYear == null) ? 0 : publishYear.hashCode());
		result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Paper other = (Paper) obj;
		if (DOI == null) {
			if (other.DOI != null)
				return false;
		} else if (!DOI.equals(other.DOI))
			return false;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (beginPage == null) {
			if (other.beginPage != null)
				return false;
		} else if (!beginPage.equals(other.beginPage))
			return false;
		if (pageNum == null) {
			if (other.pageNum != null)
				return false;
		} else if (!pageNum.equals(other.pageNum))
			return false;
		if (publishYear == null) {
			if (other.publishYear != null)
				return false;
		} else if (!publishYear.equals(other.publishYear))
			return false;
		if (publisher == null) {
			if (other.publisher != null)
				return false;
		} else if (!publisher.equals(other.publisher))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Paper [author=" + author + "\nauthorEN=" + authorEN + "\ntitle=" + title + "\ntitleEN=" + titleEN
				+ "\npublisher=" + publisher + "\npublisherEN=" + publisherEN + "\nabstracts=" + abstracts
				+ "\nabstractsEN=" + abstractsEN + "\nkeyWords=" + keyWords + "\nkeyWordsEN=" + keyWordsEN
				+ "\nreferences=" + references + "\nDOI=" + DOI + "\npublishYear=" + publishYear + "\nbeginPage="
				+ beginPage + "\npageNum=" + pageNum + "\ncitedNum=" + citedNum + "\nreferencesNum=" + referencesNum
				+ "]";
	}
	
	
	
}
