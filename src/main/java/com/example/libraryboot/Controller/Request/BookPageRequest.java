package com.example.libraryboot.Controller.Request;

import lombok.Data;

@Data
public class BookPageRequest extends  BaseRequest{
    private  String name;
    private String bookNo;
}
