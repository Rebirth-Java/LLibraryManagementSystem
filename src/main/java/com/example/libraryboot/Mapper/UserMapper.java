package com.example.libraryboot.Mapper;

import com.example.libraryboot.Controller.Request.BaseRequest;
import com.example.libraryboot.Controller.Request.UserPageRequest;
import com.example.libraryboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.yaml.snakeyaml.events.Event;

import java.util.List;
@Mapper
public interface UserMapper {


    //    @Select("select*from user")
    List<User> list();
    List<User> list1(Integer ID);
  List<User>  listByCondition(BaseRequest baseRequest);
    void save(User user);

    User getById(Integer id);

    void UpdateById(User user);

    void DeleteById(Integer id);

    User getByUserName(String userName);


}
