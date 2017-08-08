package com.kanade.treeadapter;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

    private int id;
    /**
     * 根节点pId为0
     */
    private int pId = 0;

    private String name;

    private T item;

    /**
     * 是否展开
     */
    private boolean isExpand = false;

    private int icon;

    /**
     * 下一级的子Node
     */
    private List<Node<T>> children = new ArrayList<>();

    /**
     * 父Node
     */
    private Node parent;

    private int resId;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Node<T>> children) {
        this.children = children;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    /**
     * 是否为根节点
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断父节点是否展开
     */
    public boolean isParentExpand() {
        return parent != null && parent.isExpand();
    }

    /**
     * 是否是叶子界点
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }

    /**
     * 获取level
     */
    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

    /**
     * 设置展开
     */
    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
    }
}
