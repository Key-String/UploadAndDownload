package com.wrp.save.controller;

import com.wrp.save.entity.UserFile;
import com.wrp.save.result.Result;
import com.wrp.save.result.ResultFactory;
import com.wrp.save.service.UserFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Date;
@CrossOrigin
@RestController
public class UserFileController {
    @Autowired
    UserFileService userFileService;
    private static final Logger logger = LoggerFactory.getLogger(UserFileController.class);
    //在文件操作中，不用/或者\最好，推荐使用File.separator
    private final static String  fileDir="files";
    private  final static String rootPath = System.getProperty("user.home")+File.separator+fileDir+File.separator;
    @PostMapping("/api/upload")
    public Result coversUpload(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得文件：
        MultipartFile file = multipartRequest.getFile("file");
        String folder = "D:/vue/wrp-vue/src/11111/File";
        File imageFolder = new File(folder);
        UserFile userFile=new UserFile();
        File f = new File(imageFolder, file.getOriginalFilename());
        java.util.Date cl=new java.util.Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str=sdf.format(cl);
        System.out.println(str);
        java.sql.Date date=new java.sql.Date(cl.getTime());
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            System.out.println(f.length());
            float l=f.length()/1024;
            userFile.setUserid(3);
            userFile.setName(f.getName());
             userFile.setUrl( "11111/File/" + f.getName());
             userFile.setDate(date);
             userFile.setLenght(l);
            userFileService.Save(userFile);
            return ResultFactory.buildSuccessResult("上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("上传失败");
        }
    }
    @GetMapping("/api/filelist")
    public List<UserFile> Files(){
        return userFileService.findAll();
    }
    @PostMapping("/api/download")
    public String downloadFile(@RequestBody Map<String,String> map, HttpServletResponse response) {
        FileInputStream fis = null; //文件输入流
        BufferedInputStream bis = null;
        OutputStream os = null; //输出流
        byte[] buffer = new byte[1024];
        int len=0;
        try{
            String filename=map.get("filename");
            String filePath = "D:/vue/wrp-vue/src/11111/File/";
            System.out.println(filename);
            File file = new File(filePath + filename);
            if (file.exists()) { //判断文件父目录是否存在
                response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
                response.setHeader("Access-Control-Allow-Credentials", "true");
                //response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
                //response.setHeader("Access-Control-Max-Age", "3600");
                //response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
                response.setHeader("content-Type", "application/octet-stream");
                response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment;fileName=" + filename);
                try {
                    os = response.getOutputStream();
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    while((len=bis.read(buffer))>0){
                        os.write(buffer,0,len);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("----------file download----->" + filename);
                try {
                    bis.close();
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @PostMapping("/api/delete")
    public Result deleteFile(@RequestBody Map<String,String> map) {
        try{
            String filename=map.get("filename");
            String filePath = "D:/vue/wrp-vue/src/"+"11111"+"/File/";
            System.out.println(filename);
            File file = new File(filePath + filename);
            if (file.exists()) { //判断文件父目录是否存在
                try {
                    if(userFileService.deleteFile(filename,3)!=0) {
                        file.delete();
                        return ResultFactory.buildSuccessResult("删除成功");
                    }
                   System.out.println("----------file download----->" + filename);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return ResultFactory.buildFailResult("删除失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("删除失败");
        }
        return null;
    }
}

