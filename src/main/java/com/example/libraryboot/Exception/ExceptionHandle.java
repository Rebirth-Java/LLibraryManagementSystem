package com.example.libraryboot.Exception;



import cn.hutool.core.util.StrUtil;
import com.example.libraryboot.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = ServiceException.class)
    public Result serviceExceptionError(ServiceException e) {
        log.error("业务异常", e);
        String code=e.getCode();
        if (StrUtil.isBlank(code)){
            return Result.Error(code,e.getMessage());
        }
        return Result.Error(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public Result exceptionError(Exception e) {
        log.error("系统错误", e);
        return Result.Error("系统错误");
    }

}