package com.smoothstack.lms.service;

import com.smoothstack.lms.dao.GenreDAO;
import com.smoothstack.lms.entity.Book;
import com.smoothstack.lms.entity.Genre;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GenresService extends BaseService{

    public GenresService() {}

    public GenresService(Connection conn) { super(conn); }

    public List<Genre> getGenres() throws SQLException{
        List<Genre> genres = null;
        try {
            if (conn == null)
                conn = new ConnectionUtil().getConnection();
            genres = new GenreDAO(conn).readAllGenres();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to add connection wizard");
        } finally {
            closeConn();
        }
        return genres;
    }

    public void addGenres(List<Genre> genres, Integer bookId) throws SQLException {
        try {
            if (conn == null)
                conn = new ConnectionUtil().getConnection();
            GenreDAO genreDAO;
            if (genres.isEmpty()) return;
            genreDAO = new GenreDAO(conn);
            for (Genre genre : genres) {
                boolean hasGenre = genreDAO.hasGenre(genre.getName());
                if (genre.getId() == null && !hasGenre) {
                    genre.setId(genreDAO.addGenre(genre));
                } else if (hasGenre) {
                    genre.setId(genreDAO.readByName(genre.getName()).getId());
                }
                if (bookId != null)
                    genreDAO.addBookGenre(bookId, genre.getId());
            }
            if(!isOutsideConnection) {
                conn.commit();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to add connection wizard");
        } finally {
            closeConn();
        }
    }

    public void addGenre(Genre genre) throws SQLException{
        try {
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            new GenreDAO(conn).addGenre(genre);
            if(!isOutsideConnection)
                conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to add Genre");
        } finally {
            closeConn();
        }
    }

    public void updateGenre(Genre genre) throws SQLException{
        try {
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            new GenreDAO(conn).updateGenre(genre);
            if(!isOutsideConnection)
                conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to add connection wizard");
        } finally {
            closeConn();
        }
    }

    public void deleteGenre(Genre genre) throws SQLException {
        try {
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            new GenreDAO(conn).deleteGenre(genre);
            if(!isOutsideConnection)
                conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to add connection wizard");
        } finally {
            closeConn();
        }
    }
}
