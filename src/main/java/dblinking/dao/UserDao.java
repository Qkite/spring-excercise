package dblinking.dao;

import dblinking.connection_maker.ConnectionMaker;
import dblinking.connection_maker.LocalConnectionMaker;
import dblinking.domain.User;

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

    public void add(User user) throws SQLException, ClassNotFoundException {

        Connection c = connectionMaker.makeConnection();
        PreparedStatement ps = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();
        ps.close();
        c.close();
    }

    public User findById(String id) throws SQLException, ClassNotFoundException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE id = ?");

        ps.setString(1,id);

        ResultSet rs = ps.executeQuery();

        rs.next();
        String[] outputs = {rs.getString(1), rs.getString(2),  rs.getString(3)};

        rs.close();
        ps.close();
        c.close();

        return new User(outputs[0], outputs[1], outputs[2]);

    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement("delete from users");

        ps.executeUpdate();
        ps.close();
        c.close();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        userDao.add(new User("7", "Ruru", "1123457"));

    }

}
