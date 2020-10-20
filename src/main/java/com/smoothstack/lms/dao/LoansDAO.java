/**
 * 
 */
package com.smoothstack.lms.dao;

import com.smoothstack.lms.entity.Book;
import com.smoothstack.lms.entity.Borrower;
import com.smoothstack.lms.entity.Branch;
import com.smoothstack.lms.entity.Loan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author amavumkal
 *
 */
public class LoansDAO extends BaseDAO<Loan> {
    public LoansDAO(Connection conn) {
        super(conn);
    }

    public List<Loan> readByBranchId(int branchId) throws SQLException, ClassNotFoundException{
        return read("SELECT * FROM tbl_book_loans WHERE branchId = ?", new Object[] {branchId});
    }

    public List<Loan> readByBookId(int bookId) throws SQLException, ClassNotFoundException {
        return read("SELECT * FROM tbl_book_loans WHERE bookId = ?", new Object[] {bookId});
    }

    public List<Loan> readByCardNo(Borrower borrower) throws ClassNotFoundException, SQLException {
        return read("SELECT * FROM tbl_book_loans WHERE cardNo = ?", new Object[] {borrower.getCardNo()});
    }

    public void checkoutBook(Borrower borrower, Book book, Branch branch) throws ClassNotFoundException, SQLException {
        Calendar currentTimeCal = Calendar.getInstance();
        currentTimeCal.add(Calendar.DAY_OF_MONTH, 7);
        saveWithPk("INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate), VALUES (?, ?, ?, ?, ?)",
                new Object[] {book.getBookId(), branch.getId(), borrower.getCardNo(), new Date(System.currentTimeMillis()), new Date(currentTimeCal.getTimeInMillis())});
    }

    @Override
    public List<Loan> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
        List<Loan> loans = new ArrayList<>();
        while(rs.next()) {
            Loan loan = new Loan();
            loan.setBook(new BookDAO(conn).readById(rs.getInt("bookId")));
            loan.setBranch(new BranchDAO(conn).readById(rs.getInt("branchId")));
            loan.setBorrower(new BorrowerDao(conn).readByCardNo(rs.getInt("cardNo")));
            loan.setDateIn(rs.getDate("dateIn"));
            loan.setDateOut(rs.getDate("dateOut"));
            loan.setDueDate(rs.getDate("dueDate"));
            loans.add(loan);
        }
        return loans;
    }
}
