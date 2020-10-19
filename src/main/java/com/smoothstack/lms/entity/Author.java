/**
 * 
 */
package com.smoothstack.lms.entity;

import java.util.List;

/**
 * @author ppradhan
 *
 */
public class Author {
	
	private Integer authorId;
	private String authorName;
	private List<Book> books;
	
	public Author(Integer authorId, String authorName) {
		this.authorId = authorId;
		this.authorName = authorName;
	}
	public Author(String authorName) {
		this.authorName = authorName;
	}
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "Author: " + authorName;
	}
}
