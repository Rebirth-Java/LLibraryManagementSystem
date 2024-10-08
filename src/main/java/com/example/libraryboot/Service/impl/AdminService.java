package com.example.libraryboot.Service.impl;

import cn.hutool.core.text.PasswdStrength;
import cn.hutool.crypto.SecureUtil;
import com.example.libraryboot.Controller.Request.BaseRequest;
import com.example.libraryboot.Controller.Request.LoginRequest;
import com.example.libraryboot.Controller.Request.PasswordRequest;
import com.example.libraryboot.Controller.dto.LogionDto;
import com.example.libraryboot.Exception.ServiceException;
import com.example.libraryboot.Mapper.AdminMapper;
import com.example.libraryboot.Service.IAdminService;
import com.example.libraryboot.Utils.TokenUtils;
import com.example.libraryboot.entity.Admin;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminService implements IAdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    public List<Admin> list1(Integer id) {
        return adminMapper.list1(id);
    }

    @Override
    public PageInfo<Admin> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Admin> Admins = adminMapper.listByCondition(baseRequest);

        return new PageInfo<>(Admins);
    }

    @Override
    public void save(Admin obj) {
obj.setPassword(securePass(obj.getPassword()));
try {
    adminMapper.save(obj);
}catch (DuplicateKeyException e){

        log.error("数据插入失败,username:{}",obj.getUsername() ,e);
        throw  new ServiceException("用户重复已存在,请重新输入");
    }

}



    @Override
    public Admin getById(Integer id) {

        return adminMapper.getById(id);
    }

    @Override
    public void update(Admin obj) {
        try {
            String Ispass=obj.getPassword();
            if (Ispass.length()>10){
                obj.setPassword(Ispass);
            }else {
                String newpassword=  securePass(obj.getPassword());
                obj.setPassword(newpassword);
            }
obj.setUpdatetime(new Date());
            adminMapper.UpdateById(obj);
        }catch (DuplicateKeyException e){

            log.error("数据插入失败,username:{}",obj.getUsername() ,e);
            throw  new ServiceException("用户重复,请重新输入");
        }

    }

    @Override
    public void DeleteById(Integer id) {
        adminMapper.DeleteById(id);
    }

    @Override
    public LogionDto login(LoginRequest request) {

        Admin admin=null;
        try {
             admin=  adminMapper.getByUsername(request.getUsername());
        }catch (Exception e){
log.error("根据用户名查询出错",request.getUsername());
            throw  new ServiceException("用户名错误");
        }

//        Admin admin = adminMapper.getUsernameAndPassword(request.getUsername(),request.getPassword());
        if (admin == null) {
            throw new ServiceException("用户名或密码错误");
        }
        //判断密码是否合法
        String securePass123=securePass(request.getPassword());
        if (!securePass123.equals(admin.getPassword())){


            throw  new ServiceException("用户名或密码错误");
        }
        if(!admin.isStatus()){
            throw  new ServiceException("当前用户处于禁用状态,请联系管理员解除");
        }
        LogionDto logionDto = new LogionDto();
        BeanUtils.copyProperties(admin, logionDto);
     String token= TokenUtils.genToken(String.valueOf(admin.getId()),admin.getPassword());
        logionDto.setToken(token);
        return logionDto;

    }

    @Override
    public void ChangePass(Admin admin) {

//        加密新密码

  String phone=admin.getPhone();
  String username=admin.getUsername();
  adminMapper.Isexist(username,phone);
        Admin admin1 = adminMapper.Isexist(admin.getUsername(),admin.getPhone());
        if (admin1 == null) {
            throw new ServiceException("用户名或手机号输入错误");
        }else {
            admin.setPassword("123");
        }
}




    //封装md5加密算法
    private  String securePass(String password){
        return  SecureUtil.md5(password);
    }
}



