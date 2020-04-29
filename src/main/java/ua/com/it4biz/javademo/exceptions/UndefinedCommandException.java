package ua.com.it4biz.javademo.exceptions;

public class UndefinedCommandException extends RuntimeException{
  public UndefinedCommandException(String messsage) {
    super(messsage);
  }
}
