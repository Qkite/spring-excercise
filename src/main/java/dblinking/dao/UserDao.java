package dblinking.dao;

import dblinking.connection_maker.ConnectionMaker;
import dblinking.connection_maker.LocalConnectionMaker;
import dblinking.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private ConnectionMaker connectionMaker = new LocalConnectionMaker();

    public UserDao(){

    }

    public UserDao(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;

    }

    // add와 deleteAll의 공통로직
    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = connectionMaker.makeConnection();
            ps = stmt.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally { // error가 나도 실행되는 블럭
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {

                }
            }

            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {

                }
            }

        }
    }


    public void add(User user) throws SQLException, ClassNotFoundException {
//        jdbcContextWithStatementStrategy(new AddStrategy(user));
        jdbcContextWithStatementStrategy(
                // 익명 클래스 적용
                new StatementStrategy() {
                    @Override
                    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values (?, ?, ?)");
                        ps.setString(1, user.getId());
                        ps.setString(2, user.getName());
                        ps.setString(3, user.getPassword());

                        return ps;
                    }
                }
        );


    }


    public void deleteAll() throws SQLException, ClassNotFoundException{
        jdbcContextWithStatementStrategy(
                new StatementStrategy() {
                    @Override
                    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                        PreparedStatement ps = c.prepareStatement("delete from users");
                        return ps;
                    }
                }
        );
    }

    
    public User findById(String id) throws SQLException, ClassNotFoundException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE id = ?");

        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        //rs.next()가 null이 되는 경우도 있음

        User user = null;
        if (rs.next()) {
            user = new User(rs.getString(1), rs.getString(2), rs.getString(3));
        }

        rs.close();
        ps.close();
        c.close();

        if(user == null){
            throw new EmptyResultDataAccessException(1);
        }

        return user;

    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {

            c = connectionMaker.makeConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            count = rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {

            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {

                }
            }
            if (ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {

                }
            }
            if (c!=null){
                try {
                    c.close();
                } catch (SQLException e) {

                }
            }
        }
        return count;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        userDao.add(new User("7", "Ruru", "1123457"));

    }

}
