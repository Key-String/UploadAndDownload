package com.wrp.save.dao;

import com.wrp.save.entity.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface UserFileDao extends JpaRepository<UserFile,Integer> {
    public List<UserFile> findByUserid(String userid);
    public  UserFile getByUseridAndName(String userid, String name);
    public int deleteUserFileByNameAndAndUserid(String filename, Integer userid);
}
