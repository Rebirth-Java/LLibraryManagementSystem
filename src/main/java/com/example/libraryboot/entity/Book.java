package com.example.libraryboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class Book extends BaseEntity implements Serializable {



    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 出版日期
     */
    private String publishDate;

    /**
     * 作者
     */
    private String author;

    /**
     * 出版社
     */
    private String publisher;

    /**
     * 分类
     */
    private String category;

    /**
     * 标准书号
     */
    private String bookNo;

    /**
     * 封面
     */
    private String cover;

private List<String> categories;

private  Integer score;
    private  int nums;

}