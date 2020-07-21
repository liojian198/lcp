package com.bin.node;

public class TreeNode {
    private TreeNode parent;
    private TreeNode lChild;
    private TreeNode rChild;
    private Long value;

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode getlChild() {
        return lChild;
    }

    public void setlChild(TreeNode lChild) {
        this.lChild = lChild;
    }

    public TreeNode getrChild() {
        return rChild;
    }

    public void setrChild(TreeNode rChild) {
        this.rChild = rChild;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
