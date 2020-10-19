
package com.smoothstack.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smoothstack.lms.entity.Author;

/**
 * @author ppradhan
 *
 */
public class AuthorDAO extends BaseDAO<Author> {

	public AuthorDAO(Connection conn) {
		super(conn);
	}

	public Integer addAuthor(Author author) throws SQLException {
		return saveWithPk("INSERT INTO tbl_author (authorName) VALUES (?)", new Object[] { author.getAuthorName() });
	}

	public void updateAuthor(Author author) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_author SET authorName = ? WHERE authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_author WHERE authorId = ?", new Object[] { author.getAuthorId() });
	}

	public List<Author> readAllAuthors() throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM tbl_author", null);
	}
	
	public List<Author> readAllAuthorsByName(String searchString) throws SQLException, ClassNotFoundException {
		searchString = "%"+searchString+"%";
		return read("SELECT * FROM tbl_author WHERE authorName LIKE ?", new Object[] {searchString});
	}
	
	public void addBookAuthors(Integer bookId, Integer authorId) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_book_authors VALUES (?, ?)", new Object[] { bookId, authorId });
	}

	public Author readById(int authorId) throws SQLException, ClassNotFoundException {
		List<Author> authors = read("Select * FROM tbl_author WHERE authorId = ?", new Object[] {authorId});
		if (authors.isEmpty()) return null;
		return authors.get(0);
	}

	public List<Author> readByName(String Author) throws SQLException, ClassNotFoundException {
		return read("SELECT * FROM tbl_author WHERE authorName = ?", new Object[] {Author});
	}

	public boolean hasAuthor(int authorId) throws SQLException, ClassNotFoundException {
		return readById(authorId) != null;
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			authors.add(new Author(rs.getInt("authorId"), rs.getString("authorName")));
			//also populate the books written by this Author
		}
		return authors;
	}
}
