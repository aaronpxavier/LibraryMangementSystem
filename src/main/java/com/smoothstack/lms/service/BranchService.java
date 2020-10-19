package com.smoothstack.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.smoothstack.lms.dao.AuthorDAO;
import com.smoothstack.lms.dao.BookDAO;
import com.smoothstack.lms.dao.BranchDAO;
import com.smoothstack.lms.entity.Author;
import com.smoothstack.lms.entity.Book;
import com.smoothstack.lms.entity.Branch;


public class BranchService {
    public ConnectionUtil conUtil = new ConnectionUtil();

    public List<Branch> getAllBranches() {
        try(Connection conn = conUtil.getConnection()) {
            BranchDAO bdao = new BranchDAO(conn);
            List<Branch> branches = bdao.readAllBranches();
            return branches;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
