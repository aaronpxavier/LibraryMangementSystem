package com.smoothstack.lms.service;

import com.smoothstack.lms.dao.*;
import com.smoothstack.lms.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BorrowerService extends BaseService{
    public BorrowerService(Connection conn) { super(conn); }

    public BorrowerService() {}

    public List<Borrower> getBorrowers() throws SQLException {
        try {
            BorrowerDao borrowerDao;
            List<Borrower> borrowers;
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            borrowerDao = new BorrowerDao(conn);
            borrowers =  borrowerDao.readAllBorrowers();
            closeConn();
            return borrowers;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            closeConn();
            return null;
        }
    }

    public void addBorrower(Borrower borrower) throws SQLException {
        try {
            if (borrower == null)
                return;
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            BorrowerDao borrowerDao = new BorrowerDao(conn);
            borrower.setCardNo(borrowerDao.addBorrower(borrower));
            if(!isOutsideConnection)
                conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to add Borrower");
        } finally {
            closeConn();
        }
    }

    public void updateBorrower(Borrower borrower) throws SQLException {
        try {
            if (borrower == null)
                return;
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            BorrowerDao borrowerDao = new BorrowerDao(conn);
            borrowerDao.updateBorrower(borrower);
            if(!isOutsideConnection)
                conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to update Borrower");
        } finally {
            closeConn();
        }
    }

    public void deleteBorrower(Borrower borrower) throws SQLException {
        try {
            if (borrower == null)
                return;
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            BorrowerDao borrowerDao = new BorrowerDao(conn);
            borrowerDao.deleteBorrower(borrower);
            if(!isOutsideConnection)
                conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to delete Borrower");
        } finally {
            closeConn();
        }
    }

    public List<Loan> getLoans(Borrower borrower) throws SQLException {
        try {
            if (borrower == null)
                return null;
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            return new LoansDAO(conn).readByCardNo(borrower);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to delete Borrower");
        } finally {
            closeConn();
        }
        return null;
    }

    public boolean checkCardNo(int cardNo) throws SQLException {
        boolean isValidUser = false;
        try {
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            isValidUser = new BorrowerDao(conn).readByCardNo(cardNo) != null;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to update Borrower");
        } finally {
            closeConn();
        }
        return isValidUser;
    }

    public void checkoutBook(Borrower borrower, Book book, Branch branch) throws SQLException {
        try {
            if (book == null || borrower == null || branch == null)
                return;
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            BookCopiesDAO bookCopiesDAO = new BookCopiesDAO(conn);
            LoansDAO loansDAO = new LoansDAO(conn);
            int numOfCopies;
            List<Loan> loans = loansDAO.readByCardNo(borrower);
            for(Loan loan: loans) {
                if(loan.getBook().getBookId() == book.getBookId()) {
                    System.out.println("Borrower " + borrower.getName() + " already has book "  + book.getTitle() + " checked out pick another book");
                    return;
                }
            }
            loansDAO.checkoutBook(borrower, book, branch);
            numOfCopies = bookCopiesDAO.readByBranchIdBookId(branch, book).getNoOfCopies();
            bookCopiesDAO.updateBranchBookCopies(branch, book, numOfCopies - 1);
            if(!isOutsideConnection)
                conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to complete checkout transaction");
        } finally {
            closeConn();
        }
    }

    public void checkInBook(Book book, Borrower borrower) throws SQLException {
        try {
            if (book == null || borrower == null)
                return;
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            Branch branch;
            BookCopiesDAO bookCopiesDAO = new BookCopiesDAO(conn);
            LoansDAO loansDAO = new LoansDAO(conn);
            int numOfCopies;
            List<Loan> loans = loansDAO.readByCardNo(borrower);
            branch = loansDAO.getLoanBranch(book.getBookId(), borrower.getCardNo());
            loansDAO.checkInBook(borrower, book);
            numOfCopies = bookCopiesDAO.readByBranchIdBookId(branch, book).getNoOfCopies();
            bookCopiesDAO.updateBranchBookCopies(branch, book, numOfCopies + 1);
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

    public Borrower getBorrower(int cardNo) throws SQLException {
        Borrower borrower;
        try {
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            borrower = new BorrowerDao(conn).readByCardNo(cardNo);
            closeConn();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            closeConn();
            return null;
        }
        return borrower;
    }

}
