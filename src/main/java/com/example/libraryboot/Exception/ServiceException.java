package com.example.libraryboot.Exception;

public class ServiceException extends RuntimeException{
private  String code;
    public ServiceException(String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }

public  ServiceException(String message,Throwable cause){
        super(message,cause);


}


    public ServiceException(String code, String message) {

        super(message);
        this.code=code;
    }

}