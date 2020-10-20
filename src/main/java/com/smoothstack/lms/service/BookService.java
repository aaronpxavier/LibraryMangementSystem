package com.smoothstack.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.smoothstack.lms.dao.BookDAO;
import com.smoothstack.lms.dao.LoansDAO;
import com.smoothstack.lms.dao.PublisherDAO;
import com.smoothstack.lms.entity.Book;
import com.smoothstack.lms.entity.Branch;

public class BookService extends BaseService{

	public BookService(Connection conn) {
		super(conn);
	}

	public BookService() {}

	public void addBook(Book book) throws SQLException {
		try {
			if (conn == null || !isOutsideConnection)
				conn = new ConnectionUtil().getConnection();
			BookDAO bookDAO = new BookDAO(conn);
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
			if (!isOutsideConnection)
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
			if (conn == null || !isOutsideConnection)
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

	public List<Book> getBooksInBranch(Branch branch) throws SQLException {
		try {
			if (conn == null || !isOutsideConnection)
				conn = new ConnectionUtil().getConnection();
			return new BookDAO(conn).readAllBooksInBranch(branch);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			if (conn != null && !isOutsideConnection) {
				conn.rollback();
			}
			System.out.println("Unable to update Borrower");
		} finally {
			closeConn();
		}
		return null;
	}

	public List<Book> getBooks() throws SQLException{
		BookDAO bdao;
		List<Book> books;
		try {
			if (conn == null || !isOutsideConnection)
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
			if (conn == null || !isOutsideConnection)
				conn = new ConnectionUtil().getConnection();
			bookDAO = new BookDAO(conn);
			loansDAO = new LoansDAO(conn);
			if (!loansDAO.readByBookId(book.getBookId()).isEmpty()) {
				System.out.println("Book has active loans are you sure you want to delete? y/n");
				String input = new Scanner(System.in).nextLine();
				if(input.toLowerCase().compareTo("n") == 0) {
					conn.rollback();
					closeConn();
					throw new SQLException("User selected to terminate transaction");
				}
			}
			bookDAO.deleteBook(book);
			if(!isOutsideConnection)
				conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			closeConn();
		}
	}

	public void updateBook(Book book) throws SQLException {
		try {
			if (conn == null || !isOutsideConnection)
				conn = new ConnectionUtil().getConnection();
			new BookDAO(conn).updateBook(book);
			if(!isOutsideConnection)
				conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			closeConn();
		}
	}

}
