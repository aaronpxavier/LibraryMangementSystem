package com.smoothstack.lms.service;

import com.smoothstack.lms.dao.PublisherDAO;
import com.smoothstack.lms.entity.Publisher;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class PublisherService extends BaseService {

    public PublisherService() {}

    PublisherService(Connection conn) {
        super(conn);
    }

    private Publisher publisherConflict(List<Publisher> publishers) {
        String input;
        int i = 0;
        System.out.println("Publisher Name conflicts with existing publisher");
        System.out.println("Select existing publisher from following list or enter + to create new publisher entry");

        for (Publisher publisher : publishers) {
            ++i;
            System.out.println(i + ") " + publisher);
        }
        input = new Scanner(System.in).nextLine();
        if (input.compareTo("+") == 0) {
            return null;
        } else {
            return publishers.get(i - 1);
        }
    }

    public void addPublisher(Publisher publisher) throws SQLException {
        try {
            if (publisher == null)
                return;
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            PublisherDAO publisherDAO = new PublisherDAO(conn);
            List<Publisher> publishers = publisherDAO.readByName(publisher.getName());
            if(publishers.isEmpty()) {
                publisher.setId(publisherDAO.addPublihser(publisher));
            } else {
                Publisher tempPub = publisherConflict(publishers);
                if (tempPub == null)
                    publisher.setId(publisherDAO.addPublihser(publisher));
                else
                    publisher.setId(tempPub.getId());
            }
            if(!isOutsideConnection)
                conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to add Publisher");
        } finally {
            closeConn();
        }
    }

    public void update(Publisher publisher) throws SQLException {
        try {
            if (publisher == null)
                throw new SQLException("publisher object is null");
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            new PublisherDAO(conn).update(publisher);
            if(!isOutsideConnection) {
                conn.commit();
            }
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to add Publisher");
        } finally {
            closeConn();
        }
    }

    public void delete(Publisher publisher) throws SQLException {
        try {
            if (publisher == null)
                throw new SQLException("publisher object is null");
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            new PublisherDAO(conn).deletePublisher(publisher);
            if(!isOutsideConnection)
                conn.commit();
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to delete Publisher");
        } finally {
            closeConn();
        }
    }

    public List<Publisher> getPublishers() throws SQLException{
        try {
            if (conn == null || !isOutsideConnection)
                conn = new ConnectionUtil().getConnection();
            return new PublisherDAO(conn).readAllPublishers();
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            if (conn != null && !isOutsideConnection) {
                conn.rollback();
            }
            System.out.println("Unable to add Publisher");
        } finally {
            closeConn();
        }
        return null;
    }
}
