package ua.com.it4biz.javademo.model;

import java.util.Map;

// This is "stub" like class just for task purpose.
// In real app it likely would be some DTO class for some "OrderBookRecord" class.
public final class CustomPair {
  private final Integer key;
  private final Integer value;

  public CustomPair(Map.Entry<Integer, Integer> entry) {
    key = entry.getKey();
    value = entry.getValue();
  }

  public Integer getKey() {
    return key;
  }

  public Integer getValue() {
    return value;
  }

  @Override
  public String toString() {
    return key + "," + value;
  }
}