package com.example.libraryboot.Service.impl;

import cn.hutool.core.collection.CollUtil;
import com.example.libraryboot.Controller.Request.BaseRequest;
import com.example.libraryboot.Exception.ServiceException;
import com.example.libraryboot.Mapper.BookMapper;

import com.example.libraryboot.Service.IBookService;
import com.example.libraryboot.entity.Book;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class BookService implements IBookService {
    @Resource
    BookMapper bookMapper;
    public List<Book> list() {
        return bookMapper.list();
    }

    public List<Book> list1(Integer id) {
        return bookMapper.list1(id);
    }

    @Override
    public PageInfo<Book> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
//        自关联查询

        return new PageInfo<>(bookMapper.listByCondition(baseRequest));
    }

    @Override
    public void save(Book obj) {
        try{
            obj.setCategory(Category(obj.getCategories()));
            bookMapper.save(obj);
        }catch (Exception e){

throw  new ServiceException("数据插入错误",e);
        }

    }

    @Override
    public Book getById(Integer id) {
        return  bookMapper.getById(id);
    }

    @Override
    public void update(Book obj) {

        try{
            obj.setCategory(Category(obj.getCategories()));
            obj.setUpdatetime(LocalDate.now());
            bookMapper.UpdateById(obj);
        }catch (Exception e){
            throw  new ServiceException("数据更新错误",e);
        }

    }

    @Override
    public void DeleteById(Integer id) {
bookMapper.DeleteById(id);
    }

    private String Category(List<String> categories){

        StringBuilder sb=new StringBuilder();
        if (CollUtil.isNotEmpty(categories)){
            categories.forEach(v->sb.append(v).append(">"));

       return   sb.substring(0,sb.lastIndexOf(">"));
        }
return  sb.toString();
    }
}

