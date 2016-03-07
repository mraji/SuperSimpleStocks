package simpleStocks.trades;

import java.sql.Timestamp;

import simpleStocks.stocks.Stock;

public class Trade {

  public final Timestamp timestamp;

  public final TradeType type;

  public final Stock stock;

  public final int quantity;

  public final Double price;

  public enum TradeType {
    BUY, SELL;
  }

  public Trade(Timestamp timestamp, Stock stock, int quantity, TradeType type, Double price) {
    this.timestamp = timestamp;
    this.type = type;
    this.stock = stock;
    this.quantity = quantity;
    this.price = price;
  }

  @Override
  public String toString() {
    return "Trade of type " + type.toString() + ", stock " + stock + ", quantity " + quantity + ", price " + price +
        ", time " + timestamp;
  }

}
