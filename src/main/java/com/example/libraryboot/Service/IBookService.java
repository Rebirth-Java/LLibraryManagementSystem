package com.example.libraryboot.Service;

import com.example.libraryboot.Controller.Request.BaseRequest;
import com.example.libraryboot.entity.Book;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IBookService {
    List<Book> list();
    List<Book> list1(Integer ID);
    PageInfo<Book> page(BaseRequest baseRequest);

    void save(Book obj);

    Book getById(Integer id);

    void update(Book obj);

    void DeleteById(Integer id);
}
