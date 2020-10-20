package com.smoothstack.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.smoothstack.lms.dao.*;
import com.smoothstack.lms.entity.*;


public class BranchService extends BaseService {

    public BranchService(Connection conn) { super(conn); }

    public BranchService() {}

    public List<Branch> getBranches() throws SQLException{
        try {
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            return new BranchDAO(conn).readAllBranches();
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to add Publisher");
        } finally {
            closeConn();
        }
        return null;
    }

    public void addBranch(Branch branch) throws SQLException {
        try {
            if (branch == null)
                return;
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            BranchDAO branchDAO = new BranchDAO(conn);
            branchDAO.addBranch(branch);
            if(!isOutsideConnection)
                conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to add branch");
        } finally {
            closeConn();
        }
    }

    public void updateBranch (Branch branch) throws SQLException {
        try {
            if (branch == null)
                return;
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            new BranchDAO(conn).updateBranch(branch);
            if(!isOutsideConnection)
                conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to add branch");
        } finally {
            closeConn();
        }
    }

    public void deleteBranch (Branch branch) throws SQLException {
        try {
            if (branch == null)
                return;
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            new BranchDAO(conn).deleteBranch(branch);
            if(!isOutsideConnection)
                conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to add branch");
        } finally {
            closeConn();
        }
    }

    public boolean branchHasLoans(Branch branch) throws SQLException {
        try {
            if (branch == null)
                return false;
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            List<Loan> loans = new LoansDAO(conn).readByBranchId(branch.getId());
            if(!isOutsideConnection) {
                conn.commit();
                closeConn();
            }
            return !loans.isEmpty();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
                closeConn();
            }
            System.out.println("Error");
            return false;
        }
    }

    public void addCopiesOfBook(Branch branch, Book book, int copies) throws SQLException {
        try {
            if (branch == null)
                return;
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            new BranchDAO(conn).addBooksToBranch(branch, book, copies);
            if(!isOutsideConnection)
                conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to add branch");
        } finally {
            closeConn();
        }
    }

    public Integer existingNumberOfCopies(Branch branch, Book book) throws SQLException {
        Integer resultInt;
        try {
            if (branch == null)
                return null;
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            BookCopies bookCopies = new BookCopiesDAO(conn).readByBranchIdBookId(branch, book);
            if(bookCopies == null)
                resultInt = 0;
            else
                resultInt = bookCopies.getNoOfCopies();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to get no of copies");
            return null;
        } finally {
            closeConn();
        }
        return resultInt;
    }

    public void updateBranchCopies(Branch branch, Book book, int noCopies) throws SQLException {
        try {
            if (branch == null)
                return;
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            new BookCopiesDAO(conn).updateBranchBookCopies(branch, book, noCopies);
            if(!isOutsideConnection)
                conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to add branch");
        } finally {
            closeConn();
        }
    }

}
