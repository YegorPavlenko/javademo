package ua.com.it4biz.javademo.executors;

import com.github.valfirst.slf4jtest.TestLogger;
import com.github.valfirst.slf4jtest.TestLoggerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.it4biz.javademo.exceptions.UndefinedCommandException;
import ua.com.it4biz.javademo.exceptions.UndefinedOrderException;
import ua.com.it4biz.javademo.model.OrderBook;

import static com.github.valfirst.slf4jtest.LoggingEvent.error;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;
import static uk.org.lidalia.slf4jtest.LoggingEvent.error;*/

@ExtendWith(MockitoExtension.class)
class ProcessorsTest {
  @Mock
  OrderBook orderBook;
  Processors spiedProcessors;
  TestLogger testLogger = TestLoggerFactory.getTestLogger(Processors.class);

  @BeforeEach
  void setUp() {
    spiedProcessors = Mockito.spy(new Processors(orderBook));
  }

  @AfterEach
  void tearDown() {
    testLogger.clear();
    TestLoggerFactory.clear();
  }

  @Test
  void processOrderBuy() {
    String[] splittedLine = new String[]{InputDefaultVocabulary.ORDER,InputDefaultVocabulary.ORDER_BUY,"13"};
    spiedProcessors.processOrder(splittedLine);
    Mockito.verify(orderBook).marketOrder(13, OrderBook.MARKET_BUY);
  }

  @Test
  void processOrderSell() {
    String[] splittedLine = new String[]{InputDefaultVocabulary.ORDER,InputDefaultVocabulary.ORDER_SELL,"20"};
    spiedProcessors.processOrder(splittedLine);
    Mockito.verify(orderBook).marketOrder(20, OrderBook.MARKET_SELL);
  }

  @Test
  void processNonNumberOrderBuySize() {
    String[] splittedLine = new String[]{InputDefaultVocabulary.ORDER,InputDefaultVocabulary.ORDER_BUY,"aew"};
    // test exception
    Exception exception = assertThrows(NumberFormatException.class, () ->
      spiedProcessors.processOrder(splittedLine));
    assertEquals("For input string: \"aew\"", exception.getMessage());
    // test logging
    assertEquals(asList(error(exception.getClass() + " For input string: \"aew\"")), testLogger.getLoggingEvents());

  }

  @Test
  void processNonNumberOrderSellSize() {
    String[] splittedLine = new String[]{InputDefaultVocabulary.ORDER,InputDefaultVocabulary.ORDER_SELL,"#F"};
    Exception exception = assertThrows(NumberFormatException.class, () ->
      spiedProcessors.processOrder(splittedLine));
    assertEquals("For input string: \"#F\"", exception.getMessage());
    assertEquals(asList(error(exception.getClass() + " For input string: \"#F\"")), testLogger.getLoggingEvents());
  }

  @Test
  void processOrderWithWrongType() {
    String[] splittedLine = new String[]{InputDefaultVocabulary.ORDER,"InputDefaultVocabulary.ORDER_BUY","45"};
    Exception exception = assertThrows(UndefinedOrderException.class, () ->
      spiedProcessors.processOrder(splittedLine));
    assertEquals("Given order type: InputDefaultVocabulary.ORDER_BUY is not defined.", exception.getMessage());
    assertEquals(asList(error("Given order type: {} is not defined.", "InputDefaultVocabulary.ORDER_BUY")), testLogger.getLoggingEvents());
  }

  @Test
  void processQueryBestAsk() {
    String[] splittedLine = new String[]{InputDefaultVocabulary.QUERY,InputDefaultVocabulary.QUERY_BEST_ASK};
    spiedProcessors.processQuery(splittedLine);
    Mockito.verify(orderBook).getBestAsk();
  }

  @Test
  void processQueryBestBid() {
    String[] splittedLine = new String[]{InputDefaultVocabulary.QUERY,InputDefaultVocabulary.QUERY_BEST_BID};
    spiedProcessors.processQuery(splittedLine);
    Mockito.verify(orderBook).getBestBid();
  }

  @Test
  void processQuerySizeByPrice() {
    String[] splittedLine = new String[]{InputDefaultVocabulary.QUERY,InputDefaultVocabulary.QUERY_SIZE,"21"};
    spiedProcessors.processQuery(splittedLine);
    Mockito.verify(orderBook).getSizeByPrice(21);
  }

  @Test
  void processNonNumberQueryPrice() {
    String[] splittedLine = new String[]{InputDefaultVocabulary.QUERY,InputDefaultVocabulary.QUERY_SIZE,"3i"};
    Exception exception = assertThrows(NumberFormatException.class, () ->
      spiedProcessors.processQuery(splittedLine));
    assertEquals("For input string: \"3i\"", exception.getMessage());
    assertEquals(asList(error(exception.getClass() + " For input string: \"3i\"")), testLogger.getLoggingEvents());
  }

  @Test
  void processQueryWithWrongType() {
    String[] splittedLine = new String[]{InputDefaultVocabulary.QUERY,"query","45"};
    Exception exception = assertThrows(UndefinedCommandException.class, () ->
      spiedProcessors.processQuery(splittedLine));
    assertEquals("Given command type: q is not defined.", exception.getMessage());
    assertEquals(asList(error("Given command type: {} is not defined.", InputDefaultVocabulary.QUERY)), testLogger.getLoggingEvents());
  }

  @Test
  void processUpdateAsk() {
    String[] splittedLine = new String[]{InputDefaultVocabulary.UPDATE,"100","31",InputDefaultVocabulary.UPDATE_ASK};
    spiedProcessors.processUpdate(splittedLine);
    Mockito.verify(orderBook).updateAsk(100, 31);
  }

  @Test
  void processUpdateBid() {
    String[] splittedLine = new String[]{InputDefaultVocabulary.UPDATE,"10000","3100",InputDefaultVocabulary.UPDATE_BID};
    spiedProcessors.processUpdate(splittedLine);
    Mockito.verify(orderBook).updateBid(10000, 3100);
  }

  @Test
  void processNonNumberUpdatePrice() {
    String[] splittedLine = new String[]{InputDefaultVocabulary.UPDATE,"L","900",InputDefaultVocabulary.UPDATE_ASK};
    Exception exception = assertThrows(NumberFormatException.class, () ->
      spiedProcessors.processUpdate(splittedLine));
    assertEquals("For input string: \"L\"", exception.getMessage());
    assertEquals(asList(error(exception.getClass() + " For input string: \"L\"")), testLogger.getLoggingEvents());
  }

  @Test
  void processNonNumberUpdateSize() {
      String[] splittedLine = new String[]{InputDefaultVocabulary.UPDATE,"555","R",InputDefaultVocabulary.UPDATE_ASK};
    Exception exception = assertThrows(NumberFormatException.class, () ->
      spiedProcessors.processUpdate(splittedLine));
    assertEquals("For input string: \"R\"", exception.getMessage());
    assertEquals(asList(error(exception.getClass() + " For input string: \"R\"")), testLogger.getLoggingEvents());
  }

  @Test
  void processNonNumberUpdatePriceSize() {
    String[] splittedLine = new String[]{InputDefaultVocabulary.UPDATE,"I","i",InputDefaultVocabulary.UPDATE_BID};
    Exception exception = assertThrows(NumberFormatException.class, () ->
      spiedProcessors.processUpdate(splittedLine));
    assertEquals("For input string: \"I\"", exception.getMessage());
    assertEquals(asList(error(exception.getClass() + " For input string: \"I\"")), testLogger.getLoggingEvents());
  }

  @Test
  void processUpdateWithWrongType() {
    String[] splittedLine = new String[]{InputDefaultVocabulary.UPDATE,"1","1","up"};
    Exception exception = assertThrows(UndefinedCommandException.class, () ->
      spiedProcessors.processUpdate(splittedLine));
    assertEquals("Given command type: up is not defined.", exception.getMessage());
    assertEquals(asList(error("Given command type: {} is not defined.", "up")), testLogger.getLoggingEvents());
  }

  @Test
  void processInputLineOrderBuy() {
    String line = "o,buy,11";
    String[] splittedLine = new String[]{InputDefaultVocabulary.ORDER,InputDefaultVocabulary.ORDER_BUY,"11"};
    spiedProcessors.processInputLine(line);
    Mockito.verify(spiedProcessors).processOrder(splittedLine);
  }

  @Test
  void processInputLineOrderSell() {
    String line = "o,sell,12";
    String[] splittedLine = new String[]{InputDefaultVocabulary.ORDER,InputDefaultVocabulary.ORDER_SELL,"12"};
    spiedProcessors.processInputLine(line);
    Mockito.verify(spiedProcessors).processOrder(splittedLine);
  }

  @Test
  void processInputLineQueryBestAsk() {
    String line = "q,best_ask";
    String[] splittedLine = new String[]{InputDefaultVocabulary.QUERY,InputDefaultVocabulary.QUERY_BEST_ASK};
    spiedProcessors.processInputLine(line);
    Mockito.verify(spiedProcessors).processQuery(splittedLine);
  }

  @Test
  void processInputLineQueryBestBid() {
    String line = "q,best_bid";
    String[] splittedLine = new String[]{InputDefaultVocabulary.QUERY,InputDefaultVocabulary.QUERY_BEST_BID};
    spiedProcessors.processInputLine(line);
    Mockito.verify(spiedProcessors).processQuery(splittedLine);
  }

  @Test
  void processInputLineQuerySizeByPrice() {
    String line = "q,size,2";
    String[] splittedLine = new String[]{InputDefaultVocabulary.QUERY,InputDefaultVocabulary.QUERY_SIZE,"2"};
    spiedProcessors.processInputLine(line);
    Mockito.verify(spiedProcessors).processQuery(splittedLine);
  }

  @Test
  void processInputLineUpdateAsk() {
    String line = "u,10,3,ask";
    String[] splittedLine = new String[]{InputDefaultVocabulary.UPDATE,"10","3",InputDefaultVocabulary.UPDATE_ASK};
    spiedProcessors.processInputLine(line);
    Mockito.verify(spiedProcessors).processUpdate(splittedLine);
  }

  @Test
  void processInputLineUpdateBid() {
    String line = "u,9,1,bid";
    String[] splittedLine = new String[]{InputDefaultVocabulary.UPDATE,"9","1",InputDefaultVocabulary.UPDATE_BID};
    spiedProcessors.processInputLine(line);
    Mockito.verify(spiedProcessors).processUpdate(splittedLine);
  }

  @Test
  void processInputLineWithWrongCommandType() {
    Exception exception = assertThrows(UndefinedCommandException.class, () ->
      spiedProcessors.processInputLine("update,query,45"));
    assertEquals("Given command type: update is not defined.", exception.getMessage());
    assertEquals(asList(error("Given command type: {} is not defined.", "update")), testLogger.getLoggingEvents());
  }
}