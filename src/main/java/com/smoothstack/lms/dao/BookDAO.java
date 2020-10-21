/**
 * 
 */
package com.smoothstack.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.smoothstack.lms.entity.Book;
import com.smoothstack.lms.entity.Branch;

/**
 * @author  ppradhan
 *
 */
public class BookDAO extends BaseDAO<Book>{

	public BookDAO(Connection conn) {
		super(conn);
	}

	public Integer addBook(Book book) throws ClassNotFoundException, SQLException {
		Integer publisherId  = book.getPublisher() == null ? null : book.getPublisher().getId();
		String isbn = book.getIsbn() == null ? null : book.getIsbn();
		if (publisherId == null) throw new SQLException("Publisher id can't be null when adding book");
		return isbn == null ? saveWithPk("INSERT INTO tbl_book (title, pubId) VALUES(?, ?)", new Object[] {book.getTitle(), publisherId}) :
				saveWithPk("INSERT INTO tbl_book (title, pubId, isbn) VALUES (?, ?, ?)", new Object[] { book.getTitle(), publisherId,  isbn});
	}

	public boolean hasBook(String isbn) throws ClassNotFoundException, SQLException {
		return readByIsbn(isbn) == null ? false : true;
	}

	public boolean hasBook(int bookId) throws ClassNotFoundException, SQLException {
		return readById(bookId) == null ? false : true;
	}

	public void updateBook(Book book) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_book SET title = ? WHERE bookId = ?", new Object[] { book.getTitle(), book.getBookId()});
	}

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book WHERE bookId = ?", new Object[] { book.getBookId() });
	}

	public List<Book> readAllBooks() throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM tbl_book", null);
	}
	
	public List<Book> readAllBooksByName(String searchString) throws SQLException, ClassNotFoundException {
		searchString = "%"+searchString+"%";
		return read("SELECT * FROM tbl_book WHERE title LIKE ?", new Object[] {searchString});
	}

	public Book readById(int bookId) throws SQLException, ClassNotFoundException {
		List<Book> books = read("SELECT * FROM tbl_book WHERE bookId = ?", new Object[] {bookId});
		if(books.isEmpty())
			System.out.println("hello");
		return books.isEmpty() ? null : books.get(0);
	}

	public Book readByIsbn(String isbn) throws SQLException, ClassNotFoundException {
		List<Book> books = read("SELECT * FROM tbl_book WHERE isbn = ?", new Object[] {isbn});
		return books.isEmpty() ? null : books.get(0);
	}

	public List<Book> readAllBooksInBranch(Branch branch) throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_copies WHERE branchId = ?)", new Object[] {branch.getId()});
	}

	public List<Book> readByPubId(int pubId) throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM tbl_book WHERE pubId = ?", new Object[] {pubId});
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		AuthorDAO adao = new AuthorDAO(conn);
		while (rs.next()) {
			Book b = new Book(rs.getInt("bookId"), rs.getString("title"));
			try {
				b.setAuthors(adao.read("select * from tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?)", new Object[]{b.getBookId()}));
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
			b.setIsbn(rs.getString("isbn"));
			b.setPublisher(new PublisherDAO(conn).readById(rs.getInt("pubId")));
			b.setGenres(new GenreDAO(conn).readByBookId(b.getBookId()));
			books.add(b);
		}
		rs.close();
		return books;
	}
}
