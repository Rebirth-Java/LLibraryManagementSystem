package com.example.libraryboot.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
public class Borrow extends BaseEntity implements Serializable {
    /**
     * @description borrow
     * @author JSON.CN
     * @date 2024-08-28
     */


    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    private Integer id;

    /**
     * 图书名称
     */
    private String bookName;
    private String userNo;
    /**
     * 图书标准码
     */
    private String bookNo;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户联系方式
     */
    private String userPhone;
    private int nums;

    /**
     * 创建时间
     */

    /**
     * 借书积分
     */
    private Integer score;
    private String status;
    private Integer days;
    private LocalDate returnDate;
//    提醒状态
    private String note;
}

