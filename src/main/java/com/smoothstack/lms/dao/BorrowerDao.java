package com.smoothstack.lms.dao;

import com.smoothstack.lms.entity.Borrower;

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
        return read("SELECT * FROM tbl_borrower WHERE cardNo = ?", new Object[] {cardNo}).get(0);
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
