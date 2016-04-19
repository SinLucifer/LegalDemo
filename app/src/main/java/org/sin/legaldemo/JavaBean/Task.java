package org.sin.legaldemo.JavaBean;

import cn.bmob.v3.BmobObject;

public class Task extends BmobObject{
    private String title;
    private String event_type;
    private String short_content;
    private boolean isBook = false;
    private UserBean task_publisher;
    private UserBean lawyer;

    public UserBean getTask_publisher() {
        return task_publisher;
    }

    public void setTask_publisher(UserBean task_publisher) {
        this.task_publisher = task_publisher;
    }

    public UserBean getLawyer() {
        return lawyer;
    }

    public void setLawyer(UserBean lawyer) {
        this.lawyer = lawyer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getShort_content() {
        return short_content;
    }

    public void setShort_content(String short_content) {
        this.short_content = short_content;
    }

    public boolean isBook() {
        return isBook;
    }

    public void setBook(boolean book) {
        isBook = book;
    }

}
