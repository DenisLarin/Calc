package Parser;

import java.util.Stack;

public class Element {
    private String value;
    private String type;
    private byte operation_priority;

    public Element(String value, String type, byte operation_priority) {
        this.value = value;
        this.type = type;
        this.operation_priority = operation_priority;
    }

    public Element(String value, String type) {
        this.value = value;
        this.type = type;
        //определение приоритета операции
        switch (value){
            case "+":
                this.operation_priority = 0;
                break;
            case "-":
                this.operation_priority = 0;
                break;
            case "*":
                this.operation_priority = 1;
                break;
            case "/":
                this.operation_priority = 1;
                break;
            default:
                this.operation_priority = 2;
                break;
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte getOperation_priority() {
        return operation_priority;
    }

    public void setOperation_priority(byte operation_priority) {
        this.operation_priority = operation_priority;
    }

    @Override
    public String toString() {
        return "ParserApp.Element{" +
                "value='" + value + '\'' +
                ", type='" + type + '\'' +
                ", operation_priority=" + operation_priority +
                '}';
    }
}
