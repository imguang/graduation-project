package com.imguang.demo.clean.paper;

import java.util.List;

/**
 * @author 书生
 *
 */
public class TemPaper {

	private String id;
	private String title;
	private String abContent;
	private String lan_type;
	private String author;
	private String local_path;
	private String link_path;
	private String key_word;
	private List<String> citedReferenceIds;
	private String DOI;
	
	public List<String> getCitedReferenceIds() {
		return citedReferenceIds;
	}
	public void setCitedReferenceIds(List<String> citedReferenceIds) {
		this.citedReferenceIds = citedReferenceIds;
	}
	public String getDOI() {
		return DOI;
	}
	public void setDOI(String dOI) {
		DOI = dOI;
	}
	
	public String getKey_word() {
		return key_word;
	}
	public void setKey_word(String key_word) {
		this.key_word = key_word;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getLocal_path() {
		return local_path;
	}
	public void setLocal_path(String local_path) {
		this.local_path = local_path;
	}
	public String getLink_path() {
		return link_path;
	}
	public void setLink_path(String link_path) {
		this.link_path = link_path;
	}
	public String getTitle() {
		return title;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAbContent() {
		return abContent;
	}
	public void setAbContent(String abContent) {
		this.abContent = abContent;
	}
	public String getLan_type() {
		return lan_type;
	}
	
	public void setLan_type(String lan_type) {
		this.lan_type = lan_type;
	}
	@Override
	public String toString() {
		return "TemPaper [id=" + id + ", title=" + title + ", abContent=" + abContent + ", lan_type=" + lan_type
				+ ", author=" + author + ", local_path=" + local_path + ", link_path=" + link_path + ", key_word="
				+ key_word + ", citedReferenceIds=" + citedReferenceIds + ", DOI=" + DOI + "]";
	}
	
}
