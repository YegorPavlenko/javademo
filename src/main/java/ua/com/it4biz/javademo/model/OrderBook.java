package ua.com.it4biz.javademo.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.it4biz.javademo.exceptions.UndefinedOrderException;

import java.util.TreeMap;

// OrderBookApp structure is different from given in task (here are no spreads values) because
// in task only operation with spread is getting size by price. Therefore looks like better to split asks and bids in
// two more simple collections than use one more complex.
public class OrderBook {
  private static final String ASK_ORDER = "ask";
  private static final String BID_ORDER = "bid";

  public static final String MARKET_BUY = "market_buy";
  public static final String MARKET_SELL = "market_sell";

  // In real app most likely here would be used some "OrderBookRecord" type for additional domain specific methods instead of TreeMap.
  private final TreeMap<Integer, Integer> asks;
  private final TreeMap<Integer, Integer> bids;

  public static final Logger logger = LoggerFactory.getLogger(OrderBook.class);

  public OrderBook() {
    asks = new TreeMap<>();
    bids = new TreeMap<>();
  }

  public void updateAsk(Integer price, Integer size) {
    if (price == null || price == 0) {
      logger.info("{} parameter is empty in {} method", "price", "updateAsk");
      return;
    }
    if (size == null || size == 0) {
      logger.info("{} parameter is empty in {} method", "size", "updateAsk");
      return;
    }
    // check if ask is executed immediately
    CustomPair bestBid = getBestBid();
    if (bestBid == null) {
      asks.put(price, size);
      return;
    }
    Integer bestBidPrice = bestBid.getKey();
    Integer bestBidSize = bestBid.getValue();
    if (bestBidPrice > 0 && bestBidPrice >= price) {
      if (bestBidSize.equals(size)) {
        bids.remove(bestBidPrice);
        return;
      }
      if (bestBidSize > size) {
        // decrement size of element
        bids.compute(bestBidPrice, (k, v) -> v - size); // null here means concurrent access. Allow to crash because concurrency not allowed.
        return;
      }
      // bestBidSize < size
      bids.remove(bestBidPrice);
      updateAsk(price, size - bestBidSize);
    } else {
      asks.put(price, size);
    }
  }

  // This part looks like "copy-paste". The decision to leave it in this way was made to better readability what is going on.
  public void updateBid(Integer price, Integer size) {
    if (price == null || price == 0) {
      logger.info("{} parameter is empty in {} method", "price", "updateBid");
      return;
    }
    if (size == null || size == 0) {
      logger.info("{} parameter is empty in {} method", "size", "updateBid");
      return;
    }
    // check if bid is executed immediately
    CustomPair bestAsk = getBestAsk();
    if (bestAsk == null) {
      bids.put(price, size);
      return;
    }
    Integer bestAskPrice = bestAsk.getKey();
    Integer bestAskSize = bestAsk.getValue();
    if (bestAskPrice > 0 && bestAskPrice <= price) {
      if (bestAskSize.equals(size)) {
        asks.remove(bestAskPrice);
        return;
      }
      if (bestAskSize > size) {
        // decrement size of element
        asks.compute(bestAskPrice, (k, v) -> v - size); // null here means concurrent access. Allow to crash because concurrency is not allowed.
        return;
      }
      // bestAskSize < size
      asks.remove(bestAskPrice);
      updateBid(price, size - bestAskSize);
    } else {
      bids.put(price, size);
    }
  }

  private boolean isRecordValid(CustomPair curRecord) {
    return curRecord != null && curRecord.getKey() != null && curRecord.getKey() != 0 &&
      curRecord.getValue() != null && curRecord.getValue() != 0;
  }

  // if market size is not enough to execute the total market order size then order will not be fully executed
  public void marketOrder(Integer orderSize, String orderType) {
    if (orderSize == null || orderSize == 0) {
      logger.info("{} parameter is empty in {} method", "orderSize", "marketOrder");
      return;
    }
    if (orderType == null || orderType.isEmpty()) {
      logger.info("{} parameter is empty in {} method", "orderType", "marketOrder");
      return;
    }
    // Map.Entry<Integer, Integer> curRecord;
    CustomPair curRecord;
    Integer curRecordKey;
    Integer curRecordSize;
    TreeMap<Integer, Integer> curOrderBookPart;
    switch (orderType) {
      case MARKET_BUY:
        curRecord = getBestAsk();
        curOrderBookPart = asks;
        break;
      case MARKET_SELL:
        curRecord = getBestBid();
        curOrderBookPart = bids;
        break;
      default:
        throw new UndefinedOrderException(
          "Given order type: " + orderType + " is not defined."
        );
    }
    // TODO check if order book is empty
    if (!isRecordValid(curRecord)) {
      logger.error("Somehow current record in orderbook is not valid.");
      return;
    }
    curRecordKey = curRecord.getKey();
    curRecordSize = curRecord.getValue();
    if (curRecordSize.equals(orderSize)) {
      // remove least
      curOrderBookPart.remove(curRecordKey);
      return;
    }
    if (curRecordSize < orderSize) {
      // remove least and take next least recursively
      Integer diff = orderSize - curRecordSize;
      curOrderBookPart.remove(curRecordKey);
      marketOrder(diff, orderType);
      return;
    }
    // (curRecordSize > orderSize)
    // decrement orderSize of element
    curOrderBookPart.compute(curRecordKey, (k, v) -> v - orderSize); // null here means concurrent access. Allow to crash because concurrency not allowed.
  }

  // In real app most likely here would be used some "OrderBookRecord" type.
  public CustomPair getBestAsk() {
    if (asks.firstEntry() == null) return null;
    return new CustomPair(asks.firstEntry());
  }

  // In real app most likely here would be used some "OrderBookRecord" type.
  public CustomPair getBestBid() {
    if (bids.lastEntry() == null) return null;
    return new CustomPair(bids.lastEntry());
  }

  public Integer getSizeByPrice(Integer price) {
    Integer result;
    if (price == null || price == 0) return 0;

    result = asks.get(price);
    if (result == null) {
      result = bids.get(price);
    }
    // if there is no record neither in asks nor in bids return 0
    return (result == null) ? 0 : result;
  }
}
