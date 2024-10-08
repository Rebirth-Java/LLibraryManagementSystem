package com.example.libraryboot.Mapper;

import com.example.libraryboot.Controller.Request.BaseRequest;
import com.example.libraryboot.Controller.Request.BorrowReturCountPO;
import com.example.libraryboot.entity.Borrow;
import com.example.libraryboot.entity.Retur;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface BorrowMapper {
    List<Borrow> list();
    List<Borrow> list1(Integer ID);
    List<Borrow>  listByCondition(BaseRequest baseRequest);
    List<Retur>  listReturByCondition(BaseRequest baseRequest);
    void save(Borrow borrow);
    void saveRetur(Retur retur);
    Borrow getById(Integer id);

    void UpdateById(Borrow borrow);

    void DeleteById(Integer id);

    void updateStatus(@Param("status")String status,@Param("id") Integer id);
    void DeleteReturById(Integer id);

    List<Retur> listRetur(Integer id);

    List<BorrowReturCountPO> getCountByTimeRange(@Param("timeRange") String timeRange, @Param("type") int type);
}
