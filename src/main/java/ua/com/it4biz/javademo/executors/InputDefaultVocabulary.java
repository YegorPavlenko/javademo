package ua.com.it4biz.javademo.executors;

// Settings could be used from property file and when correspond value is absent in it then use from this default values
// TODO get settings from properties file
public class InputDefaultVocabulary {
  public static final String DELIMITER = ",";

  public static final String ORDER = "o";
  public static final String QUERY = "q";
  public static final String UPDATE = "u";

  public static final String ORDER_BUY = "buy";
  public static final String ORDER_SELL = "sell";

  public static final String QUERY_BEST_ASK = "best_ask";
  public static final String QUERY_BEST_BID = "best_bid";
  public static final String QUERY_SIZE = "size";

  public static final String UPDATE_ASK = "ask";
  public static final String UPDATE_BID = "bid";

  public static final int COMMAND_TYPE_POSITION = 0;

  public static final int QUERY_COMMAND_POSITION = 1;

  public static final int ORDER_COMMAND_POSITION = 1;

  public static final int QUERY_SIZE_BY_PRICE_POSITION = 2;

  public static final int ORDER_BUY_SIZE_POSITION = 2;
  public static final int ORDER_SELL_SIZE_POSITION = 2;

  public static final int UPDATE_COMMAND_POSITION = 3;
  public static final int UPDATE_PRICE_POSITION = 1;
  public static final int UPDATE_SIZE_POSITION = 2;
}
