package com.smoothstack.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.smoothstack.lms.dao.BookDAO;
import com.smoothstack.lms.dao.LoansDAO;
import com.smoothstack.lms.dao.PublisherDAO;
import com.smoothstack.lms.entity.Book;

public class BookService {
	private Connection conn = null;
	private Boolean isOutsideConnection = false;

	public BookService(Connection conn) {
		this.conn = conn;
		isOutsideConnection = true;
	}

	public BookService() {}

	private void closeConn() throws SQLException{
		if (conn != null && !isOutsideConnection) {
			conn.close();
		}
	}

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
			if (conn != null && !isOutsideConnection) {
				conn.rollback();
			}
			System.out.println("Unable to add book - contact admin.");
		} finally {
			closeConn();
		}
	}


	public List<Book> getBooks(String searchString) throws SQLException {
		try {
			BookDAO bdao;
			List<Book> books;
			if (searchString == null)
				return null;
			if(conn == null)
				conn = new ConnectionUtil().getConnection();
			bdao = new BookDAO(conn);
			books =  bdao.readAllBooksByName(searchString);
			closeConn();
			return books;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			closeConn();
			return null;
		}
	}

	public List<Book> getBooks() throws SQLException{
		BookDAO bdao;
		List<Book> books;
		try {
			if(conn == null)
				conn = new ConnectionUtil().getConnection();
			bdao = new BookDAO(conn);
			books = bdao.readAllBooks();
			closeConn();
			return books;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			closeConn();
			return null;
		}
	}

	public void deleteBook(Book book) throws SQLException {
		BookDAO bookDAO;
		LoansDAO loansDAO;
		try {
			if (conn == null)
				conn = new ConnectionUtil().getConnection();
			bookDAO = new BookDAO(conn);
			loansDAO = new LoansDAO(conn);
			if (!loansDAO.readByBookId(book.getBookId()).isEmpty()) {
				System.out.println("Book has active loans are you sure you want to delete? y/n");
				String input = new Scanner(System.in).nextLine();
				if(input.toLowerCase().compareTo("n") == 0) {
					closeConn();
					throw new SQLException("User selected to terminate transaction");
				}
			}
			bookDAO.deleteBook(book);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			closeConn();
		}
	}

}
