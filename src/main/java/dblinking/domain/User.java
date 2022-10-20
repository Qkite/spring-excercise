package dblinking.domain;

import java.sql.*;
import java.util.Map;

public class User {

    private String id;
    private String name;
    private String password;

    public User(){

    } // 비어있는 생성자 -> input을 안 넣었을 때도 사용할 수 있도록 함

    public User(String id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }
    // 생성자를 통해서 설정해줄 수 있음

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }


    public User get(String id) throws SQLException, ClassNotFoundException {
        Map<String, String> env = System.getenv(); // os의 환경변수를 불러올 수 있음
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(dbHost,dbUser,dbPassword);
        PreparedStatement ps = conn.prepareStatement("SELECT id, name, password FROM users WHERE id =?");
        // PreparedStatement: 객체를 캐시에 담아서 재사용 -> 반복적으로 쿼리를 실행할 경우 statement에 비해서 성ㅇ능이 좋음
        // statement는 보안적인 문제도 있어서 PreparedStatement가 좋음
        // https://cocodo.tistory.com/m/11

        ps.setString (1, id);
        ResultSet rs = ps.executeQuery();
        // executeQuery: result set을 만드는 sql문(주로 SELECT문)에서 사용
        // executeUpdate: INSERT나 UPDATE와 같은 DDL이나 DML을 실행할 때 사용
        // DDL(data definition languatge), DML(data manipulation language)

        rs.next();
        User user = new User(rs.getString("id"),
                rs.getString("name"), rs.getString("password"));

        rs.close();
        ps.close();
        conn.close();
        return user;
    }

    public User findAll(String id) throws ClassNotFoundException, SQLException {
        Map<String, String> env = System.getenv();
        String dbHost = env.get("DB_HOST");
        String dbUser = env.get("DB_USER");
        String dbPassword = env.get("DB_PASSWORD");

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(dbHost,dbUser,dbPassword);
        PreparedStatement ps = conn.prepareStatement("SELECT id, name, password FROM users WHERE id =?");
        ps.setString (1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User(rs.getString("id"),
                rs.getString("name"), rs.getString("password"));
        rs.close();
        ps.close();
        conn.close();
        return user;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        User user = new User();
        //userDao2.add();
        User user1 = user.get("1");
        System.out.println(user1.getName());
    }



}

