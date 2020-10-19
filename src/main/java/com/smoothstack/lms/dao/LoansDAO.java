/**
 * 
 */
package com.smoothstack.lms.dao;

import com.smoothstack.lms.entity.Loan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
