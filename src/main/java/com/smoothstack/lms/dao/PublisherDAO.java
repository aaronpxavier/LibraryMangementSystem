/**
 * 
 */
package com.smoothstack.lms.dao;

import com.smoothstack.lms.entity.Publisher;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author amavumkal
 *
 */
public class PublisherDAO extends BaseDAO<Publisher> {
    public PublisherDAO(Connection conn) {
        super(conn);
    }

    public Integer addPublihser(Publisher publisher) throws SQLException {
        return saveWithPk("INSERT INTO tbl_publisher (publisherName, publisherAddress, publisherPhone) VALUES(?, ?, ?)", new Object[] {publisher.getName(), publisher.getAddress(), publisher.getPublisherPhone()});
    }
    public Publisher readById(int pubId) throws SQLException, ClassNotFoundException {
        List<Publisher> publishers = read("SELECT * FROM tbl_publisher WHERE publisherId = ?", new Object[] {pubId});
        return publishers.isEmpty() ? null : publishers.get(0);
    }

    public List<Publisher> readByName(String name) throws SQLException, ClassNotFoundException {
        return read("SELECT * FROM tbl_publisher WHERE publisherName = ?", new Object[] {name});
    }

    public List<Publisher> readAllPublishers() throws SQLException, ClassNotFoundException {
        return read("SELECT * FROM tbl_publisher", new Object[] {});
    }

    public boolean hasPublisher(int pubId) throws SQLException, ClassNotFoundException {
        return readById(pubId) == null ? false : true;
    }

    public boolean hasPublisher(String name) throws SQLException, ClassNotFoundException {
        return readByName(name).isEmpty() ? false : true;
    }

    public void deletePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
        save("DELETE FROM tbl_publisher WHERE publisherId = ?", new Object[] {publisher.getId()});
    }

    public void update(Publisher publisher) throws ClassNotFoundException, SQLException {
        save("UPDATE tbl_publisher SET publisherName = ?, publisherAddress = ?, publisherPhone = ? WHERE publisherId = ?"
                , new Object[] {publisher.getName(), publisher.getAddress(), publisher.getPublisherPhone(), publisher.getId()});
    }


    @Override
    public List<Publisher> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
        List<Publisher> publishers = new ArrayList<>();
        while(rs.next()) {
            Publisher publisher = new Publisher(rs.getInt("publisherId"), rs.getString("publisherName"), rs.getString("publisherAddress"));
            publisher.setPublisherPhone(rs.getString("publisherPhone"));
            publishers.add(publisher);
        }
        return publishers;
    }

}
