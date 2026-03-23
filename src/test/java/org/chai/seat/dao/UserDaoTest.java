package org.chai.seat.dao;

import org.chai.seat.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testUserCRUD() {
        System.out.println("-------insert a user");
        User user = new User();
        user.setUsername("chai");
        user.setPassword("123456");
        user.setRole("CM");
        user.setUserGroup("CM");
        user.setDisplayName("吴天晨");
        int insert = userDao.insert(user);
        System.out.println("insert User number of: " + insert);
        System.out.println("-------select all users");

    }
}
