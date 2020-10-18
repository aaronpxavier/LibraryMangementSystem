/**
 * 
 */
package com.smoothstack.lms.dao;

import com.smoothstack.lms.entity.Genre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author amavumkal
 *
 */
public class GenreDAO extends BaseDAO<Genre>{

    public GenreDAO(Connection conn) {
        super(conn);
    }

    public List<Genre> readByBookId(int bookId) throws SQLException, ClassNotFoundException {
        return read("SELECT * FROM tbl_genre WHERE genre_id IN (SELECT genre_id from tbl_book_genres WHERE bookId = ?)", new Object[] {bookId});
    }

    @Override
    public List<Genre> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
        List<Genre> genres = new ArrayList<>();
        while (rs.next()) {
            Genre genre = new Genre(rs.getInt("genre_id"), rs.getString("genre_name"));
            genres.add(genre);
        }
        return genres;
    }
}
