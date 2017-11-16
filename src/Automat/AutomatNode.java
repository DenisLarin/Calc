package Automat;

import java.util.Map;

public class AutomatNode {
    private boolean isFinal;
    private Map<String,AutomatNode> nextNodesMap;

    public AutomatNode(boolean isFinal){
        this.isFinal = isFinal;
    }

    public AutomatNode(Map<String,AutomatNode> nexts){
        this.nextNodesMap = nexts;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public Map<String, AutomatNode> getNextNodesMap() {
        return nextNodesMap;
    }

    public void setNextNodesMap(Map<String, AutomatNode> nextNodesMap) {
        this.nextNodesMap = nextNodesMap;
    }

    public void ptintString() {
        System.out.println("____________________________________________________________________");
        System.out.println("финальный ли: " + this.isFinal);
        for (Map.Entry<String, AutomatNode> pair:this.nextNodesMap.entrySet()) {
            System.out.print("Ключ: " + pair.getKey() + "; ");
            Map<String,AutomatNode> map = pair.getValue().nextNodesMap;
            System.out.println("Значение: " + pair.getValue());
        }
        System.out.println("____________________________________________________________________");
    }
}
