package com.example.libraryboot.Service.impl;

import com.example.libraryboot.Controller.Request.BaseRequest;
import com.example.libraryboot.Mapper.CategoryMapper;
import com.example.libraryboot.Service.ICategoryService;
import com.example.libraryboot.entity.Category;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class CategoryService implements ICategoryService {
    @Resource
    CategoryMapper categoryMapper;
    public List<Category> list() {
        return categoryMapper.list();
    }


    public List<Category> list1(Integer id) {
        return categoryMapper.list1(id);
    }

    @Override
    public PageInfo<Category> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
//        自关联查询
        List<Category> Categorys = categoryMapper.listByCondition(baseRequest);
        System.out.println(Categorys);
        return new PageInfo<>(Categorys);
    }

    @Override
    public void save(Category obj) {
        categoryMapper.save(obj);
    }

    @Override
    public Category getById(Integer id) {
        return  categoryMapper.getById(id);
    }

    @Override
    public void update(Category obj) {
        obj.setUpdatetime(LocalDate.now());
        System.out.println(obj.getUpdatetime());
categoryMapper.UpdateById(obj);
    }

    @Override
    public void DeleteById(Integer id) {
categoryMapper.DeleteById(id);
    }
}
