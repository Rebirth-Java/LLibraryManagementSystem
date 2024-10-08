package com.example.libraryboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class LibraryBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryBootApplication.class, args);
    }
//健康检查接口
@GetMapping
    public  String Success(){
        return  "Success";
}

}

