package com.smoothstack.lms.service;

import com.smoothstack.lms.dao.AuthorDAO;
import com.smoothstack.lms.entity.Author;
import com.smoothstack.lms.entity.Book;
import org.apache.http.auth.AuthProtocolState;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AuthorsService extends BaseService {

    public AuthorsService(Connection conn) { super(conn); }

    public AuthorsService() {};

    private Integer authorConflict(List<Author> authors) {
        String input;
        int i = 0;
        System.out.println("Author Name conflicts with existing author");
        System.out.println("Select existing author from following list or enter + to create new author entry");
        for (Author author : authors) {
            ++i;
            System.out.println(i + ") " + author);
        }
        input = new Scanner(System.in).nextLine();
        if (input.compareTo("+") == 0) {
            return -1;
        } else {
            return authors.get(i - 1).getAuthorId();
        }
    }

    public void addAuthors(List<Author> authors, int bookId) throws SQLException {
        try {
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            AuthorDAO authorDAO = new AuthorDAO(conn);
            for (Author a : authors) {

                if (a.getAuthorId() == null) {
                    Integer authorId = null;
                    List<Author> existingAuthors = authorDAO.readAllAuthorsByName(a.getAuthorName());
                    if (existingAuthors.isEmpty())
                        a.setAuthorId(authorDAO.addAuthor(a));
                    else
                        authorId = authorConflict(existingAuthors);
                    if (authorId != null && authorId != -1) {
                        a.setAuthorId(authorId);
                    }
                }
                authorDAO.addBookAuthors(bookId, a.getAuthorId());
            }
            if(!isOutsideConnection) {
                conn.commit();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to add author - contact admin.");
        } finally {
            closeConn();
        }
    }

    public void updateAuthor(Author author) throws SQLException {
        try {
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            AuthorDAO authorDAO = new AuthorDAO(conn);
            authorDAO.updateAuthor(author);
            if(!isOutsideConnection)
                conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to add author - contact admin.");
        } finally {
            closeConn();
        }
    }
}
