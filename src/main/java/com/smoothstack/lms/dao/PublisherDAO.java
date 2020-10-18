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

    public Publisher readByPubId(int pubId) throws SQLException, ClassNotFoundException {
        return read("SELECT * FROM tbl_publihser WHERE publihserId = ?", new Object[] {pubId}).get(0);
    }

    @Override
    public List<Publisher> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
        List<Publisher> publishers = new ArrayList<>();
        while(rs.next()) {
            Publisher publisher = new Publisher(rs.getInt("publisherId"), rs.getString("pubslisherName"), rs.getString("publsiherAddress"));
            publisher.setPublisherPhone(rs.getString("publisherPhone"));
            publishers.add(publisher);
        }
        return publishers;
    }

}
