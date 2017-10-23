package com.kanade.treeview;

import com.kanade.treeadapter.RvTree;

public class User implements RvTree {
    private long id;
    private long pid;
    private String title;
    private int resId;

    public User(){}

    public User(long id, long pid, String title) {
        this.id = id;
        this.pid = pid;
        this.title = title;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
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