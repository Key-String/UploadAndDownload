package com.wrp.save.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "userfile")
@JsonIgnoreProperties({"handle","hibernateLazyInitializer"})
public class UserFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    int userid;
    String name;
    String url;
    Date date;
    float lenght;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUserid() {
        return userid;
    }

    public String getUrl() {
        return url;
    }
    public Date getDate(){
        return date;
    }

    public float getLenght() {
        return lenght;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLenght(float lenght) {
        this.lenght = lenght;
    }
}
