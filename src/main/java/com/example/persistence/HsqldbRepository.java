package com.example.persistence;

import java.sql.*;

public class HsqldbRepository {

    private static final String JDBC_URI = "jdbc:hsqldb:file:events.db";

    private final Connection con;

    static {
        try {
            // it would be nice to parametrize the file here (for example for unit tests)
            Connection con = DriverManager.getConnection(JDBC_URI, "SA", "");
            Statement statement = con.createStatement();
            statement.executeQuery("CREATE TABLE IF NOT EXISTS EVENT " +
                    "(id varchar(12)," +
                    "duration bigint," +
                    "host varchar(256), " +
                    "type varchar(32)," +
                    "alert char(1));");
            statement.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public HsqldbRepository() {
        // wrapped in try/catch to avoid unnecessary code burden (here and later in the code)
        // I don't see how SQLException here could improve error handling
        // more suitable exception than RuntimeException could be considered
        try {
            con = DriverManager.getConnection(JDBC_URI, "SA", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(EventEntity entity) {
        try {
            Statement statement = con.createStatement();
            // being fully aware that such crude approach enables SQL injection
            statement.execute(
                    String.format("insert into event (id, duration, host, type, alert) " +
                            "values ('%s',%d, '%s','%s','%c')",
                            entity.getId(), entity.getDuration(), entity.getHost(), entity.getType(),
                            entity.getAlert() ? 'T':'F'));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
