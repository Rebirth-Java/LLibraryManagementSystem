package com.example.libraryboot.Controller;

import com.example.libraryboot.Controller.Request.AdminPageRequest;
import com.example.libraryboot.Controller.Request.LoginRequest;
import com.example.libraryboot.Controller.dto.LogionDto;
import com.example.libraryboot.Service.IAdminService;

import com.example.libraryboot.common.Result;
import com.example.libraryboot.entity.Admin;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//标识都是以json格式传递的数据
@RestController
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    IAdminService adminService;
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Admin Admin = adminService.getById(id);
        return Result.Success(Admin);

    }
//查询
    @GetMapping("/list")
    public Result List() {
            List<Admin> Admins = adminService.list();
        return Result.Success(Admins);

    }//详情
    @GetMapping("/listID/{ID}")
    public Result List1( @PathVariable Integer ID) {
        List<Admin> list = adminService.list1(ID);
        return Result.Success(list);

    }
    @PostMapping("/login")
    public  Result login(@RequestBody LoginRequest request){
        LogionDto logionDto=adminService.login(request);
        return  Result.Success(logionDto);
    }
//    修改密码的接口
@PutMapping("/resetpassword")

    public  Result resetpassword(@RequestBody Admin admin){
    adminService.ChangePass(admin);
      return Result.Success();
    }
//新增
    @PostMapping("/save")
    public  Result save(@RequestBody Admin obj){
        adminService.save(obj);

        return  Result.Success();
    }
    //修改
    @PutMapping("/update")
    public  Result update(@RequestBody Admin obj){


        adminService.update(obj);
        return  Result.Success();
    }
    //分页
    @GetMapping("/page")
    public Result Page(AdminPageRequest pageRequest) {

        return Result.Success( adminService.page(pageRequest));

    }
//删除
    @DeleteMapping("/delete/{id}")
    public  Result delete(@PathVariable Integer id){
        adminService.DeleteById(id);
        return  Result.Success();
    }

}
