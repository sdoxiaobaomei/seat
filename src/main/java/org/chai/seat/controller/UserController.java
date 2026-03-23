package org.chai.seat.controller;


import com.alibaba.fastjson2.JSON;
import org.chai.seat.dao.UserDao;
import org.chai.seat.entity.User;
import org.chai.seat.service.UserService;
import org.chai.seat.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserDao userDao;

    @Autowired
    public UserController(UserService userService, UserDao userDao) {
        this.userService = userService;
        this.userDao = userDao;
    }

    final private UserService userService;

    @CrossOrigin(origins = "*")
    @GetMapping("/{username}")
    UserVO getUserByUsername(@PathVariable("username") String username) {
        User userByName = userService.getUserByName(username);
        System.out.println(userByName);
        return new UserVO(userByName.getUsername(), userByName.getDisplayName(), userByName.getUserGroup());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("")
    List<UserVO> getUsers(@RequestParam(value = "username", required = false) String username) {
        List<User> users;
        if (username != null && !username.isEmpty()) {
            users = new ArrayList<>();
            User user = userService.getUserByName(username);
            if (user != null) {
                users.add(user);
            }
        } else {
            users = userService.getAllUsers();
        }
        List<UserVO> userVOList = new ArrayList<>();
        for (User u : users) {
            userVOList.add(new UserVO(u.getUsername(), u.getDisplayName(), u.getUserGroup()));
        }
        return userVOList;
    }

    @PostMapping
    void insertUser(UserVO userVO) {
        userService.insertUser(userVO);
    }

    @PutMapping
    void updateUser(UserVO userVO) {
        userService.updateUser(userVO);
    }

    @DeleteMapping("{id}")
    void deleteUser(Integer id) {
        userService.deleteUser(id);
    }

}
