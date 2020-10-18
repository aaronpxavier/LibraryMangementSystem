package com.smoothstack.lms.entity;

import java.util.List;

/**
 * @author ppradhan
 *
 */
public class Book {
	private Integer bookId;
	private String title;
	private List<Author> authors;
	private String isbn;
//	private List<Genre> genres;
//	private List<Branch> branches;
//	private Publisher publisher;
	public Book(Integer bookId, String title) {
		super();
		this.bookId = bookId;
		this.title = title;
	}
	public Book(String title) {
		this.title = title;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Author> getAuthors() {
		return authors;
	}
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	public void setAuthor(Author author) { this.authors.add(author); }
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getIsbn() {
		return isbn;
	}
}
