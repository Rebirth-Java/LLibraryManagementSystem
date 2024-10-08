package com.example.libraryboot.Service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.example.libraryboot.Controller.Request.BaseRequest;
import com.example.libraryboot.Controller.Request.UserPageRequest;
import com.example.libraryboot.Mapper.UserMapper;
import com.example.libraryboot.Service.IUserService;
import com.example.libraryboot.entity.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
@Service
public class UserService implements IUserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public List<User> list() {
        return userMapper.list();
    }
    public List<User> list1(Integer ID) {
        return userMapper.list1(ID);
    }

    @Override
    public PageInfo<User> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(),baseRequest.getPageSize());
     List<User> users= userMapper.listByCondition(baseRequest);

       return new PageInfo<>(users);
    }

    @Override
    public void save(User user) {
        Date date = new Date();
        //当卡号处理
        user.setUsername(DateUtil.format(date ,"MMDDYY") +Math.abs(IdUtil.fastSimpleUUID().hashCode()) );
        userMapper.save(user);
    }

    @Override
    public User getById(Integer id) {

        return userMapper.getById(id);
    }

    @Override
    public void update(User user) {
        user.setUpdatetime(new Date());
        userMapper.UpdateById(user);
    }

    @Override
    public void DeleteById(Integer id) {
        userMapper.DeleteById(id);
    }

    @Override
    public void handleAccount(User user) {
        Integer score = user.getScore();
        if (score==null){
            return;
        }
        Integer id=user.getID();
        User dbUser= userMapper.getById(id);
        dbUser.setAccount(dbUser.getAccount()+score);
        userMapper.UpdateById(dbUser);
    }


}
