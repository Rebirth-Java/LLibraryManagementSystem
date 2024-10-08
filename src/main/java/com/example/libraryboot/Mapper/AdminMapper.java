package com.example.libraryboot.Mapper;

import com.example.libraryboot.Controller.Request.BaseRequest;
import com.example.libraryboot.Controller.Request.LoginRequest;
import com.example.libraryboot.Controller.Request.PasswordRequest;
import com.example.libraryboot.Controller.Request.UserPageRequest;
import com.example.libraryboot.entity.Admin;
import com.example.libraryboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {


    //    @Select("select*from user")
    List<Admin> list();
    List<Admin> list1(Integer ID);
  List<Admin>  listByCondition(BaseRequest baseRequest);
    void save(Admin obj);

    Admin getById(Integer id);

    void UpdateById(Admin obj);

    void DeleteById(Integer id);

    Admin getUsernameAndPassword(@Param("username") String username,@Param("password") String password);
Admin Isexist(@Param("username") String username,@Param("phone") String phone);

    Admin getByUsername(String username);
}
