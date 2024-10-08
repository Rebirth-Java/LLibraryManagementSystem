package com.example.libraryboot.Controller.Request;

import lombok.Data;
import org.omg.PortableInterceptor.INACTIVE;

@Data
public class BorrowReturCountPO {
    private  String date;
    private Integer count;
}
