package com.wrp.save.service;

import com.wrp.save.dao.UserFileDao;
import com.wrp.save.entity.UserFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserFileService {
    @Autowired
    UserFileDao userFileDao;
    public List<UserFile> findAll(){
        return userFileDao.findAll();
    }
    public List<UserFile> findByUser(String userid){
        return userFileDao.findByUserid(userid);
    }
    public  UserFile getByUseridAndName(String userid,String name){
        return userFileDao.getByUseridAndName(userid,name);
    }
    public void Save(UserFile userFile){
        System.out.println(userFile.getUserid());
        System.out.println(userFile.getUrl());
        System.out.println(userFile.getName());
        System.out.println(userFile.getDate());
        System.out.println(userFile.getLenght());
        userFileDao.save(userFile);
    }
    @Transactional
    public int deleteFile(String filename,int userid){
        return userFileDao.deleteUserFileByNameAndAndUserid(filename, userid);
    }
}
