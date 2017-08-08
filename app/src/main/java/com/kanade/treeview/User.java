package com.kanade.treeview;

import com.kanade.treeadapter.RvTree;

public class User implements RvTree {
    private int id;
    private int pid;
    private String title;
    private int resId;

    public User(){}

    public User(int id, int pid, String title) {
        this.id = id;
        this.pid = pid;
        this.title = title;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Override
    public int getImageResId() {
        return R.drawable.ic_detail;
    }
}