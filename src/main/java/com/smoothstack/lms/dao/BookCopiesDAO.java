package com.smoothstack.lms.dao;

import com.smoothstack.lms.entity.BookCopies;

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

    @Override
    public List<BookCopies> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
        List<BookCopies> bookCopiesList = new ArrayList<>();
        BookDAO bookDAO = new BookDAO(conn);
        BranchDAO branchDAO = new BranchDAO(conn);
        while (rs.next()) {
            BookCopies bookCopies = new BookCopies(rs.getInt("noOfCopies"));
            bookCopies.setBook(bookDAO.getBookById(rs.getInt("bookId")));
            bookCopies.setBranch(branchDAO.readById(rs.getInt("branchId")));
        }
        return bookCopiesList;
    }
}
