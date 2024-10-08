package com.example.libraryboot.Mapper;

import com.example.libraryboot.Controller.Request.BaseRequest;

import com.example.libraryboot.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {


    List<Book> list();

    List<Book> list1(Integer ID);

    List<Book> listByCondition(BaseRequest baseRequest);

    void save(Book Book);

    Book getById(Integer id);

    void UpdateById(Book Book);

    void DeleteById(Integer id);

    Book getByNo(String userNo);

    void updateNumByNo(String bookNo);
}



