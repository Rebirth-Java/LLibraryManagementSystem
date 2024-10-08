package com.example.libraryboot.Service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.libraryboot.Controller.Request.BaseRequest;
import com.example.libraryboot.Controller.Request.BorrowReturCountPO;
import com.example.libraryboot.Exception.ServiceException;
import com.example.libraryboot.Mapper.BookMapper;
import com.example.libraryboot.Mapper.BorrowMapper;

import com.example.libraryboot.Mapper.UserMapper;
import com.example.libraryboot.Service.IBorrowService;
import com.example.libraryboot.Service.IBorrowService;
import com.example.libraryboot.entity.Book;
import com.example.libraryboot.entity.Borrow;
import com.example.libraryboot.entity.Retur;
import com.example.libraryboot.entity.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.Transient;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Slf4j
public class BorrowService implements IBorrowService {
    @Resource
    BorrowMapper borrowMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    BookMapper bookMapper;


    public List<Borrow> list() {
        return borrowMapper.list();
    }

    public List<Borrow> list1(Integer id) {
        return borrowMapper.list1(id);
    }

    @Override
    public PageInfo<Borrow> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
//        自关联查询
        List<Borrow> borrows = borrowMapper.listByCondition(baseRequest);
        for (Borrow borrow : borrows) {
            LocalDate returnDate = borrow.getReturnDate();
            LocalDate now = LocalDate.now();
            if (now.plusDays(1).isEqual(returnDate)) {
                borrow.setNote("即将到期");
            } else if (now.equals(returnDate)) {
                borrow.setNote("已到期");
            } else if (now.isAfter(returnDate)) {
                borrow.setNote("已过期");
            } else {
                borrow.setNote("正常");
            }

        }
        return new PageInfo<>(borrows);
    }

    @Override
    public PageInfo<Retur> pageRetur(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());

        List<Retur> returs = borrowMapper.listReturByCondition(baseRequest);
        return new PageInfo<>(returs);
    }

    @Transactional
    @Override
    public void saveRetur(Retur obj) {
        obj.setStatus("已归还");
        int id = obj.getId();
        String status = obj.getStatus();
        borrowMapper.updateStatus(status, id);
        obj.setRealDate(LocalDate.now());
        borrowMapper.saveRetur(obj);
//        obj.setId(null);
        //新增数据
        bookMapper.updateNumByNo(obj.getBookNo());
        //返还用户积分
        Book book = bookMapper.getByNo(obj.getBookNo());
        if (book != null) {
            long until = 0;
            if (obj.getRealDate().isBefore(obj.getReturnDate())) {
                until = obj.getRealDate().until(obj.getReturnDate(), ChronoUnit.DAYS);
            } else if (obj.getRealDate().isAfter(obj.getReturnDate())) {
                until = -obj.getReturnDate().until(obj.getRealDate(), ChronoUnit.DAYS);
            }
            int score = (int) until * book.getScore();//获取待归还的积分
            User user = userMapper.getByUserName(obj.getUserNo());
            int account = user.getAccount() + score;
            user.setAccount(account);
            if (score < 0 && account < 0) {
                user.setStatus(false);
            }
            userMapper.UpdateById(user);
        }

    }


    @Override
    @Transactional
    public void save(Borrow obj) {
//1.校验用户积分是否足够
        String userNo = obj.getUserNo();
        User user = userMapper.getByUserName(userNo);
        if (Objects.isNull(user)) {
            throw new ServiceException("用户不存在");

        }
        //2.校验图书馆的数量是否足够
        Book book = bookMapper.getByNo(obj.getBookNo());
        if (Objects.isNull(book)) {
            throw new ServiceException("所借图书不存在");
        }
        if (book.getNums() < 1) {
            throw new ServiceException("图书数量不足");
        }
        Integer account = user.getAccount();
        Integer score = book.getScore() * obj.getDays();//借一本的天数
//3.校验用户余额
        if (score > account) {
            throw new ServiceException("用户积分不足");
        }
        //4.校验用户余额

        //更新余额
        user.setAccount(user.getAccount() - score);
        userMapper.UpdateById(user);
//更新图书的数量
        int nums = book.getNums() - 1;
        book.setNums(nums);
        bookMapper.UpdateById(book);

        obj.setNums(nums);
        obj.setReturnDate(LocalDate.now().plus(obj.getDays(), ChronoUnit.DAYS));//当前日期加上days得到归还的日期
        //新增借书纪录
        obj.setScore(score);
        borrowMapper.save(obj);
    }

    @Override
    public Borrow getById(Integer id) {
        return borrowMapper.getById(id);
    }

    @Override
    public void update(Borrow obj) {

        obj.setUpdatetime(LocalDate.now());
        borrowMapper.UpdateById(obj);
    }

    @Override
    public void DeleteById(Integer id) {
        borrowMapper.DeleteById(id);
    }

    @Override
    public void DeleteReturById(Integer id) {
        borrowMapper.DeleteReturById(id);
    }

    @Override
    public List<Retur> listRetur(Integer id) {

        return borrowMapper.listRetur(id);
    }

    @Override
    public Map<String, Object> getCountByTimeRange(String timeRange) {
        Map<String, Object> map = new HashMap<>();
        Date today = new Date();
        List<DateTime> dateRange;
        switch (timeRange) {
            case "week":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -6), today, DateField.DAY_OF_WEEK);
                break;

            case "month":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -29), today, DateField.DAY_OF_MONTH);
                break;

            case "month1":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -59), today, DateField.DAY_OF_MONTH);
                break;
            case "month2":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -89), today, DateField.DAY_OF_MONTH);
                break;
            default:
                dateRange = new ArrayList<>();
        }
        List<String> dateStrRange = datetimeToDateStr(dateRange);
        map.put("date", dateStrRange);
        List<BorrowReturCountPO> borrowCount = borrowMapper.getCountByTimeRange(timeRange, 1);
        List<BorrowReturCountPO> returnCount = borrowMapper.getCountByTimeRange(timeRange, 2);
        map.put("borrow", countList(borrowCount, dateStrRange));
        map.put("retur", countList(returnCount, dateStrRange));
        return map;
    }

    private List<String> datetimeToDateStr(List<DateTime> dateTimeList) {
        List<String> list = CollUtil.newArrayList();
        if (CollUtil.isEmpty(dateTimeList)) {
            return list;
        }
        for (DateTime datetime : dateTimeList) {
            String date = DateUtil.formatDate(datetime);
            list.add(date);

        }
        return  list;
    }
private  List<Integer> countList(List<BorrowReturCountPO> countPOList, List<String> dateRange){
        List<Integer> list=CollUtil.newArrayList();
        if (CollUtil.isEmpty(countPOList)){
            return  list;
        }
    for (String date:dateRange
         ) {
        Integer count=countPOList.stream().filter(countPO->date.equals(countPO.getDate()))
                .map(BorrowReturCountPO::getCount).findFirst().orElse(0);
        list.add(count);
    }
    return  list;
}
}

