package com.smoothstack.lms.dao;

import com.smoothstack.lms.entity.Borrower;
import com.smoothstack.lms.entity.Loan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowerDao extends BaseDAO<Borrower>{
    public BorrowerDao(Connection conn) {
        super(conn);
    }

    public Borrower readByCardNo(int cardNo) throws SQLException, ClassNotFoundException {
        List<Borrower> borrowers = read("SELECT * FROM tbl_borrower WHERE cardNo = ?", new Object[] {cardNo});
        return borrowers.isEmpty() ? null : borrowers.get(0);
    }

    public List<Borrower> readAllBorrowers() throws SQLException, ClassNotFoundException {
        return read("SELECT * FROM tbl_borrower", new Object[] {});
    }

    public Integer addBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
        return saveWithPk("INSERT INTO tbl_borrower (name, address, phone) VALUES(?, ?, ?)", new Object[] {borrower.getName(), borrower.getAddress(), borrower.getPhone()});
    }

    public void updateBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
        save("UPDATE tbl_book SET name = ?, address = ?, phone = ?", new Object[] {borrower.getName(), borrower.getAddress(), borrower.getPhone()});
    }

    public void deleteBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
        save("DELETE FROM tbl_borrower WHERE cardNo = ?", new Object[] {borrower.getCardNo()});
    }

    @Override
    public List<Borrower> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
        List<Borrower> borrowers = new ArrayList<>();
        while (rs.next()) {
            Borrower borrower = new Borrower(rs.getInt("cardNo"), rs.getString("name"),
                    rs.getString("address"), rs.getString("phone"));
            borrowers.add(borrower);
        }
        return borrowers;
    }

}
