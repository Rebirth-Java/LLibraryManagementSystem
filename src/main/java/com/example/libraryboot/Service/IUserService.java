package com.example.libraryboot.Service;

import com.example.libraryboot.Controller.Request.BaseRequest;
import com.example.libraryboot.Controller.Request.UserPageRequest;
import com.example.libraryboot.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IUserService {
    List<User> list();
    List<User> list1(Integer ID);
   PageInfo<User> page(BaseRequest baseRequest);

    void save(User user);

    User getById(Integer id);

    void update(User user);

    void DeleteById(Integer id);

    void handleAccount(User user);
}
