package ua.com.it4biz.javademo.model;

import com.github.valfirst.slf4jtest.TestLogger;
import com.github.valfirst.slf4jtest.TestLoggerFactory;
import org.junit.jupiter.api.*;
import ua.com.it4biz.javademo.exceptions.UndefinedOrderException;

import java.lang.reflect.Field;

import static com.github.valfirst.slf4jtest.LoggingEvent.error;
import static com.github.valfirst.slf4jtest.LoggingEvent.info;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderBookAppTest {
  private OrderBook orderBook;
  TestLogger testLogger = TestLoggerFactory.getTestLogger(OrderBook.class);

  @BeforeEach
  void setUp() {
    orderBook = new OrderBook();
  }

  @AfterEach
  void tearDown() {
    testLogger.clear();
    TestLoggerFactory.clear();
  }

  @Nested
  class UpdateAskTests {
    Object asksValue;
    Object bidsValue;
    @BeforeEach
    void setUp() throws Exception {
      // "good" updates
      orderBook.updateAsk(21, 5);
      orderBook.updateAsk(22, 4);
      orderBook.updateAsk(20, 7);

      // check orderBook size via Reflection API
      // it also could be done via to add public get methods for asks and bids
      Field asksField = orderBook.getClass().getDeclaredField("asks");
      Field bidsField = orderBook.getClass().getDeclaredField("bids");
      asksField.setAccessible(true);
      bidsField.setAccessible(true);
      asksValue = asksField.get(orderBook);
      bidsValue = bidsField.get(orderBook);
    }

  // "bad" updates start
    @Test
    void updateAskZeroPrice() throws Exception {
      orderBook.updateAsk(0, 5);

      // check orderBook size via Reflection API
      // it also could be done via to add public get methods for asks and bids
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(0, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(0, orderBook.getSizeByPrice(0));
      assertEquals(7, orderBook.getSizeByPrice(20));
      assertEquals(5, orderBook.getSizeByPrice(21));
      assertEquals(4, orderBook.getSizeByPrice(22));
      // test logging
      assertEquals(asList(info("{} parameter is empty in {} method", "price", "updateAsk")), testLogger.getLoggingEvents());
    }

    @Test
    void updateAskZeroSize() throws Exception {
      orderBook.updateAsk(21, 0);

      // check orderBook size via Reflection API
      // it also could be done via to add public get methods for asks and bids
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(0, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(0, orderBook.getSizeByPrice(0));
      assertEquals(7, orderBook.getSizeByPrice(20));
      assertEquals(5, orderBook.getSizeByPrice(21));
      assertEquals(4, orderBook.getSizeByPrice(22));
      // test logging
      assertEquals(asList(info("{} parameter is empty in {} method", "size", "updateAsk")), testLogger.getLoggingEvents());
    }

    @Test
    void updateAskZeroPriceZeroSize() throws Exception {
      orderBook.updateAsk(0, 0);

      // check orderBook size via Reflection API
      // it also could be done via to add public get methods for asks and bids
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(0, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(0, orderBook.getSizeByPrice(0));
      assertEquals(7, orderBook.getSizeByPrice(20));
      assertEquals(5, orderBook.getSizeByPrice(21));
      assertEquals(4, orderBook.getSizeByPrice(22));
      // test logging
      assertEquals(asList(info("{} parameter is empty in {} method", "price", "updateAsk")), testLogger.getLoggingEvents());
    }

    @Test
    void updateAskNullPrice() throws Exception {
      orderBook.updateAsk(null, 3);

      // check orderBook size via Reflection API
      // it also could be done via to add public get methods for asks and bids
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(0, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(0, orderBook.getSizeByPrice(0));
      assertEquals(7, orderBook.getSizeByPrice(20));
      assertEquals(5, orderBook.getSizeByPrice(21));
      assertEquals(4, orderBook.getSizeByPrice(22));
      // test logging
      assertEquals(asList(info("{} parameter is empty in {} method", "price", "updateAsk")), testLogger.getLoggingEvents());
    }

    @Test
    void updateAskNullSize() throws Exception {
      orderBook.updateAsk(1, null);

      // check orderBook size via Reflection API
      // it also could be done via to add public get methods for asks and bids
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(0, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(0, orderBook.getSizeByPrice(0));
      assertEquals(7, orderBook.getSizeByPrice(20));
      assertEquals(5, orderBook.getSizeByPrice(21));
      assertEquals(4, orderBook.getSizeByPrice(22));
      // test logging
      assertEquals(asList(info("{} parameter is empty in {} method", "size", "updateAsk")), testLogger.getLoggingEvents());
    }

    @Test
    void updateAskNullPriceNullSize() throws Exception {
      orderBook.updateAsk(null, null);

      // check orderBook size via Reflection API
      // it also could be done via to add public get methods for asks and bids
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(0, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(0, orderBook.getSizeByPrice(0));
      assertEquals(7, orderBook.getSizeByPrice(20));
      assertEquals(5, orderBook.getSizeByPrice(21));
      assertEquals(4, orderBook.getSizeByPrice(22));
      // test logging
      assertEquals(asList(info("{} parameter is empty in {} method", "price", "updateAsk")), testLogger.getLoggingEvents());
    }
  // "bad" updates end

    @Test
    void updateAsk() throws Exception {
      // check orderBook size via Reflection API
      // it also could be done via to add public get methods for asks and bids
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(0, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(0, orderBook.getSizeByPrice(0));
      assertEquals(7, orderBook.getSizeByPrice(20));
      assertEquals(5, orderBook.getSizeByPrice(21));
      assertEquals(4, orderBook.getSizeByPrice(22));
    }

    @Test
    void updateAskWithBidPriceLessSize() throws Exception {
      // check when ask price equals best bid price but ask size <
      orderBook.updateBid(2, 12);
      orderBook.updateAsk(2, 9);
      assertEquals(3, orderBook.getSizeByPrice(2));
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(1, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));
    }

    @Test
    void updateAskWithBidPriceEqualSize() throws Exception {
      // check when ask price and size equals best bid price and size
      orderBook.updateBid(2, 12);
      orderBook.updateAsk(2, 9);
      orderBook.updateAsk(2, 3);
      assertEquals(0, orderBook.getSizeByPrice(2));
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(0, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));
    }

    @Test
    void updateAskWithBidPriceGreaterSizeOneBid() throws Exception {
      // check when ask price equals best bid price but ask size > best bid size (only one bid)
      orderBook.updateBid(3, 15);
      orderBook.updateAsk(3, 17);
      assertEquals(2, orderBook.getSizeByPrice(3));
      assertEquals(4, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(0, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));
    }

    @Test
    void updateAskWithBidPriceGreaterSizeMoreThanOneBid() throws Exception {
    // check when ask price less and equals than best bid price but ask size > best bid size (more than one bid)
    orderBook.updateBid(4, 11);
    orderBook.updateBid(3, 15);
    orderBook.updateAsk(3, 17);
    assertEquals(9, orderBook.getSizeByPrice(3));
    assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
    assertEquals(1, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));
    }
  }

  @Nested
  class UpdateBidTests {
    private Object asksValue;
    private Object bidsValue;

    @BeforeEach
    void setUp() throws Exception {
      // "good" updates
      orderBook.updateBid(1, 5);
      orderBook.updateBid(2, 4);
      orderBook.updateBid(3, 7);

      // check orderBook size via Reflection API
      Field asksField = orderBook.getClass().getDeclaredField("asks");
      Field bidsField = orderBook.getClass().getDeclaredField("bids");
      asksField.setAccessible(true);
      bidsField.setAccessible(true);
      asksValue = asksField.get(orderBook);
      bidsValue = bidsField.get(orderBook);
    }

  // "bad" updates start
    @Test
    void updateBidZeroPrice() throws Exception {
      orderBook.updateBid(0, 5);

      // check orderBook size via Reflection API
      assertEquals(0, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(0, orderBook.getSizeByPrice(0));
      assertEquals(5, orderBook.getSizeByPrice(1));
      assertEquals(4, orderBook.getSizeByPrice(2));
      assertEquals(7, orderBook.getSizeByPrice(3));
      // test logging
      assertEquals(asList(info("{} parameter is empty in {} method", "price", "updateBid")), testLogger.getLoggingEvents());
    }

    @Test
    void updateBidZeroSize() throws Exception {
      orderBook.updateBid(1, 0);

      // check orderBook size via Reflection API
      assertEquals(0, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(0, orderBook.getSizeByPrice(0));
      assertEquals(5, orderBook.getSizeByPrice(1));
      assertEquals(4, orderBook.getSizeByPrice(2));
      assertEquals(7, orderBook.getSizeByPrice(3));
      // test logging
      assertEquals(asList(info("{} parameter is empty in {} method", "size", "updateBid")), testLogger.getLoggingEvents());
    }

    @Test
    void updateBidZeroPriceZeroSize() throws Exception {
      orderBook.updateBid(0, 0);

      // check orderBook size via Reflection API
      assertEquals(0, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(0, orderBook.getSizeByPrice(0));
      assertEquals(5, orderBook.getSizeByPrice(1));
      assertEquals(4, orderBook.getSizeByPrice(2));
      assertEquals(7, orderBook.getSizeByPrice(3));
      // test logging
      assertEquals(asList(info("{} parameter is empty in {} method", "price", "updateBid")), testLogger.getLoggingEvents());
    }

    @Test
    void updateBidNullPrice() throws Exception {
      orderBook.updateBid(null, 3);

      // check orderBook size via Reflection API
      assertEquals(0, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(0, orderBook.getSizeByPrice(0));
      assertEquals(5, orderBook.getSizeByPrice(1));
      assertEquals(4, orderBook.getSizeByPrice(2));
      assertEquals(7, orderBook.getSizeByPrice(3));
      // test logging
      assertEquals(asList(info("{} parameter is empty in {} method", "price", "updateBid")), testLogger.getLoggingEvents());
    }

    @Test
    void updateBidNullSize() throws Exception {
      orderBook.updateBid(2, null);

      // check orderBook size via Reflection API
      assertEquals(0, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(0, orderBook.getSizeByPrice(0));
      assertEquals(5, orderBook.getSizeByPrice(1));
      assertEquals(4, orderBook.getSizeByPrice(2));
      assertEquals(7, orderBook.getSizeByPrice(3));
      // test logging
      assertEquals(asList(info("{} parameter is empty in {} method", "size", "updateBid")), testLogger.getLoggingEvents());
    }

    @Test
    void updateBidNullPriceNullSize() throws Exception {
      orderBook.updateBid(null, null);

      // check orderBook size via Reflection API
      assertEquals(0, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(0, orderBook.getSizeByPrice(0));
      assertEquals(5, orderBook.getSizeByPrice(1));
      assertEquals(4, orderBook.getSizeByPrice(2));
      assertEquals(7, orderBook.getSizeByPrice(3));
      // test logging
      assertEquals(asList(info("{} parameter is empty in {} method", "price", "updateBid")), testLogger.getLoggingEvents());
    }
    // "bad" updates end

    @Test
    void updateBid() throws Exception {
      // check orderBook size via Reflection API
      assertEquals(0, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(0, orderBook.getSizeByPrice(0));
      assertEquals(5, orderBook.getSizeByPrice(1));
      assertEquals(4, orderBook.getSizeByPrice(2));
      assertEquals(7, orderBook.getSizeByPrice(3));
    }

    @Test
    @DisplayName("test updateBid method with bid price equals with ask price and updateBid size less than ask")
    void updateBidWithAskPriceLessSize() throws Exception {
      // check when ask price equals best bid price (but less size)
      orderBook.updateAsk(6, 13);
      orderBook.updateBid(6, 10);
      assertEquals(3, orderBook.getSizeByPrice(6));
      assertEquals(1, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));
    }

    @Test
    void updateBidWithAskPriceEqualSize() throws Exception {
      // check when ask price and size equals best bid price and size
      orderBook.updateAsk(6, 13);
      orderBook.updateBid(6, 10);
      orderBook.updateBid(6, 3);
      assertEquals(0, orderBook.getSizeByPrice(6));
      assertEquals(0, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));
    }

    @Test
    void updateBidWithAskPriceGreaterSizeOneBid() throws Exception {
      // check when bid price equals best ask price but bid size > best ask size (only one bid)
      orderBook.updateAsk(6, 25);
      orderBook.updateBid(6, 55);
      assertEquals(30, orderBook.getSizeByPrice(6));
      assertEquals(0, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(4, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));
    }

    @Test
    void updateBidWithAskPriceGreaterSizeMoreThanOneBid() throws Exception {
      // check when bid price less and equals than best bid price but ask size > best bid size (more than one bid)
      orderBook.updateAsk(6, 13);
      orderBook.updateAsk(5, 13);
      orderBook.updateBid(6, 23);
      assertEquals(3, orderBook.getSizeByPrice(6));
      assertEquals(1, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));
    }
  }

  @Test
  void updateBook() throws Exception {
    // "bad" updates
    orderBook.updateAsk(0, 5);
    orderBook.updateAsk(21, 0);
    orderBook.updateAsk(0, 0);
    orderBook.updateAsk(null, 3);
    orderBook.updateAsk(1, null);
    orderBook.updateAsk(null, null);

    orderBook.updateBid(0, 5);
    orderBook.updateBid(1, 0);
    orderBook.updateBid(0, 0);
    orderBook.updateBid(null, 3);
    orderBook.updateBid(2, null);
    orderBook.updateBid(null, null);

    // "good" updates
    orderBook.updateAsk(21, 5);
    orderBook.updateBid(1, 5);
    orderBook.updateAsk(22, 4);
    orderBook.updateBid(2, 4);
    orderBook.updateAsk(20, 7);
    orderBook.updateBid(3, 7);
    orderBook.updateBid(4, 8);

    // check orderBook size via Reflection API
    Field asksField = orderBook.getClass().getDeclaredField("asks");
    Field bidsField = orderBook.getClass().getDeclaredField("bids");
    asksField.setAccessible(true);
    bidsField.setAccessible(true);
    Object asksValue = asksField.get(orderBook);
    Object bidsValue = bidsField.get(orderBook);
    assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
    assertEquals(4, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

    assertEquals(0, orderBook.getSizeByPrice(0));
    assertEquals(7, orderBook.getSizeByPrice(20));
    assertEquals(5, orderBook.getSizeByPrice(21));
    assertEquals(4, orderBook.getSizeByPrice(22));
    assertEquals(5, orderBook.getSizeByPrice(1));
    assertEquals(4, orderBook.getSizeByPrice(2));
    assertEquals(7, orderBook.getSizeByPrice(3));
    assertEquals(8, orderBook.getSizeByPrice(4));
  }

  @Nested
  class MarketOrderTests {
    Object asksValue;
    Object bidsValue;

    @BeforeEach
    void setUp() throws Exception {
      orderBook.updateAsk(21, 5);
      orderBook.updateBid(1, 5);
      orderBook.updateAsk(22, 4);
      orderBook.updateBid(2, 4);
      orderBook.updateAsk(20, 7);
      orderBook.updateBid(3, 9);

      // check orderBook size via Reflection API
      Field asksField = orderBook.getClass().getDeclaredField("asks");
      Field bidsField = orderBook.getClass().getDeclaredField("bids");
      asksField.setAccessible(true);
      bidsField.setAccessible(true);
      asksValue = asksField.get(orderBook);
      bidsValue = bidsField.get(orderBook);
    }

    @Test
    void marketOrderForEmptyBook() throws Exception {
      OrderBook emptyOrderBook = new OrderBook();
      emptyOrderBook.marketOrder(12, OrderBook.MARKET_BUY);

      // check orderBook size via Reflection API
      Object emptyAsksValue;
      Object emptyBidsValue;
      Field emptyAsksField = emptyOrderBook.getClass().getDeclaredField("asks");
      Field emptyBidsField = emptyOrderBook.getClass().getDeclaredField("bids");
      emptyAsksField.setAccessible(true);
      emptyBidsField.setAccessible(true);
      emptyAsksValue = emptyAsksField.get(emptyOrderBook);
      emptyBidsValue = emptyBidsField.get(emptyOrderBook);
      assertEquals(0, emptyAsksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(emptyAsksValue));
      assertEquals(0, emptyBidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(emptyBidsValue));

      assertEquals(0, emptyOrderBook.getSizeByPrice(20));
      assertEquals(0, emptyOrderBook.getSizeByPrice(3));
      assertEquals(0, emptyOrderBook.getSizeByPrice(100));
      assertEquals(0, emptyOrderBook.getSizeByPrice(0));
      assertEquals(0, emptyOrderBook.getSizeByPrice(0));

      assertEquals(asList(error("Somehow current record in orderbook is not valid.")), testLogger.getLoggingEvents());
    }

    @Test
    void marketOrderBuyZeroSize() throws Exception {
      orderBook.marketOrder(0, OrderBook.MARKET_BUY);

      // check orderBook size via Reflection API
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(7, orderBook.getSizeByPrice(20));
      assertEquals(9, orderBook.getSizeByPrice(3));
      assertEquals(0, orderBook.getSizeByPrice(100));

      assertEquals(asList(info("{} parameter is empty in {} method", "orderSize", "marketOrder")), testLogger.getLoggingEvents());
    }

    @Test
    void marketOrderSellZeroSize() throws Exception {
      orderBook.marketOrder(0, OrderBook.MARKET_SELL);

      // check orderBook size via Reflection API
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(7, orderBook.getSizeByPrice(20));
      assertEquals(9, orderBook.getSizeByPrice(3));
      assertEquals(0, orderBook.getSizeByPrice(100));

      assertEquals(asList(info("{} parameter is empty in {} method", "orderSize", "marketOrder")), testLogger.getLoggingEvents());
    }

    @Test
    void marketOrderEmptyType() throws Exception {
      orderBook.marketOrder(1, "");

      // check orderBook size via Reflection API
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(7, orderBook.getSizeByPrice(20));
      assertEquals(9, orderBook.getSizeByPrice(3));
      assertEquals(0, orderBook.getSizeByPrice(100));

      assertEquals(asList(info("{} parameter is empty in {} method", "orderType", "marketOrder")), testLogger.getLoggingEvents());
    }

    @Test
    void marketOrderZeroSizeEmptyType() throws Exception {
      orderBook.marketOrder(0, "");

      // check orderBook size via Reflection API
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(7, orderBook.getSizeByPrice(20));
      assertEquals(9, orderBook.getSizeByPrice(3));
      assertEquals(0, orderBook.getSizeByPrice(100));

      assertEquals(asList(info("{} parameter is empty in {} method", "orderSize", "marketOrder")), testLogger.getLoggingEvents());
    }

    @Test
    void marketOrderBuyNullSize() throws Exception {
      orderBook.marketOrder(null, OrderBook.MARKET_BUY);

      // check orderBook size via Reflection API
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(7, orderBook.getSizeByPrice(20));
      assertEquals(9, orderBook.getSizeByPrice(3));
      assertEquals(0, orderBook.getSizeByPrice(100));

      assertEquals(asList(info("{} parameter is empty in {} method", "orderSize", "marketOrder")), testLogger.getLoggingEvents());
    }

    @Test
    void marketOrderSellNullSize() throws Exception {
      orderBook.marketOrder(null, OrderBook.MARKET_SELL);

      // check orderBook size via Reflection API
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(7, orderBook.getSizeByPrice(20));
      assertEquals(9, orderBook.getSizeByPrice(3));
      assertEquals(0, orderBook.getSizeByPrice(100));

      assertEquals(asList(info("{} parameter is empty in {} method", "orderSize", "marketOrder")), testLogger.getLoggingEvents());
    }

    @Test
    void marketOrderNullType() throws Exception {
      orderBook.marketOrder(2, null);

      // check orderBook size via Reflection API
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(7, orderBook.getSizeByPrice(20));
      assertEquals(9, orderBook.getSizeByPrice(3));
      assertEquals(0, orderBook.getSizeByPrice(100));

      assertEquals(asList(info("{} parameter is empty in {} method", "orderType", "marketOrder")), testLogger.getLoggingEvents());
    }

    @Test
    void marketOrderNullSizeNullType() throws Exception {
      orderBook.marketOrder(null, null);

      // check orderBook size via Reflection API
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(7, orderBook.getSizeByPrice(20));
      assertEquals(9, orderBook.getSizeByPrice(3));
      assertEquals(0, orderBook.getSizeByPrice(100));

      assertEquals(asList(info("{} parameter is empty in {} method", "orderSize", "marketOrder")), testLogger.getLoggingEvents());
    }

    @Test
    void marketOrderSellNullSizeNullType() throws Exception {
      orderBook.marketOrder(null, null);

      // check orderBook size via Reflection API
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(7, orderBook.getSizeByPrice(20));
      assertEquals(9, orderBook.getSizeByPrice(3));
      assertEquals(0, orderBook.getSizeByPrice(100));

      assertEquals(asList(info("{} parameter is empty in {} method", "orderSize", "marketOrder")), testLogger.getLoggingEvents());
    }

    @Test
    void marketBuyOrderLessSize() throws Exception {
      orderBook.marketOrder(1, OrderBook.MARKET_BUY);

      // check orderBook size via Reflection API
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(6, orderBook.getSizeByPrice(20));
      assertEquals(0, orderBook.getSizeByPrice(100));
    }

    @Test
    void marketBuyOrderEqualSize() throws Exception {
      orderBook.marketOrder(7, OrderBook.MARKET_BUY);

      // check orderBook size via Reflection API
      assertEquals(2, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(0, orderBook.getSizeByPrice(20));
      assertEquals(5, orderBook.getSizeByPrice(21));
      assertEquals(4, orderBook.getSizeByPrice(22));
    }

    @Test
    void marketBuyOrderGreaterSize() throws Exception {
      orderBook.marketOrder(8, OrderBook.MARKET_BUY);

      // check orderBook size via Reflection API
      assertEquals(2, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(0, orderBook.getSizeByPrice(20));
      assertEquals(4, orderBook.getSizeByPrice(21));
      assertEquals(4, orderBook.getSizeByPrice(22));
    }
/*
    @Test
    void updateBidWithAskPriceGreaterSizeMoreThanOneBid() throws Exception {
      // check when bid price less and equals than best bid price but ask size > best bid size (more than one bid)
      orderBook.updateAsk(6, 13);
      orderBook.updateAsk(5, 13);
      orderBook.updateBid(6, 23);
      assertEquals(3, orderBook.getSizeByPrice(6));
      assertEquals(1, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));
    }*/

    @Test
    void marketSellOrder() throws Exception {
      orderBook.marketOrder(5, OrderBook.MARKET_SELL);

      // check orderBook size via Reflection API
      assertEquals(3, asksValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(asksValue));
      assertEquals(3, bidsValue.getClass().getDeclaredMethod("size", new Class[]{}).invoke(bidsValue));

      assertEquals(4, orderBook.getSizeByPrice(3));
      assertEquals(0, orderBook.getSizeByPrice(100));
    }

    @Test
    void marketOrderWrongType() {
      Exception exception = assertThrows(UndefinedOrderException.class, () ->
        orderBook.marketOrder(1, "something"));
      assertEquals("Given order type: something is not defined.", exception.getMessage());
    }
  }

  @Test
  void getBestAsk() {
    orderBook.updateAsk(11, 5);
    orderBook.updateAsk(12, 4);
    orderBook.updateAsk(10, 7);
    assertEquals(10, orderBook.getBestAsk().getKey());
    assertEquals(7, orderBook.getBestAsk().getValue());
  }

  @Test
  void getBestBid() {
    orderBook.updateBid(11, 5);
    orderBook.updateBid(12, 4);
    orderBook.updateBid(10, 7);
    assertEquals(12, orderBook.getBestBid().getKey());
    assertEquals(4, orderBook.getBestBid().getValue());
  }

  @Test
  void getSizeByPrice() {
    orderBook.updateAsk(21, 5);
    orderBook.updateBid(11, 5);
    orderBook.updateAsk(22, 4);
    orderBook.updateBid(12, 4);
    orderBook.updateAsk(20, 7);
    orderBook.updateBid(10, 7);
    assertEquals(0, orderBook.getSizeByPrice(null));
    assertEquals(0, orderBook.getSizeByPrice(0));
    assertEquals(7, orderBook.getSizeByPrice(20));
    assertEquals(4, orderBook.getSizeByPrice(12));
    assertEquals(0, orderBook.getSizeByPrice(120));
  }
}