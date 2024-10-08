package com.example.libraryboot.Controller;

import com.example.libraryboot.Controller.Request.BorrowPageRequest;

import com.example.libraryboot.Service.IBorrowService;
import com.example.libraryboot.common.Result;
import com.example.libraryboot.entity.Borrow;
import com.example.libraryboot.entity.Borrow;
import com.example.libraryboot.entity.Retur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Borrow")
public class BorrowController {
    //标识都是以json格式传递的数据


        @Autowired
        IBorrowService borrowService;
        @GetMapping("/{id}")
        public Result getById(@PathVariable Integer id){
            Borrow obj=borrowService.getById(id);
            return  Result.Success(obj);
        }
        //查询
        @GetMapping("/list")
        public Result List() {
            List<Borrow> Borrows = borrowService.list();
            return Result.Success(Borrows);

        }//详情
        @GetMapping("/listID/{ID}")
        public Result List1( @PathVariable Integer ID) {
            List<Borrow> list = borrowService.list1(ID);
            return Result.Success(list);

        }
    @GetMapping("/listRetur/{ID}")
    public Result ListRetur( @PathVariable Integer ID) {
        List<Retur> list = borrowService.listRetur(ID);
        return Result.Success(list);

    }

        @PostMapping("/save")
        public  Result save(@RequestBody Borrow obj){
            borrowService.save(obj);

            return  Result.Success();
        }
        //修改
        @PutMapping("/update")
        public  Result update(@RequestBody Borrow obj){


            borrowService.update(obj);
            return  Result.Success();
        }
        //分页
        @GetMapping("/page")
        public Result Page(BorrowPageRequest pageRequest) {

            return Result.Success( borrowService.page(pageRequest));

        }
    @GetMapping("/pageRetur")
    public Result PageRetur(BorrowPageRequest pageRequest) {

        return Result.Success( borrowService.pageRetur(pageRequest));

    }
    @PostMapping("/saveRetur")
    public  Result saveRetur(@RequestBody Retur obj){

        borrowService.saveRetur(obj);

        return  Result.Success();
    }
    @DeleteMapping("/delete/{id}")
    public  Result deleteRetur(@PathVariable Integer id){
        borrowService.DeleteReturById(id);
        return  Result.Success();
    }
        //删除
        @DeleteMapping("/deleteRetur/{id}")
        public  Result delete(@PathVariable Integer id){
            borrowService.DeleteReturById(id);
            return  Result.Success();
        }
@GetMapping("/lineChars/{timeRange}")
    public  Result lineChars(@PathVariable String timeRange){
            return  Result.Success(borrowService.getCountByTimeRange(timeRange));
}
    }


