package dblinking.dao;

import dblinking.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;

    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {
        UserDao userDao = context.getBean("localConnectionMaker", UserDao.class);
        User user = new User("9", "EternityHwan", "11234");
        userDao.add(user);

        User selectedUser = userDao.findById("8");
        Assertions.assertEquals("EternityHwan", selectedUser.getName());
    }

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {

        UserDao userDao = context.getBean("localConnectionMaker", UserDao.class);
        userDao.deleteAll();
        assertEquals(userDao.getCount(), 0);

        User user = new User("101", "박성철", "1122345");

        userDao.add(user);
        assertEquals(userDao.getCount(), 1);

        User user2 = userDao.findById("101");

        assertEquals(user2.getName(), "박성철");

    }



}