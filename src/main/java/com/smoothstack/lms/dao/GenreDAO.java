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

    public List<Genre> readAllGenres() throws  SQLException, ClassNotFoundException {
        return read("SELECT * FROM tbl_genre", new Object[] {});
    }

    public List<Genre> readByBookId(int bookId) throws SQLException, ClassNotFoundException {
        return read("SELECT * FROM tbl_genre WHERE genre_id IN (SELECT genre_id from tbl_book_genres WHERE bookId = ?)", new Object[] {bookId});
    }

    public Integer addGenre(Genre genre) throws SQLException {
        return saveWithPk("INSERT INTO tbl_genre (genre_name) VALUES(?)", new Object[] {genre.getName()});
    }

    public Genre readById(int genreId) throws SQLException, ClassNotFoundException {
        List<Genre> genres = read("SELECT * FROM tbl_genre WHERE genre_id = ?", new Object[] {genreId});
        return genres.isEmpty() ? null : genres.get(0);
    }

    public Genre readByName(String name) throws  SQLException, ClassNotFoundException {
        List<Genre> genres = read("SELECT *  FROM tbl_genre WHERE genre_name = ?", new Object[] {name});
        return genres.isEmpty() ? null : genres.get(0);
    }

    public void deleteGenre(Genre genre) throws ClassNotFoundException, SQLException {
        save("DELETE FROM tbl_genre WHERE genre_id = ?", new Object[] {genre.getId()});
    }

    public boolean hasGenre(int genreId) throws SQLException, ClassNotFoundException {
        return readById(genreId) == null ? false : true;
    }

    public boolean hasGenre(String genreName) throws SQLException, ClassNotFoundException {
        return readByName(genreName) == null ? false : true;
    }

    public Integer addBookGenre(int bookId, int genreId) throws SQLException {
        return saveWithPk("INSERT INTO tbl_book_genres (genre_id, bookId) VALUES(?, ?)", new Object[] {genreId, bookId});
    }

    public void updateGenre(Genre genre) throws ClassNotFoundException, SQLException {
        save("UPDATE tbl_genre SET genre_name = ? WHERE genre_id = ?", new Object[] {genre.getName(), genre.getId()});
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
