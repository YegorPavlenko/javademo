package ua.com.it4biz.javademo.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.it4biz.javademo.exceptions.UndefinedCommandException;
import ua.com.it4biz.javademo.exceptions.UndefinedOrderException;
import ua.com.it4biz.javademo.model.CustomPair;
import ua.com.it4biz.javademo.model.OrderBook;

import java.util.Optional;

public class Processors {
  private final OrderBook orderBook;

  public static final Logger logger = LoggerFactory.getLogger(Processors.class);

  public Processors(OrderBook theOrderBook) {
    orderBook = theOrderBook;
  }

  /* and market orders in the following format:
    o,buy,<size> - removes <size> shares out of asks, most cheap ones.
    o,sell,<size> - removes <size> shares out of bids, most expensive ones.
    In case of a buy order this is similar to going to a market (assuming that you want to buy <size> similar
    items there, and that all instances have identical quality, so price is the only factor) - you buy <size>
    units at the cheapest price available. */
  public void processOrder(String[] values) {
    Integer size;
    switch (values[InputDefaultVocabulary.ORDER_COMMAND_POSITION]) {
      case InputDefaultVocabulary.ORDER_BUY:
        try {
          size = Integer.parseInt(values[InputDefaultVocabulary.ORDER_BUY_SIZE_POSITION]);
        } catch (NumberFormatException e) {
          // TODO For now just rethrow.
          logger.error(e.getClass() + " " + e.getMessage());
          throw e;
        }
        orderBook.marketOrder(size, OrderBook.MARKET_BUY);
        break;
      case InputDefaultVocabulary.ORDER_SELL:
        try {
          size = Integer.parseInt(values[InputDefaultVocabulary.ORDER_SELL_SIZE_POSITION]);
        } catch (NumberFormatException e) {
          // TODO For now just rethrow.
          logger.error(e.getClass() + " " + e.getMessage());
          throw e;
        }
        orderBook.marketOrder(size, OrderBook.MARKET_SELL);
        break;
      default:
        logger.error("Given order type: {} is not defined.", values[InputDefaultVocabulary.ORDER_COMMAND_POSITION]);
        throw new UndefinedOrderException(
          "Given order type: " + values[InputDefaultVocabulary.ORDER_COMMAND_POSITION] + " is not defined."
        );
    }
  }

  /* queries in the following format:
     q,best_bid - print best bid price and size
     q,best_ask - print best ask price and size
     q,size,<price> - print size at specified price (bid, ask or spread). */
  public String processQuery(String[] values) {
    CustomPair tmp = null;
    switch (values[InputDefaultVocabulary.QUERY_COMMAND_POSITION]) {
      case InputDefaultVocabulary.QUERY_BEST_ASK:
        tmp = orderBook.getBestAsk();
        return (tmp == null)? null: tmp.toString();
      case InputDefaultVocabulary.QUERY_BEST_BID:
        tmp = orderBook.getBestBid();
        return (tmp == null)? null: tmp.toString();
      case InputDefaultVocabulary.QUERY_SIZE:
        Integer price;
        try {
          price = Integer.parseInt(values[InputDefaultVocabulary.QUERY_SIZE_BY_PRICE_POSITION]);
        } catch (NumberFormatException e) {
          // TODO For now just rethrow.
          logger.error(e.getClass() + " " + e.getMessage());
          throw e;
        }
        return orderBook.getSizeByPrice(price).toString();
      default:
        logger.error("Given command type: {} is not defined.", values[InputDefaultVocabulary.COMMAND_TYPE_POSITION]);
        throw new UndefinedCommandException(
          "Given command type: " + values[InputDefaultVocabulary.COMMAND_TYPE_POSITION] + " is not defined."
        );
    }
  }

  public void processUpdate(String[] values) {
  /* updates to the limit order book in the following format:
    u,<price>,<size>,bid - set bid size at <price> to <size> (<size> shares in total are now being offered at
      <price>)
     u,<price>,<size>,ask - set ask size at <price> to <size> */
    Integer price;
    Integer size;
    try {
      price = Integer.parseInt(values[InputDefaultVocabulary.UPDATE_PRICE_POSITION]);
      size = Integer.parseInt(values[InputDefaultVocabulary.UPDATE_SIZE_POSITION]);
    } catch (NumberFormatException e) {
      // TODO For now just rethrow.
      logger.error(e.getClass() + " " + e.getMessage());
      throw e;
    }
    switch (values[InputDefaultVocabulary.UPDATE_COMMAND_POSITION]) {
      case InputDefaultVocabulary.UPDATE_ASK:
        orderBook.updateAsk(price, size);
        break;
      case InputDefaultVocabulary.UPDATE_BID:
        orderBook.updateBid(price, size);
        break;
      default:
        logger.error("Given command type: {} is not defined.", values[InputDefaultVocabulary.UPDATE_COMMAND_POSITION]);
        throw new UndefinedCommandException(
          "Given command type: " + values[InputDefaultVocabulary.UPDATE_COMMAND_POSITION] + " is not defined."
        );
    }
  }

  public Optional<String> processInputLine(String line) {
    String[] values = line.split(InputDefaultVocabulary.DELIMITER);
    switch (values[InputDefaultVocabulary.COMMAND_TYPE_POSITION]) {
      case InputDefaultVocabulary.ORDER:
        processOrder(values);
        return Optional.empty();
      case InputDefaultVocabulary.QUERY:
        return Optional.ofNullable(processQuery(values));
      case InputDefaultVocabulary.UPDATE:
        processUpdate(values);
        return Optional.empty();
      default:
        logger.error("Given command type: {} is not defined.", values[InputDefaultVocabulary.COMMAND_TYPE_POSITION]);
        throw new UndefinedCommandException(
          "Given command type: " + values[InputDefaultVocabulary.COMMAND_TYPE_POSITION] + " is not defined."
        );
    }
  }
}
