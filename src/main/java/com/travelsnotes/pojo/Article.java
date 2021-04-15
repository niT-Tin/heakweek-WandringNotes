package com.travelsnotes.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    int articleId;
    String title;
    String content;
    int tag;
    String img;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //返回时间类型
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") //接收时间类型 yyyy-MM-dd HH:mm:ss,
    Date date;
    int userId;

    public Article(String title, String content, int tag, String img, Date date, int userId) {
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.img = img;
        this.date = date;
        this.userId = userId;
    }
}
