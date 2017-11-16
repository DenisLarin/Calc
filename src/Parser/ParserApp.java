package Parser;

import Exceptions.SpaceErrorException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserApp {
    public static boolean parser(String str, List<Element> elementList){
        System.out.println("\n\n");
        System.out.println("Парсинг строки:");
        Pattern checkerExternalSpaces = Pattern.compile("[\"\\d\"]+[ ]+[\"\\d\"]*");
        Matcher matcherExternalSpaces = checkerExternalSpaces.matcher(str);
        //если есть пробелы то выкидываем false
        if (matcherExternalSpaces.find()){
            throw new SpaceErrorException(str);
        }

        str = str.replaceAll(" ","");
        //если число начинается с -
        if (str.charAt(0) == '-')
            str = str.replaceFirst("-","0-");
        str = str.replaceAll("\\(-","(0-");

        while (true){
            //начинается с числа
            Pattern patternNumber = Pattern.compile("^[\"\\d\"]+");
            Matcher matcherNumber = patternNumber.matcher(str);
            //начинается с действия
            Pattern patternOperation = Pattern.compile("^[[+]|[-]|[*]|[/]]");
            Matcher matcherOperation = patternOperation.matcher(str);
            //начинается с открывающейся скобки
            Pattern patternOpenBracket = Pattern.compile("^[(]");
            Matcher matcherOpenBracket = patternOpenBracket.matcher(str);
            //начинается с закрывающейся скобки
            Pattern patternCloseBracket = Pattern.compile("^[)]");
            Matcher matcherCloseBracket = patternCloseBracket.matcher(str);
            //есть совпадение
            if (matcherNumber.find()){
                createObjAndAddToList(elementList, str.substring(matcherNumber.start(),matcherNumber.end()), "number");
                str = matcherNumber.replaceFirst("");
            }
            else if (matcherOperation.find()){
                createObjAndAddToList(elementList,str.substring(matcherOperation.start(),matcherOperation.end()),"operation");
                str = matcherOperation.replaceFirst("");
            }
            else if (matcherOpenBracket.find()){
                createObjAndAddToList(elementList,str.substring(matcherOpenBracket.start(),matcherOpenBracket.end()),"openbracket");
                str = matcherOpenBracket.replaceFirst("");
            }
            else if (matcherCloseBracket.find()){
                createObjAndAddToList(elementList,str.substring(matcherCloseBracket.start(),matcherCloseBracket.end()),"closebracket");
                str = matcherCloseBracket.replaceFirst("");
            }
            else {
                for (Element element:elementList) {
                    System.out.println(element.toString());
                }
                return true;
            }
        }
    }

    private static void createObjAndAddToList(List<Element> elementList, String substring, String type) {
        type = type.toLowerCase();
        Element element = new Element(substring, type);
        elementList.add(element);
    }
}
