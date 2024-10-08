package com.example.libraryboot.Controller;

import com.example.libraryboot.Controller.Request.UserPageRequest;
import com.example.libraryboot.Mapper.UserMapper;
import com.example.libraryboot.Service.IUserService;
import com.example.libraryboot.common.Result;
import com.example.libraryboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
//标识都是以json格式传递的数据
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        User user = userService.getById(id);
        return Result.Success(user);

    }
//查询
    @GetMapping("/list")
    public Result List() {
        List<User> users = userService.list();
        return Result.Success(users);

    }//详情
    @GetMapping("/listID/{ID}")
    public Result List1( @PathVariable Integer ID) {
        List<User> users = userService.list1(ID);
        return Result.Success(users);

    }

//新增
    @PostMapping("/save")
    public  Result save(@RequestBody User user){
        userService.save(user);
        return  Result.Success();
    }
    @PostMapping("/account")
    public  Result account(@RequestBody User user){
        userService.handleAccount(user);
        return  Result.Success();
    }
    //修改
    @PutMapping("/update")
    public  Result update(@RequestBody User user){
        userService.update(user);
        return  Result.Success();
    }
    //分页
    @GetMapping("/page")
    public Result Page(UserPageRequest userPageRequest) {

        return Result.Success( userService.page(userPageRequest));

    }
//删除
    @DeleteMapping("/delete/{id}")
    public  Result delete(@PathVariable Integer id){
        userService.DeleteById(id);
        return  Result.Success();
    }

}
