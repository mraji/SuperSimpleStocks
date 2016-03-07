
package simpleStocks.stockExchange;

public class StockExchangeException extends RuntimeException {

  /**
   *
   */
  private static final long serialVersionUID = -31021915641712154L;
  public StockExchangeException(String string) {
    super(string);
  }
  public StockExchangeException() {
    super();
  }
}
