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
import com.smoothstack.lms.entity.Book;

/**
 * @author  ppradhan
 *
 */
public class BookDAO extends BaseDAO<Book>{

	public BookDAO(Connection conn) {
		super(conn);
	}

	public void addBook(Book book) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_book (title) VALUES (?)", new Object[] { book.getTitle() });
	}
	
	public Integer addBookWithPk(Book book) throws ClassNotFoundException, SQLException {
		return saveWithPk("INSERT INTO tbl_book (title) VALUES (?)", new Object[] { book.getTitle() });
	}

	public void updateBook(Book book) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_book SET bookName = ? WHERE bookId = ?",
				new Object[] { book.getTitle(), book.getBookId() });
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

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		AuthorDAO adao = new AuthorDAO(conn);
		while (rs.next()) {
			Book b = new Book(rs.getInt("bookId"), rs.getString("title"));
			b.setAuthors(adao.read("select * from tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?)", new Object[] {b.getBookId()}));
			//b.setGenres()
			books.add(b);
		}
		return books;
	}
}
