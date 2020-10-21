package com.smoothstack.lms.service;

import com.smoothstack.lms.dao.BookDAO;
import com.smoothstack.lms.dao.BorrowerDao;
import com.smoothstack.lms.dao.LoansDAO;
import com.smoothstack.lms.dao.PublisherDAO;
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

//    private Publisher borrowerConflict(List<Borrower> borrowers) {
//        String input;
//        int i = 0;
//        for (Borrower borrower : borrowers) {
//            ++i;
//            System.out.println(i + ") " + borrower.getName() + ", " + borrower.getAddress());
//        }
//        System.out.println("Borrower Name conflicts with existing borrower");
//        System.out.println("Enter quit to go to previous or enter + to create new borrower entry");
//        input = new Scanner(System.in).nextLine();
//        if (input.compareTo("+") == 0) {
//            return null;
//        } else {
//            return publishers.get(i - 1);
//        }
//    }

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
            if (book == null)
                return;
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            new LoansDAO(conn).checkoutBook(borrower, book, branch);
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
