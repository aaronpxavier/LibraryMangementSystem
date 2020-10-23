package com.smoothstack.lms.service;

import com.smoothstack.lms.dao.BookCopiesDAO;
import com.smoothstack.lms.dao.BorrowerDao;
import com.smoothstack.lms.dao.LoansDAO;
import com.smoothstack.lms.entity.Book;
import com.smoothstack.lms.entity.Borrower;
import com.smoothstack.lms.entity.Branch;
import com.smoothstack.lms.entity.Loan;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class LoanService extends BaseService{
    public LoanService(Connection conn) {
        super(conn);
    }

    public LoanService() {}

    public List<Loan> getLoans() throws SQLException {
        List<Loan> loans;
        try {
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            loans = new LoansDAO(conn).readAllLoans();
            closeConn();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            closeConn();
            return null;
        }
        return loans;
    }

    public void updateDueDate(Borrower borrower, Book book, Date newDueDate) throws SQLException {
            try {
                if (book == null || borrower == null)
                    return;
                if (conn == null || !isOutsideConnection)
                    conn = new ConnectionUtil().getConnection();
                new LoansDAO(conn).updateDueDate(borrower, book, newDueDate);
                if(!isOutsideConnection)
                    conn.commit();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                if (conn != null && !isOutsideConnection) {
                    conn.rollback();
                }
                System.out.println("Unable to complete checkin transaction");
            } finally {
                closeConn();
            }
    }
}
