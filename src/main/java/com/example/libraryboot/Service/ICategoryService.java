package com.example.libraryboot.Service;

import com.example.libraryboot.Controller.Request.BaseRequest;
import com.example.libraryboot.entity.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICategoryService {
    List<Category> list();
    List<Category> list1(Integer ID);
    PageInfo<Category> page(BaseRequest baseRequest);

    void save(Category obj);

    Category getById(Integer id);

    void update(Category obj);

    void DeleteById(Integer id);
}
