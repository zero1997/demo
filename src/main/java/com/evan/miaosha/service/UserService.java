package com.evan.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evan.miaosha.dao.UserDao;
import com.evan.miaosha.domain.User;

/**
 * @author yangyifan05 <yangyifan05@kuaishou.com>
 * Created on 2020-07-31
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getById(int id) {
        return userDao.getById(id);
    }

    @Transactional
    public boolean tx() {
        User user1 = new User(2, "2222");
        userDao.insert(user1);

        userDao.insert(new User(1, "11111"));

        return true;
    }
}
