package Tree;

import Parser.Element;

import java.util.List;

public class TreeApp<E extends Element> {
    private TreeNode<E> root;
    private TreeNode<E> leftTree;
    private List<E> elementList;
    private int index;
    public TreeApp() {
        root = null;
        index = 0;
    }

    public TreeApp(List<E> elementList) {
        this.elementList = elementList;
        index = 0;
    }
    public TreeApp(List<E> elementList, boolean isCreateTree) {
        this.elementList = elementList;
        if (isCreateTree)
            createTree(index, leftTree);

        //для обхода через рут
        root = leftTree;
        leftTree = null;

        //postOrder(root);

    }

//    private void postOrder(TreeNode<E> leftChild) {
//        if (root.getLeftChild()!=null)
//            postOrder(root.getLeftChild());
//        if (root.getRightChild()!=null)
//            postOrder(root.getRightChild());
//        System.out.println(leftChild.getData().getValue());
//    }

    public String calc() {
        TreeNode<E> parrent = root;
        int numberOfOperations = operSize();
        System.out.println("В данном выражении "+ numberOfOperations +" действий");
        String operation;
        for (int i = 0; i < numberOfOperations; i++) {
            parrent = root;
            while (true){
                while (!parrent.getLeftChild().getData().getType().equals("number"))
                    parrent = parrent.getLeftChild();
                while (!parrent.getRightChild().getData().getType().equals("number"))
                    parrent = parrent.getRightChild();
                if(parrent.getLeftChild().getData().getType().equals("number") && parrent.getRightChild().getData().getType().equals("number"))
                    break;
            }
            operation = parrent.getData().getValue();
            String leftNumber = parrent.getLeftChild().getData().getValue();
            String rightNumber = parrent.getRightChild().getData().getValue();
            switch (operation){
                case "+":
                    System.out.print("Действие №"+(i+1) + ": "+leftNumber + "+" + rightNumber);
                    updateNode(parrent, Double.parseDouble(leftNumber)+Double.parseDouble(rightNumber));
                   break;
                case "-":
                    System.out.print("Действие №"+(i+1) + ": "+leftNumber + "-" + rightNumber);
                    updateNode(parrent,Double.parseDouble(leftNumber)-Double.parseDouble(rightNumber));
                    break;
                case "*":
                    System.out.print("Действие №"+(i+1)+ ": "+leftNumber + "*" + rightNumber);
                    updateNode(parrent,Double.parseDouble(leftNumber)*Double.parseDouble(rightNumber));
                    break;
                case "/":
                    System.out.print("Действие №"+(i+1) + ": "+leftNumber + "/" + rightNumber);
                    updateNode(parrent,Double.parseDouble(leftNumber)/Double.parseDouble(rightNumber));
                    break;
            }
        }
        return root.getData().getValue();
    }

    private void updateNode(TreeNode<E> parrent, double summ) {
        parrent.setRightChild(null);
        parrent.setLeftChild(null);
        parrent.getData().setType("number");
        parrent.getData().setValue(summ+"");
        System.out.print(""+ " Результат дейсвия: " + summ + "\n");
    }

    private int operSize() {
        int oper = 0;
        for (Element e:elementList) {
            if (e.getType().equals("operation"))
                oper++;
        }
        return oper;
    }

    private TreeNode<E> createTree(int index, TreeNode<E> leftUnderTree) {
        TreeNode<E> rightUnderTree;
        for (;index<this.elementList.size();index++) {
            switch (elementList.get(index).getType().toLowerCase()) {
                case "number":
                    if (leftUnderTree == null)
                        leftUnderTree = new TreeNode<>(elementList.get(index));
                    else {
                        rightUnderTree = new TreeNode<E>(elementList.get(index));
                        if (leftTree.getRightChild() == null) {
                            leftTree.setRightChild(rightUnderTree);
                            leftUnderTree = leftTree;
                        }
                    }
                    break;
                case "operation":
                    //если в корне в левом дереве число то
                    if(leftUnderTree.getData().getType().equals("number")){
                        TreeNode<E> mainNode = new TreeNode<E>(elementList.get(index));
                        mainNode.setLeftChild(leftUnderTree);
                        leftTree = mainNode;
                    }
                    //если приоритет операции в левом поддереве больше чем текущий
                    else if (leftUnderTree.getData().getOperation_priority()>elementList.get(index).getOperation_priority()){
                        return leftUnderTree;
                    }
                    else if(leftUnderTree.getData().getOperation_priority()<elementList.get(index).getOperation_priority() && !elementList.get(index-1).getType().equals("closebracket")){
                        leftUnderTree.setRightChild(createTree(index,leftTree.getRightChild()));
                        leftTree = leftUnderTree;
                    }
                    else {
                        root = new TreeNode<E>(elementList.get(index));
                        root.setLeftChild(leftTree);
                    }
                    break;
                case "openbracket":
                    TreeNode<E> leftTemp = leftTree;
                    if (leftUnderTree == null){
                        leftUnderTree = createTree(index+1,null);
                        leftTree = leftUnderTree;
                    }
                    else {
                        rightUnderTree = createTree(index + 1, null);
                        leftTemp.setRightChild(rightUnderTree);
                        leftTree = leftTemp;
                        leftTemp = null;
                    }
                    leftUnderTree = leftTree;
                    index = this.index;
                    break;
                case "closebracket":
                    this.index = index;
                    return leftTree;
            }
            if (root != null) {
                leftTree = root;
                root = null;
            }
        }
        return leftUnderTree;
    }

    @Override
    public String toString() {
        return "TreeApp{" +
                "root=" + root +
                '}';
    }
}
