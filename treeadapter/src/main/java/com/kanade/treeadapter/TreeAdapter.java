package com.kanade.treeadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * adapter
 * Created by kanade on 2016/11/15.
 */

public class TreeAdapter<T extends RvTree> extends RecyclerView.Adapter<TreeAdapter.TreeViewHolder>{
    private static final int PADDING = 30;
    private Context mContext;
    private List<Node> mNodes;
    private TreeItemClickListener mListener;

    /**
     * 要在{@link #setNodes(List)}之后才会刷新数据
     * @param context
     */
    public TreeAdapter(Context context) {
        mContext = context;
    }

    public TreeAdapter(Context context, List<T> datas) {
        mContext = context;
        setNodes(datas);
    }

    public void setListener(TreeItemClickListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 需要传入数据才会刷新treeview
     * @param datas
     */
    public void setNodes(List<T> datas) {
        List<Node> allNodes = TreeHelper.getSortedNodes(datas, 0);
        mNodes = TreeHelper.filterVisibleNode(allNodes);
    }

    @Override
    public TreeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.rv_item_tree, parent, false);
        return new TreeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TreeAdapter.TreeViewHolder holder, int position) {
        Node node = mNodes.get(position);
//        holder.itemView.setPadding(node.getLevel() * PADDING, 3, 3, 3);
        holder.setControl(node);
    }

    @Override
    public int getItemCount() {
        return mNodes.size();
    }

    class TreeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView icon;
        private TextView title;
        private ImageView detail;
        private Node node;

        private TreeViewHolder(View itemView) {
            super(itemView);

            icon = (ImageView) itemView.findViewById(R.id.rv_item_tree_icon);
            title = (TextView) itemView.findViewById(R.id.rv_item_tree_title);
            detail = (ImageView) itemView.findViewById(R.id.tv_item_tree_detail);

            itemView.setOnClickListener(this);
            detail.setOnClickListener(this);
            itemView.setTag(System.currentTimeMillis());
        }

        private void setControl(Node node) {
            this.node = node;
            title.setText(node.getName());
            title.setPadding(node.getLevel() * PADDING, 3, 3, 3);
            detail.setImageResource(node.getResId());
            if (node.isLeaf()) {
                icon.setImageResource(0);
                return;
            }

            int rotateDegree = node.isExpand() ? 90 : 0;
            icon.setRotation(0);
            icon.setRotation(rotateDegree);
            icon.setImageResource(R.drawable.ic_node_toggle);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.tv_item_tree_detail) {
                if (mListener != null) {
                    mListener.OnClick(node);
                }
                return;
            }

            if (node != null && !node.isLeaf()) {
                long lastClickTime = (long) itemView.getTag();
                // 避免过快点击
                if (System.currentTimeMillis() - lastClickTime < 200) {
                    return;
                }

                itemView.setTag(System.currentTimeMillis());
                int rotateDegree = node.isExpand() ? -90 : 90;
                icon.animate()
                        .setDuration(100)
                        .rotationBy(rotateDegree)
                        .start();

                boolean isExpand = node.isExpand();
                node.setExpand(!isExpand);
                if (!isExpand) {
                    notifyItemRangeInserted(getLayoutPosition() + 1, addChildNodes(node, getLayoutPosition() + 1));
                } else {
                    notifyItemRangeRemoved(getLayoutPosition() + 1, removeChildNodes(node));
                }
            }
        }

        private int addChildNodes(Node n, int startIndex) {
            List<Node> childList = n.getChildren();
            int addChildCount = 0;
            for (Node node : childList) {
                mNodes.add(startIndex + addChildCount++, node);
                // 递归展开其下要展开的子元素
                if (node.isExpand()) {
                    addChildCount += addChildNodes(node, startIndex + addChildCount);
                }
            }
            return addChildCount;
        }

        // 折叠父节点时删除其下所有子元素
        private int removeChildNodes(Node n) {
            if (n.isLeaf()) {
                return 0;
            }

            List<Node> childList = n.getChildren();
            int removeChildCount = childList.size();
            mNodes.removeAll(childList);
            for (Node node : childList) {
                if (node.isExpand()) {
                    removeChildCount += removeChildNodes(node);
                }
            }
            return removeChildCount;
        }
    }
}
