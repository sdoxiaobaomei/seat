package org.chai.seat.controller;


import com.alibaba.fastjson2.JSON;
import org.chai.seat.entity.User;
import org.chai.seat.service.UserService;
import org.chai.seat.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
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
    public UserController(UserService userService) {
        this.userService = userService;
    }

    final private UserService userService;

    @GetMapping
    UserVO getUserByUsername(@RequestParam String username) {
        User userByName = userService.getUserByName(username);
        System.out.println(userByName);
        return new UserVO(userByName.getUsername(), userByName.getDisplayName(), userByName.getGroup());
    }

}
