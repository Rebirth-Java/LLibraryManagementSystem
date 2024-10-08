package com.example.libraryboot.Service;

import com.example.libraryboot.Controller.Request.BaseRequest;
import com.example.libraryboot.Controller.Request.LoginRequest;
import com.example.libraryboot.Controller.Request.PasswordRequest;
import com.example.libraryboot.Controller.Request.UserPageRequest;
import com.example.libraryboot.Controller.dto.LogionDto;
import com.example.libraryboot.entity.Admin;
import com.example.libraryboot.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IAdminService {
    List<Admin> list();
    List<Admin> list1(Integer ID);
    PageInfo<Admin> page(BaseRequest baseRequest);

    void save(Admin obj);

    Admin getById(Integer id);

    void update(Admin obj);

    void DeleteById(Integer id);

   LogionDto login (LoginRequest request);

    void ChangePass(Admin admin);
}
