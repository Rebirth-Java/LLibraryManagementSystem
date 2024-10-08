package com.example.libraryboot.Controller;

import cn.hutool.core.io.FileUtil;

import cn.hutool.core.util.StrUtil;
import com.example.libraryboot.Controller.Request.BookPageRequest;
import com.example.libraryboot.Service.IBookService;

import com.example.libraryboot.Utils.TokenUtils;
import com.example.libraryboot.common.Result;
import com.example.libraryboot.entity.Admin;
import com.example.libraryboot.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {
    //标识都是以json格式传递的数据


    @Autowired
    IBookService bookService;

  private  static  final   String BASE_FILE_PATH = System.getProperty("user.dir") + "/files/";

    @PostMapping("/file/upload")



    public Result uploadFile(MultipartFile file)  {
        String originalFilename = file.getOriginalFilename();
        if (StrUtil.isBlank(originalFilename)){
            return Result.Error("文件上传失败");
        }
        long flag = System.currentTimeMillis();
        String filePath = BASE_FILE_PATH + flag + "-" + originalFilename;

        try {


            FileUtil.mkParentDirs(filePath);//创建父级目录
            file.transferTo(FileUtil.file(filePath));
            Admin currentAdmin=TokenUtils.getCurrentAdmin();
            String token= TokenUtils.genToken(currentAdmin.getId().toString(),currentAdmin.getPassword().toString(),15);
            String url="http://localhost:8090/api/book/file/download/"+ flag+"?token="+token;
            if (originalFilename.endsWith("png")||originalFilename.endsWith("jpg")|| originalFilename.endsWith("pdf"))
            return Result.Success(url);
        }catch (Exception e){
            log.error("文件上传失败",e);
        }
return  Result.Error("文件上传失败");

    }

    @GetMapping("/file/download/{flag}")
    public void download(@PathVariable String flag, @RequestParam(required = false) String play, HttpServletResponse response){
        OutputStream os;
        List<String> fileNames = FileUtil.listFileNames(BASE_FILE_PATH);
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");


        try{
            if (StrUtil.isNotEmpty(fileName)) {
                String realName = fileName.substring(fileName.indexOf("_") + 1);
                if ("1".equals(play)) {

                    response.addHeader("Content-Disposition", "inline,filename=" + URLEncoder.encode(realName, "UTF-8"));
                } else {
                    response.addHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode(realName, "UTF-8"));
                    byte[] bytes = FileUtil.readBytes(BASE_FILE_PATH + fileName);
                    os = response.getOutputStream();
                    os.write(bytes);
                    os.flush();
                    os.close();
                }
            }
        } catch(Exception e) {
log.error("数据失败",e);
        }
    }







            @GetMapping("/{id}")
            public Result getById (@PathVariable Integer id){
                Book obj = bookService.getById(id);
                return Result.Success(obj);
            }

            //查询
            @GetMapping("/list")
            public Result List () {
                List<Book> Books = bookService.list();
                return Result.Success(Books);

            }//详情

            @GetMapping("/listID/{ID}")
            public Result List1 (@PathVariable Integer ID){
                List<Book> list = bookService.list1(ID);
                return Result.Success(list);

            }

            @PostMapping("/save")
            public Result save (@RequestBody Book obj){
                bookService.save(obj);

                return Result.Success();
            }

            //修改
            @PutMapping("/update")
            public Result update (@RequestBody Book obj){


                bookService.update(obj);
                return Result.Success();
            }

            //分页
            @GetMapping("/page")
            public Result Page (BookPageRequest pageRequest){

                return Result.Success(bookService.page(pageRequest));

            }

            //删除
            @DeleteMapping("/delete/{id}")
            public Result delete (@PathVariable Integer id){
                bookService.DeleteById(id);
                return Result.Success();
            }

        }


