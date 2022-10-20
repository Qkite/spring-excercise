package dblinking.connection_maker;

import dblinking.domain.User;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class LocalConnectionMaker implements ConnectionMaker{


    @Override
    public Connection makeConnection() throws SQLException, ClassNotFoundException {
        Map<String, String> env = System.getenv();
        Connection connection = DriverManager.getConnection(env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD"));

        return connection;
    }
}
