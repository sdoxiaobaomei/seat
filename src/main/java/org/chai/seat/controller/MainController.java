package org.chai.seat.controller;


import com.alibaba.fastjson2.JSON;
import org.chai.seat.vo.UserVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class MainController {
    String usersJson = "{ \"users\": [\n" +
            "    {\n" +
            "      \"id\": \"1\",\n" +
            "      \"username\": \"admin\",\n" +
            "      \"password\": \"\",\n" +
            "      \"displayname\": \"管理员\",\n" +
            "      \"group\": \"EE\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"2\",\n" +
            "      \"username\": \"kevin\",\n" +
            "      \"password\": \"\",\n" +
            "      \"displayname\": \"吴天晨\",\n" +
            "      \"group\": \"CM\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"3\",\n" +
            "      \"username\": \"summer\",\n" +
            "      \"password\": \"\",\n" +
            "      \"displayname\": \"徐露赛\",\n" +
            "      \"group\": \"Galaxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"4\",\n" +
            "      \"username\": \"xiaojia\",\n" +
            "      \"password\": \"\",\n" +
            "      \"displayname\": \"\",\n" +
            "      \"group\": \"Galaxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"5\",\n" +
            "      \"username\": \"zhenjiang\",\n" +
            "      \"password\": \"\",\n" +
            "      \"displayname\": \"\",\n" +
            "      \"group\": \"Rocket\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"6\",\n" +
            "      \"username\": \"zhemin\",\n" +
            "      \"password\": \"\",\n" +
            "      \"displayname\": \"\",\n" +
            "      \"group\": \"Galaxy\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"7\",\n" +
            "      \"username\": \"yiying\",\n" +
            "      \"password\": \"\",\n" +
            "      \"displayname\": \"\",\n" +
            "      \"group\": \"Rocket\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"8\",\n" +
            "      \"username\": \"hong\",\n" +
            "      \"password\": \"\",\n" +
            "      \"displayname\": \"\",\n" +
            "      \"group\": \"\"\n" +
            "    }\n" +
            "  ]}";

//    @GetMapping
//    public JSONArray getAllUsers() {
//
//        final JSONObject jsonObject = JSON.parseObject(usersJson);
//        JSONArray users = (JSONArray) jsonObject.get("users");
//        System.out.println(users);
//
//        return users;
//    }

    @GetMapping
    UserVO getUserByUsername(@RequestParam String username) {
        List<UserVO> userVOList = JSON.parseArray(usersJson, UserVO.class);
        return userVOList.stream().filter(user-> user.getUsername().equals(username)).findFirst().get();
    }

}
