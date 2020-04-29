package ua.com.it4biz.javademo.executors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputDefaultVocabularyTest {
  @Test
  void delimiterTest() {
    assertEquals(",", InputDefaultVocabulary.DELIMITER);
  }

  @Test
  void commandTypeSymbolTest() {
    assertEquals("o", InputDefaultVocabulary.ORDER);
    assertEquals("q", InputDefaultVocabulary.QUERY);
    assertEquals("u", InputDefaultVocabulary.UPDATE);
  }

  @Test
  void orderCommandTypeTest() {
    assertEquals("buy", InputDefaultVocabulary.ORDER_BUY);
    assertEquals("sell", InputDefaultVocabulary.ORDER_SELL);
  }

  @Test
  void queryCommandTypeTest() {
    assertEquals("best_ask", InputDefaultVocabulary.QUERY_BEST_ASK);
    assertEquals("best_bid", InputDefaultVocabulary.QUERY_BEST_BID);
    assertEquals("size", InputDefaultVocabulary.QUERY_SIZE);
  }

  @Test
  void updateCommandTypeTest() {
    assertEquals("ask", InputDefaultVocabulary.UPDATE_ASK);
    assertEquals("bid", InputDefaultVocabulary.UPDATE_BID);
  }

  @Test
  void commandTypePositionTest() {
    assertEquals(0, InputDefaultVocabulary.COMMAND_TYPE_POSITION);
  }

  @Test
  void queryCommandPositionTest() {
    assertEquals(1, InputDefaultVocabulary.QUERY_COMMAND_POSITION);
  }

  @Test
  void orderCommandPositionTest() {
    assertEquals(1, InputDefaultVocabulary.ORDER_COMMAND_POSITION);
  }

  @Test
  void queryCommandSizeByPricePositionTest() {
    assertEquals(2, InputDefaultVocabulary.QUERY_SIZE_BY_PRICE_POSITION);
  }

  @Test
  void orderCommandBuySizePositionTest() {
    assertEquals(2, InputDefaultVocabulary.ORDER_BUY_SIZE_POSITION);
  }

  @Test
  void orderCommandSellSizePositionTest() {
    assertEquals(2, InputDefaultVocabulary.ORDER_SELL_SIZE_POSITION);
  }

  @Test
  void updateCommandPositionTest() {
    assertEquals(3, InputDefaultVocabulary.UPDATE_COMMAND_POSITION);
  }

  @Test
  void updateCommandPricePositionTest() {
    assertEquals(1, InputDefaultVocabulary.UPDATE_PRICE_POSITION);
  }

  @Test
  void updateCommandSizePositionTest() {
    assertEquals(2, InputDefaultVocabulary.UPDATE_SIZE_POSITION);
  }
}