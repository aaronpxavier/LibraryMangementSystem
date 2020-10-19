package com.smoothstack.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import com.smoothstack.lms.dao.BookDAO;
import com.smoothstack.lms.dao.PublisherDAO;
import com.smoothstack.lms.entity.Book;

public class BookService {

	public void addBook(Book book) throws SQLException {
		Connection conn = null;
		try {
			conn = new ConnectionUtil().getConnection();
			BookDAO bookDAO = new BookDAO(conn);
			PublisherDAO publisherDAO = new PublisherDAO(conn);
			boolean bookExistsInDb = bookDAO.hasBook(book.getIsbn());
			if (book.getTitle() != null && book.getTitle().length() > 45)
				System.out.println("Book Title cannot be empty and should be 45 char in length");
			if (!bookExistsInDb) {
				new PublisherService(conn).addPublisher(book.getPublisher());
				book.setBookId(bookDAO.addBook(book));
			} if (bookExistsInDb) {
				System.out.println("Book already exists book not added");
			}
			new AuthorsService(conn).addAuthors(book.getAuthors(), book.getBookId());
			new GenresService(conn).addGenres(book.getGenres(), book.getBookId());
			conn.commit();
			System.out.println("Book added sucessfully");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				conn.rollback();
			}
			System.out.println("Unable to add book - contact admin.");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}


	public List<Book> getBooks(String searchString) {
		try(Connection conn = new ConnectionUtil().getConnection()) {
			BookDAO bdao = new BookDAO(conn);
			if (searchString != null) {
				return bdao.readAllBooksByName(searchString);
			} else {
				return bdao.readAllBooks();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Book> getBooks() {
		try(Connection conn = new ConnectionUtil().getConnection()) {
			BookDAO bdao = new BookDAO(conn);
			return bdao.readAllBooks();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
