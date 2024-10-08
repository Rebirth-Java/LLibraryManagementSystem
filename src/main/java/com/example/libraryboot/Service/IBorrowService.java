package com.example.libraryboot.Service;

import com.example.libraryboot.Controller.Request.BaseRequest;
import com.example.libraryboot.entity.Borrow;
import com.example.libraryboot.entity.Retur;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface IBorrowService {
    List<Borrow> list();
    List<Borrow> list1(Integer ID);
    PageInfo<Borrow> page(BaseRequest baseRequest);
    PageInfo<Retur> pageRetur(BaseRequest baseRequest);
    void saveRetur(Retur obj);
    void save(Borrow obj);

    Borrow getById(Integer id);

    void update(Borrow obj);

    void DeleteById(Integer id);
    void DeleteReturById(Integer id);


    List<Retur> listRetur(Integer id);

    Map<String ,Object> getCountByTimeRange(String timeRange);
}
