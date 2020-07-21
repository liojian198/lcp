package com.bin.algorithm;

import com.bin.node.TreeNode;
import com.bin.tuple.Tuple;
import org.jctools.maps.NonBlockingHashMapLong;
import java.util.LinkedList;
import java.util.List;


/**
 * 在二叉树中找到两个节点的最近公共祖先：给定一棵二叉树以及这棵树上的两个节点 o1 和 o2，请找到 o1 和 o2 的最近公共祖先节点。
 *
 * 输入描述:
 * 第一行输入两个整数 n 和 root，n 表示二叉树的总节点个数，root 表示二叉树的根节点。
 *
 * 以下 n 行每行三个整数 fa，lch，rch，表示 fa 的左儿子为 lch，右儿子为 rch。(如果 lch 为 0 则表示 fa 没有左儿子，rch同理)
 *
 * 输出描述:
 *
 * 输出一个整数表示答案。
 *
 * 示例1
 * 输入
 * 8 1
 * 1 2 3
 * 2 4 5
 * 4 0 0
 * 5 0 0
 * 3 6 7
 * 6 0 0
 * 7 8 0
 * 8 0 0
 * 4 5
 * 输出
 * 2
 */
public class MapAlgorithm {
    //比较low的写法，项目结构没有整理
    //JC-tools 这里使用下，虽然算法不要求并发
    private static NonBlockingHashMapLong<TreeNode> mapLong = new NonBlockingHashMapLong();

    public static void main(String[] args) {

        int n = 8;
        int root = 1;

        List<Tuple> list = new LinkedList<Tuple>();
        list.add(Tuple.def("fa","lch","rch").val(1L,2L,3L));
        list.add(Tuple.def("fa","lch","rch").val(2L,4L,5L));
        list.add(Tuple.def("fa","lch","rch").val(4L,0L,0L));
        list.add(Tuple.def("fa","lch","rch").val(5L,0L,0L));
        list.add(Tuple.def("fa","lch","rch").val(3L,6L,7L));
        list.add(Tuple.def("fa","lch","rch").val(6L,0L,0L));
        list.add(Tuple.def("fa","lch","rch").val(7L,8L,0L));
        list.add(Tuple.def("fa","lch","rch").val(8L,0L,0L));

        //1. 构造树结构
        for (int i = 0; i< n; i ++) {
            Tuple temp = list.get(i);
            TreeNode nodeParent = mapLong.get((Long) temp.get("fa"));
            TreeNode nodeLeft = mapLong.get((Long) temp.get("lch"));
            TreeNode nodeRight = mapLong.get((Long) temp.get("rch"));

            if(null == nodeLeft) {
                nodeLeft = createAndSetMap("lch", temp);
            }
            if(null == nodeRight) {
                nodeRight =createAndSetMap("rch", temp);
            }
            if(null == nodeParent) {
                nodeParent =createAndSetMap("fa", temp);
            }

            nodeLeft.setParent(nodeParent);
            nodeRight.setParent(nodeParent);

            nodeParent.setlChild(nodeLeft);
            nodeParent.setrChild(nodeRight);

        }

        //2. 查找
        System.out.println(findLCN(4L,5L));
        System.out.println(findLCN(4L,8L));
        System.out.println(findLCN(8L,6L));
        System.out.println(findLCN(12L,11L));

    }

    public static TreeNode createAndSetMap(String nodeName, Tuple temp) {
        TreeNode node = new TreeNode();
        node.setValue((Long) temp.get(nodeName));
        mapLong.put((Long) temp.get(nodeName), node);
        return node;
    }

    public static Long findLCN(Long o1, Long o2) {
        TreeNode node1 = mapLong.get(o1);
        TreeNode node2 = mapLong.get(o2);
        if (null == node1 || null == node2) {
            return null;
        }
        if (null == node1.getParent() || null == node2.getParent() || node1.getParent().getValue() == 9999999999L || node2.getParent().getValue() == 9999999999L) {
            return 9999999999L;
        }

        TreeNode temp = node2;

        while(null != node1.getParent() && node1.getParent().getValue() != 9999999999L) {

            while (null != temp.getParent() && temp.getParent().getValue() != 9999999999L) {
                if(temp.getParent().getValue().compareTo(node1.getParent().getValue()) == 0) {
                    return temp.getParent().getValue();
                } else {
                    temp = temp.getParent();
                }
            }
            temp = node2;
            node1 = node1.getParent();
        }
        return null;
    }

}
