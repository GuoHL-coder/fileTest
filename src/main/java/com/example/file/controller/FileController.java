package com.example.file.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("file")
@Slf4j
public class FileController {
//    test 1
//    test 2
//    test 3

    @RequestMapping("/hello")
    public String hello(){
        log.info("Hello New Page");
        return "index";
    }

    @RequestMapping("uploadByStream")
    public String uploadByStream(@RequestParam("fileName")MultipartFile file){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String path = "/Users/guohualiang/Documents/Java/Demo/fileTest/"+file.getOriginalFilename();

        try {
            inputStream = file.getInputStream();
            outputStream = new FileOutputStream(path);

            int read = 0;
            while ((read=inputStream.read()) != -1){
                System.out.println(read);
                outputStream.write(read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }


    @RequestMapping("uploadByStream1")
    public String uploadByStream1(@RequestParam("fileName")MultipartFile file){

        if (file.isEmpty()){
            System.out.println("文件内容为空！！！！");
            return "uploadError";
        }
        String desPath = "/Users/guohualiang/Documents/Java/Demo/fileTest/"+file.getOriginalFilename();
        System.out.println(desPath);
        long fileSize = file.getSize();


        // 当文件有后缀名时
        if (desPath.indexOf(".") >= 0) {
            // split()中放正则表达式; 转义字符"\\."代表 "."
            String[] fileNameSplitArray = desPath.split("\\.");
            // 加上random戳,防止附件重名覆盖原文件
            desPath = fileNameSplitArray[0] + (int) (Math.random() * 100000) + "." + fileNameSplitArray[1];
        }

        try {
            file.transferTo(Paths.get(desPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }

    @RequestMapping("uploadByStream2")
    public String uploadByStream2(@RequestParam("fileNames")MultipartFile[] files){

        if (files == null  || files.length <= 0){
            System.out.println("文件内容为空！！！！");
            return "uploadError";
        }

        String desPath = "/Users/guohualiang/Documents/Java/Demo/fileTest/";
        for (MultipartFile thisFile: files) {
            if (thisFile.isEmpty()){
                System.out.println("文件内容为空#####");
                return "uploadError";
            }
            String fileOriginalFilename = thisFile.getOriginalFilename();
            // 当文件有后缀名时
            String thisDesPath = desPath;
            if (fileOriginalFilename.indexOf(".") >= 0) {
                // split()中放正则表达式; 转义字符"\\."代表 "."
                String[] fileNameSplitArray = fileOriginalFilename.split("\\.");
                // 加上random戳,防止附件重名覆盖原文件
                thisDesPath += fileNameSplitArray[0] + (int) (Math.random() * 100000) + "." + fileNameSplitArray[1];
            }
            try {
                thisFile.transferTo(Paths.get(thisDesPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "success";
    }



}
