package Exceptions;

public class SpaceErrorException extends RuntimeException{
    public SpaceErrorException(String string) {
        super("Недопустимое количество пробелов в: " + string);
    }
}
