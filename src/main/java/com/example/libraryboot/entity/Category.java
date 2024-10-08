package com.example.libraryboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Category {
    private  Integer id;
    private  String name;
    private  String remark;
    private  Integer pid;
    @JsonFormat(pattern = "yy-MM-dd", timezone = "GMT+8")
    private LocalDate createtime;
    @JsonFormat(pattern = "yy-MM-dd", timezone = "GMT+8")
    private LocalDate updatetime;
    private List<Category> children;
}
