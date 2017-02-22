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
     * @param datas 用户数据，数据model需要继承{@link RvTree}
     * @param defaultExpandLevel 默认展开层级
     * @return node数组
     */
    public static <T extends RvTree> List<Node> getSortedNodes(List<T> datas, int defaultExpandLevel) {
        List<Node> result = new ArrayList<>();
        // 将用户数据转化为List<Node>以及设置Node间关系(父子关系)
        List<Node> nodes = convertData2Node(datas);
        // 拿到根节点
        List<Node> rootNodes = getRootNodes(nodes);

        // 排序
        for (Node node : rootNodes) {
            addNode(result, node, defaultExpandLevel, 1);
        }
        return result;
    }

    /**
     * 过滤出所有可见的Node
     * @param nodes
     * @return
     */
    public static List<Node> filterVisibleNode(List<Node> nodes) {
        List<Node> result = new ArrayList<>();

        for (Node node : nodes) {
            // 如果为根节点，或者上层目录为展开状态
            if (node.isRoot() || node.isParentExpand()) {
                result.add(node);
            }
        }
        return result;
    }

    private static <T extends RvTree> List<Node> convertData2Node(List<T> datas) {
        List<Node> list = new ArrayList<>();
        for (T item : datas) {
            Node node = new Node();
            node.setId(item.getId());
            node.setpId(item.getPid());
            node.setLevel(item.getLevel());
            node.setName(item.getTitle());
            node.setResId(item.getImageResId());
            list.add(node);
        }
        sortNodeList(list);
        return list;
    }

    private static void sortNodeList(List<Node> list) {
        for (int i = 0; i < list.size(); i++) {
            Node n = list.get(i);
            for (int j = i + 1; j < list.size(); j++) {
                Node m = list.get(j);

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
    private static List<Node> getRootNodes(List<Node> nodes) {
        List<Node> root = new ArrayList<>();
        for (Node node : nodes) {
            if (node.isRoot())
                root.add(node);
        }
        return root;
    }

    /**
     * 添加某节点以下的所有子节点
     */
    private static void addNode(List<Node> nodes, Node node, int defaultExpandLeval, int currentLevel) {
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
