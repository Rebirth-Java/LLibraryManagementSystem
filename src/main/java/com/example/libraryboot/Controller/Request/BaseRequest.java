package com.example.libraryboot.Controller.Request;

import lombok.Data;

@Data
public class BaseRequest {
    private Integer PageNum=1;
    private Integer PageSize=10;
}
