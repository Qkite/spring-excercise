package dblinking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {
    // PreparedStatement를 return해서 method에 따라 다른 쿼리를 작성할 수 있게 함

    PreparedStatement makePreparedStatement(Connection c) throws SQLException;

}
