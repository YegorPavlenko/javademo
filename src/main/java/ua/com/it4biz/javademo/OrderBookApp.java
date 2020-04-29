package ua.com.it4biz.javademo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.it4biz.javademo.executors.Processors;
import ua.com.it4biz.javademo.model.OrderBook;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Optional;
import java.util.Scanner;

public class OrderBookApp {
  public static final Logger logger = LoggerFactory.getLogger(OrderBookApp.class);
  public static void main(String[] args) {
    // TODO properties file
    if (args.length < 2) {
      logger.error("File name not specified.");
      System.out.println("File name not specified.");
      System.exit(1);
    }
    String inputFileName = args[0];
    String outputFileName = args[1];

    OrderBook orderbook = new OrderBook();
    Processors processors = new Processors(orderbook);
    try (Scanner scanner = new Scanner(new File(inputFileName));
         BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));)
    {
      while (scanner.hasNextLine()) {
        Optional<String> result = processors.processInputLine(scanner.nextLine());
        if (result.isPresent()) {
          writer.write(result.get());
          writer.write("\n");
        }
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      e.printStackTrace();
    }
  }
}
