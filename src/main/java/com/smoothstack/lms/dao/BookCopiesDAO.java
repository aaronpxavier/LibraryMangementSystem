package com.smoothstack.lms.dao;

import com.smoothstack.lms.entity.Book;
import com.smoothstack.lms.entity.BookCopies;
import com.smoothstack.lms.entity.Branch;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookCopiesDAO extends BaseDAO{
    public BookCopiesDAO(Connection conn) {
        super(conn);
    }

    public List<BookCopies> readByBranchId(int branchId) throws SQLException, ClassNotFoundException {
        return read("SELECT * FROM tbl_book_copies WHERE branchId = ?", new Object[] {branchId});
    }

    public BookCopies readByBranchIdBookId(Branch branch, Book book) throws SQLException, ClassNotFoundException {
        List<BookCopies> copies = read("SELECT * FROM tbl_book_copies WHERE bookId = ? AND branchId = ?", new Object[] {book.getBookId(), branch.getId()});
        return copies.isEmpty() ? null : copies.get(0);
    }

    public void updateBranchBookCopies(Branch branch, Book book, int copies) throws  SQLException, ClassNotFoundException {
        save("UPDATE tbl_book_copies SET bookId = ?, branchId = ?, noOfCopies = ? WHERE bookId = ? and branchId = ?",
                new Object[] {book.getBookId(), branch.getId(), copies, book.getBookId(), branch.getId()});
    }

    public void insertBranchBookCopies(Branch branch, Book book, int copies) throws SQLException, ClassNotFoundException {
        save("INSERT INTO tbl_book_copies (bookId, branchId, noOfCopies) VALUES(?, ?, ?)", new Object[] {book.getBookId(), branch.getId(), copies});
    }

    @Override
    public List<BookCopies> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
        List<BookCopies> bookCopiesList = new ArrayList<>();
        BookDAO bookDAO = new BookDAO(conn);
        while (rs.next()) {
            BookCopies bookCopies = new BookCopies(rs.getInt("noOfCopies"));
            bookCopies.setBook(bookDAO.readById(rs.getInt("bookId")));
            bookCopiesList.add(bookCopies);
        }
        return bookCopiesList;
    }
}
