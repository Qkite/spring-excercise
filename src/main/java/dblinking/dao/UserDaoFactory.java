package dblinking.dao;

import dblinking.connection_maker.AWSConnectionMaker;
import dblinking.connection_maker.LocalConnectionMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDaoFactory {

    @Bean
    public UserDao awsConnectionMaker(){
        AWSConnectionMaker awsConnectionMaker = new AWSConnectionMaker();
        UserDao userDao = new UserDao();
        return userDao;
    }

    @Bean
    public UserDao localConnectionMaker(){
        LocalConnectionMaker localConnectionMaker = new LocalConnectionMaker();
        UserDao userDao = new UserDao();
        return userDao;
    }
}
