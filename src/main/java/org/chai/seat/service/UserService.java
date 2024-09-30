package org.chai.seat.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.chai.seat.dao.UserDao;
import org.chai.seat.entity.User;
import org.chai.seat.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUserByName (String username) {
        User user = new User();
        user.setUsername(username);
        return userDao.selectOne(new QueryWrapper<>(user));
    }

    public User getUserById(Long id) {
        userDao.selectById(id);
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = userDao.selectList(new QueryWrapper<>());
        System.out.println(users);
        return users;
    }

    public void insertUser(UserVO userVO) {
        User user = new User();
        user.setUsername(userVO.getUsername());
        user.setPassword(userVO.getPassword());
        user.setUserGroup(userVO.getUserGroup());
        user.setDisplayName(userVO.getDisplayName());
        user.setRole("default");
        int insert = userDao.insert(user);
        System.out.println("insert user result: " + insert);
    }
}
