/**
 * 
 */
package com.smoothstack.lms.dao;

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

    public List<Branch> readAllBranches() throws SQLException, ClassNotFoundException {
        return read("SELECT * FROM tbl_library_branch", new Object[] {});
    }

    public Branch readById(int branchId) throws SQLException, ClassNotFoundException {
        return read("SELECT * FROM tbl_library_branch WHERE branchId = ?", new Object[] {branchId}).get(0);
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
            branch.setBooksOwned(copiesDAO.readByBranchId(id));
            branches.add(branch);
        }
        return branches;
    }
}
