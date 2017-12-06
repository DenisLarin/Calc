import Automat.AutomatApp;
import Parser.Element;
import Parser.ParserApp;
import Tree.TreeApp;
import Tree.TreeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        String str = new String();
        Scanner scanner = new Scanner(System.in);
        //System.out.println("введите выражение");
        //str = scanner.nextLine();
        //str = "((((((((1+(2+(3+(4+(5+(6+(7+(8+(9)))))))))";
        // str = "(102-31)/82*54";
        //str = "((10-10)*(32-1))/45";
        str = "(1+(2+(3+(4+(5+(6+(7+(8+(9)))))))))";
        //str = "(1+2+(3)+(6)+4)";
        List<Element> elementList = new LinkedList<>(); //список элементов
        if (ParserApp.parser(str,elementList)){
            System.out.println("Выражение: '" + str  + "' записано корректно, проверка на соответствие автомату...");
            //создаем узлы автомата
            AutomatApp automatApp = addNodes(elementList);
            if(checked(automatApp,elementList)){
                System.out.println("Выражение: '" + str  + "' проверено успешно, продолжаем вычисление...");
                //формирование дерева
                TreeApp<Element> treeApp = new TreeApp<Element>(elementList,true);
                //решение
                System.out.println("\n\n");
                System.out.println(treeApp.calc());


            }
            else
                System.out.println("Выражение: '" + str  + "' проверено неуспешно, программа будет завершена...");
        }
        else
            System.out.println("Выражение: '" + str  + "' записано некорректно, программа будет завершена...");
    }

    private static boolean checked(AutomatApp automatApp, List<Element> elementList) {
        System.out.println("\n\n");
        System.out.println("Проверка автоматом:");
        int checkElements = elementList.size();
        for (Element e:elementList) {
            if(!automatApp.canGo(e)) {
                    System.out.println(e.toString() + " проверен неуспешно");
                    break;
            }
            else{
                System.out.println(e.toString() + " проверен успешно");
            }
            checkElements--;
        }
        if(checkElements == 0 && automatApp.getRecursionSteck().isEmpty()){
            return true;
        }
        if(!automatApp.getRecursionSteck().isEmpty()){
            System.out.println("Количество скобок не совпадает");
        }
        return false;
    }

    private static AutomatApp addNodes(List<Element> elementList) {
        AutomatApp automatApp = new AutomatApp();
        String[] currentOperations = {"number", "operation", "openbracket", "closebracket"};
        automatApp.addStep(0, 1, currentOperations[0], true);
        automatApp.addStep(0, 3, currentOperations[2],false);
        automatApp.addStep(1, 2, currentOperations[1],false);
        automatApp.addStep(2, 1, currentOperations[0], true);
        automatApp.addStep(3, 4, "correctSTR",false);
        automatApp.addStep(2, 3, currentOperations[2],false);
        automatApp.addStep(4, 1, currentOperations[3], true);
//        automatApp.printAutomat();
        return automatApp;
    }
}
