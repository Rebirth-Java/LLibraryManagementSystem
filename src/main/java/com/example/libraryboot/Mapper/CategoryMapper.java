package com.example.libraryboot.Mapper;

import com.example.libraryboot.Controller.Request.BaseRequest;
import com.example.libraryboot.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CategoryMapper {
    List<Category> list();
    List<Category> list1(Integer ID);
    List<Category>  listByCondition(BaseRequest baseRequest);
    void save(Category Category);

    Category getById(Integer id);

    void UpdateById(Category Category);

    void DeleteById(Integer id);
}
