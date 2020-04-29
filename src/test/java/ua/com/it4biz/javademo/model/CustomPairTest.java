package ua.com.it4biz.javademo.model;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomPairTest {

  private final Map.Entry<Integer, Integer> entry = new TreeMap.SimpleEntry<>(1, 3);
  private final CustomPair customPair = new CustomPair(entry);

  @Test
  void getKey() {
    assertEquals(1, customPair.getKey());
  }

  @Test
  void getValue() {
    assertEquals(3, customPair.getValue());
  }

  @Test
  void testToString() {
    assertEquals("1,3", customPair.toString());
  }
}