package Tree;

import Parser.Element;
import jdk.nashorn.api.tree.Tree;

import java.util.List;

public class TreeNode <E> {
    private TreeNode<E> leftChild;
    private TreeNode<E> rightChild;
    private E data;

    public TreeNode(){
        leftChild = null;
        rightChild =  null;
        data = null;
    }
    public TreeNode(E data){
        this.data = data;
    }
    public  TreeNode(E data, TreeNode<E> leftChild, TreeNode<E> rightChild){
        this.data = data;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public TreeNode<E> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(TreeNode<E> leftChild) {
        this.leftChild = leftChild;
    }

    public TreeNode<E> getRightChild() {
        return rightChild;
    }

    public void setRightChild(TreeNode<E> rightChild) {
        this.rightChild = rightChild;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "leftChild=" + leftChild +
                ", rightChild=" + rightChild +
                ", data=" + data +
                '}';
    }
}
