package com.kanade.treeadapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 树状列表的工具类
 * Created by kanade on 2016/11/15.
 */

public class TreeHelper {
    /**
     * 传入要显示的数据，转化为排序后的Node数组
     * @param data 用户数据，数据model需要继承{@link RvTree}
     * @param defaultExpandLevel 默认展开层级
     * @return node数组
     */
    public static <T extends RvTree> List<Node<T>> getSortedNode(List<T> data, int defaultExpandLevel) {
        List<Node<T>> result = new ArrayList<>();
        // 将用户数据转化为List<Node>以及设置Node间关系(父子关系)
        List<Node<T>> nodes = convertData2Node(data);
        // 拿到根节点
        List<Node<T>> rootNodes = getRootNodes(nodes);

        // 排序
        for (Node<T> node : rootNodes) {
            addNode(result, node, defaultExpandLevel, 1);
        }
        return result;
    }

    /**
     * 过滤出所有可见的Node
     */
    public static <T extends RvTree>List<Node<T>> filterVisibleNode(List<Node<T>> nodes) {
        List<Node<T>> result = new ArrayList<>();

        for (Node<T> node : nodes) {
            // 如果为根节点，或者上层目录为展开状态
            if (node.isRoot() || node.isParentExpand()) {
                result.add(node);
            }
        }
        return result;
    }

    private static <T extends RvTree> List<Node<T>> convertData2Node(List<T> data) {
        List<Node<T>> list = new ArrayList<>();
        for (T item : data) {
            Node<T> node = new Node<>();
            node.setId(item.getNid());
            node.setpId(item.getPid());
            node.setName(item.getTitle());
            node.setResId(item.getImageResId());
            node.setItem(item);
            list.add(node);
        }
        linkNodes(list);
        return list;
    }

    /**
     * 将子节点挂到相应的父节点之下
     */
    private static <T extends RvTree> void linkNodes(List<Node<T>> list) {
        for (int i = 0; i < list.size(); i++) {
            Node<T> n = list.get(i);
            for (int j = i + 1; j < list.size(); j++) {
                Node<T> m = list.get(j);

                if (n.getpId() == m.getId()) {
                    m.getChildren().add(n);
                    n.setParent(m);
                    continue;
                }

                if (m.getpId() == n.getId()) {
                    n.getChildren().add(m);
                    m.setParent(n);
                }
            }
        }
    }

    // 获取根节点
    private static <T extends RvTree> List<Node<T>> getRootNodes(List<Node<T>> nodes) {
        List<Node<T>> root = new ArrayList<>();
        for (Node<T> node : nodes) {
            if (node.isRoot()) {
                root.add(node);
            }
        }
        return root;
    }

    /**
     * 添加某节点以下的所有子节点
     */
    private static <T extends RvTree> void addNode(List<Node<T>> nodes, Node<T> node, int defaultExpandLeval, int currentLevel) {
        nodes.add(node);
        if (defaultExpandLeval == currentLevel) {
            node.setExpand(true);
        }

        if (node.isLeaf()) {
            return;
        }

        for (int i = 0; i < node.getChildren().size(); i++) {
            addNode(nodes, node.getChildren().get(i), defaultExpandLeval, currentLevel + 1);
        }
    }
}
