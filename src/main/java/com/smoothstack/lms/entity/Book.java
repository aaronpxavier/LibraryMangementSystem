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
	private List<Genre> genres;
	private Publisher publisher;

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

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	@Override
	public String toString() {
		final String NEW_LINE = System.getProperty("line.separator");
		String resultString = "\tTitle: " + title + "\tISBN: " + isbn;
		if(authors != null) {
			resultString += NEW_LINE + "\t";
			for (Author author : authors) {
				resultString +=  author + ", ";
			}
		}
		if (genres != null) {
			resultString += NEW_LINE + "\t";
			for (Genre genre : genres) {
				resultString += genre + ", ";
			}
		}
		if (publisher != null) {
			resultString += NEW_LINE + "\t" + publisher;
		}
		return resultString;
	}
}

