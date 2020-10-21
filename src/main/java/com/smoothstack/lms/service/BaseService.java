package com.smoothstack.lms.service;

import java.sql.Connection;
import java.sql.SQLException;

public class BaseService {
    protected Connection conn = null;
    protected Boolean isOutsideConnection = false;

    BaseService(Connection conn) {
        this.conn = conn;
        isOutsideConnection = true;
    }

    BaseService() {}

    protected void closeConn() throws SQLException {
        if (conn != null && !isOutsideConnection) {
            conn.close();
        }
    }
}
