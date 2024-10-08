package com.example.libraryboot.Controller;

import cn.hutool.core.collection.CollUtil;
import com.example.libraryboot.Controller.Request.CategoryPageRequest;
import com.example.libraryboot.Service.ICategoryService;
import com.example.libraryboot.common.Result;
import com.example.libraryboot.entity.Book;
import com.example.libraryboot.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


//标识都是以json格式传递的数据
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    ICategoryService categoryService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Category obj = categoryService.getById(id);
        return Result.Success(obj);
    }
//查询

    @GetMapping("/list")
    public Result List() {
        List<Category> Categorys = categoryService.list();
        return Result.Success(Categorys);

    }

    //
    @GetMapping("/tree")
    public Result tree() {
        List<Category> list = categoryService.list();
        //对list进行操作


        return Result.Success(createTree(null, list));

    }

    private List<Category> createTree(Integer pid, List<Category> categories) {

        List<Category> treeList = new ArrayList<>();

        for (Category category : categories) {
            if (pid == null) {
                if (category.getPid() == null) {//第一节点
                    treeList.add(category);
                    category.setChildren(createTree(category.getId(), categories));
                }
            } else {
                if (pid.equals(category.getPid())) {
                    treeList.add(category);
                    category.setChildren(createTree(category.getId(), categories));
                }
            }
            if (CollUtil.isEmpty(category.getChildren())) {
                category.setChildren(null);
            }
        }
        return treeList;
    }

    //详情
    @GetMapping("/listID/{ID}")
    public Result List1(@PathVariable Integer ID) {
        List<Category> list = categoryService.list1(ID);
        return Result.Success(list);

    }

    @PostMapping("/save")
    public Result save(@RequestBody Category obj) {

        categoryService.save(obj);

        return Result.Success();
    }

    //修改
    @PutMapping("/update")
    public Result update(@RequestBody Category obj) {
        categoryService.update(obj);
        return Result.Success();
    }

    //分页
    @GetMapping("/page")
    public Result Page(CategoryPageRequest pageRequest) {

        return Result.Success(categoryService.page(pageRequest));

    }

    //删除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        categoryService.DeleteById(id);
        return Result.Success();
    }

}
