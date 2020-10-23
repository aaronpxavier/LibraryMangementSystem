/**
 * 
 */
package com.smoothstack.lms.dao;

import com.smoothstack.lms.entity.Book;
import com.smoothstack.lms.entity.BookCopies;
import com.smoothstack.lms.entity.Branch;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author amavumkal
 *
 */
public class BranchDAO extends BaseDAO<Branch>{
    public BranchDAO(Connection conn) { super(conn); }

    public Integer addBranch(Branch branch) throws ClassNotFoundException, SQLException {
        return saveWithPk("INSERT INTO tbl_library_branch (branchName, branchAddress) VALUES(?, ?)", new Object[] {branch.getName(), branch.getAddress()});
    }
    public List<Branch> readAllBranches() throws SQLException, ClassNotFoundException {
        return read("SELECT * FROM tbl_library_branch", new Object[] {});
    }

    public Branch readById(int branchId) throws SQLException, ClassNotFoundException {
        return read("SELECT * FROM tbl_library_branch WHERE branchId = ?", new Object[] {branchId}).get(0);
    }

    public void updateBranch(Branch branch) throws ClassNotFoundException, SQLException {
        save("UPDATE tbl_library_branch SET branchName = ?, branchAddress = ? WHERE branchId = ?", new Object[] {branch.getName(), branch.getAddress(), branch.getId()});
    }

    public void deleteBranch(Branch branch) throws ClassNotFoundException,SQLException {
        save("DELETE FROM tbl_library_branch WHERE branchId = ?", new Object[] {branch.getId()});
    }

    public void addBooksToBranch(Branch branch, Book book, int copies) throws ClassNotFoundException, SQLException {
        save("UPDATE tbl_book_copies SET bookId = ?, branchId = ?, noOfCopies = ?", new Object[] {branch.getId(), book.getBookId(), copies});
    }
    @Override
    public List<Branch> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
        List<Branch> branches = new ArrayList<>();
        LoansDAO loansDAO = new LoansDAO(conn);
        BookCopiesDAO copiesDAO = new BookCopiesDAO(conn);
        while(rs.next()) {
            int id;
            Branch branch = new Branch(
                    rs.getInt("branchId"),
                    rs.getString("branchName"),
                    rs.getString("branchAddress")
            );
            id = branch.getId();
            branch.setLoans(loansDAO.readByBranchId(id));
            List<BookCopies> copies = copiesDAO.readByBranchId(id);
            if(!copies.isEmpty() && branch.getBooksOwned() != null)
                branch.getBooksOwned().get(branch.getBooksOwned().size() - 1).setBranch(branch);
            branches.add(branch);
        }
        return branches;
    }
}
