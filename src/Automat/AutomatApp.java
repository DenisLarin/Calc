package Automat;

import Parser.Element;

import java.util.*;

public class AutomatApp {
    private AutomatNode startNode;
    private List<AutomatNode> automatNodeList;
    private int positionIndex = 0;
    private Stack<AutomatNode> recursionSteck = new Stack(); //стек рекурсии

    public AutomatApp() {
        automatNodeList = new LinkedList<>();
        AutomatNode automatNode = new AutomatNode(false);
        startNode = automatNode;
        automatNodeList.add(startNode);
    }

    public void addStep(int fromGo, int toGo, String whatDo, boolean isFinalNode) {
        if (automatNodeList.size() - 1 < fromGo) {
            System.out.println("В списке узлов нет узла из которого надо идти");
            return;
        }
        if (isFinalNode || fromGo == toGo) {
            addFinalNode(fromGo, toGo, whatDo);
        } else {
            addNotFinalNode(fromGo, toGo, whatDo);
        }
    }

    public boolean canGo(Element element){
        if(positionIndex >automatNodeList.size()-1)
            return false;
        AutomatNode currentNode = automatNodeList.get(positionIndex);
        if (element.getType().equals("closebracket") && currentNode.isFinal() && !recursionSteck.isEmpty()){
            AutomatNode temp = recursionSteck.pop();
            currentNode = temp.getNextNodesMap().get("correctSTR");
            positionIndex = automatNodeList.indexOf(currentNode);
        }
        Map<String,AutomatNode> currentNodeMap = getMap(currentNode);
        if(currentNodeMap.isEmpty())
            return false;
        for(Map.Entry<String,AutomatNode> pair: currentNodeMap.entrySet()){
            if(element.getType().equals(pair.getKey())){
                if(element.getType().equals("openbracket")){
                    recursionSteck.push(currentNodeMap.get("openbracket"));
                    positionIndex = 0;
                    return true;
                }
                positionIndex = automatNodeList.indexOf(pair.getValue());
                return true;
            }
        }
        return false;
    }

    public AutomatNode getStartNode() {
        return startNode;
    }

    public void setStartNode(AutomatNode startNode) {
        this.startNode = startNode;
    }

    public List<AutomatNode> getAutomatNodeList() {
        return automatNodeList;
    }

    public void setAutomatNodeList(List<AutomatNode> automatNodeList) {
        this.automatNodeList = automatNodeList;
    }

    public int getPositionIndex() {
        return positionIndex;
    }

    public void setPositionIndex(int positionIndex) {
        this.positionIndex = positionIndex;
    }

    public Stack<AutomatNode> getRecursionSteck() {
        return recursionSteck;
    }

    public void setRecursionSteck(Stack<AutomatNode> recursionSteck) {
        this.recursionSteck = recursionSteck;
    }

    private void addNotFinalNode(int fromGo, int toGo, String whatDo) {
        AutomatNode fromGoNode = automatNodeList.get(fromGo);
        AutomatNode toGoNode;
        if (automatNodeList.size() - 1 < toGo) {
            automatNodeList = prepareList(toGo);
            toGoNode = new AutomatNode(false);
            mapCreate(whatDo, fromGoNode, toGoNode);
            automatNodeList.set(toGo, toGoNode);
        }
        //если список расширять не надо,значит новый узел не нужен
        else {
            toGoNode = automatNodeList.get(toGo);
            if (toGoNode == null) {
                toGoNode = new AutomatNode(false);
                automatNodeList.set(toGo, toGoNode);
            }
            mapCreate(whatDo, fromGoNode, toGoNode);
        }

    }

    private void addFinalNode(int fromGo, int toGo, String whatDo) {
        //узел из которого идем
        AutomatNode fromGoNode = automatNodeList.get(fromGo);
        //проверяем равны ли у нас fromGo и toGo
        if (fromGo == toGo) {
            //fromGoNode -- финальный и уже создан
            mapCreate(whatDo, fromGoNode, fromGoNode);
        } else {
            //если список надо расширить
            AutomatNode toGoNode;
            if (automatNodeList.size() - 1 < toGo) {
                automatNodeList = prepareList(toGo);
                toGoNode = new AutomatNode(true);
                mapCreate(whatDo, fromGoNode, toGoNode);
                automatNodeList.set(toGo, toGoNode);
            }
            //если список расширять не надо,значит новый узел не нужен
            else {
                toGoNode = automatNodeList.get(toGo);
                if (toGoNode == null) {
                    toGoNode = new AutomatNode(true);
                    automatNodeList.set(toGo, toGoNode);
                }
                mapCreate(whatDo, fromGoNode, toGoNode);
            }
        }
    }

    private void mapCreate(String whatDo, AutomatNode fromGoNode, AutomatNode toGoNode) {
        Map<String, AutomatNode> automatNodeMap = getMap(fromGoNode);
        automatNodeMap.put(whatDo, toGoNode);
        fromGoNode.setNextNodesMap(automatNodeMap);
    }

    private List<AutomatNode> prepareList(int toGo) {
        List<AutomatNode> temp = this.automatNodeList;
        int size = toGo - automatNodeList.size();
        while (size != -1) {
            size--;
            temp.add(null);
        }
        return temp;
    }

    private Map<String, AutomatNode> getMap(AutomatNode node) {
        if (node.getNextNodesMap() != null) {
            return node.getNextNodesMap();
        }
        return new HashMap<>();
    }


    public void printAutomat() {
        for (AutomatNode aN : automatNodeList) {
            aN.ptintString();
        }
    }
}
