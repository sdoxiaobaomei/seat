package org.chai.seat.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.chai.seat.entity.User;

public interface UserDao extends BaseMapper<User> {




    @Select("SELECT * FROM users WHERE username=#{username}")
    User selectUserByName (String username);

//    User selectUserById(int id);
}
