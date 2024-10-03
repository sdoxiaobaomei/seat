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
    //    @GetMapping
//    public JSONArray getAllUsers() {
//
//        final JSONObject jsonObject = JSON.parseObject(usersJson);
//        JSONArray users = (JSONArray) jsonObject.get("users");
//        System.out.println(users);
//
//        return users;
//    }

    @Autowired
    public UserController(UserService userService, UserDao userDao) {
        this.userService = userService;
        this.userDao = userDao;
    }

    final private UserService userService;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/{username}")
    UserVO getUserByUsername(@PathVariable("username") String username) {
        User userByName = userService.getUserByName(username);
        System.out.println(userByName);
        return new UserVO(userByName.getUsername(), userByName.getDisplayName(), userByName.getUserGroup());
    }

//    @GetMapping("/{id}")
//    UserVO getUserById(@PathVariable("id") String id) {
//        long i = Long.parseLong(id);
//        User user = userService.getUserById(i);
//        return new UserVO(user.getUsername(), user.getDisplayName(), user.getGroup());
//    }



    @GetMapping
    List<UserVO> getUsers() {
        List<User> allUsers = userService.getAllUsers();
        List<UserVO> userVOList = new ArrayList<>();
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
