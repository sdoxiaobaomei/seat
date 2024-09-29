package org.chai.seat.dao;


import org.chai.seat.entity.User;

public interface UserDao {


    User selectUserByName (String username);

}
