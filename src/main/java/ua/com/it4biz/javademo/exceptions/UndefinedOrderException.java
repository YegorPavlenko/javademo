package ua.com.it4biz.javademo.exceptions;

public class UndefinedOrderException extends RuntimeException{
  public UndefinedOrderException(String messsage) {
    super(messsage);
  }
}
