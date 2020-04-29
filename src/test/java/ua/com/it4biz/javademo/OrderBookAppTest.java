package ua.com.it4biz.javademo;

import com.github.valfirst.slf4jtest.TestLogger;
import com.github.valfirst.slf4jtest.TestLoggerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.valfirst.slf4jtest.LoggingEvent.error;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;*/
//import static uk.org.lidalia.slf4jtest.LoggingEvent.error;

class OrderBookAppTest {
  TestLogger testLogger = TestLoggerFactory.getTestLogger(OrderBookApp.class);

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
    testLogger.clear();
    TestLoggerFactory.clear();
  }

  @Test
  void main() {
    OrderBookApp.main(new String[]{"notexist_input.csv", "notexist_output.csv"});
    assertEquals(asList(error("notexist_input.csv (No such file or directory)")), testLogger.getLoggingEvents());
  }
}