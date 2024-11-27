package com.example.fruit.bean;

import org.litepal.crud.DataSupport;

public class Comment extends DataSupport {
    private int id;
    private String userAccount; // 用户的账号
    private String content;     // 评论内容
    private long timestamp;     // 评论的时间戳
    private String fruitTitle;  // 被评论的水果标题

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFruitTitle() {
        return fruitTitle;
    }

    public void setFruitTitle(String fruitTitle) {
        this.fruitTitle = fruitTitle;
    }
}

