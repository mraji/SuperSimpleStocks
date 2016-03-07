package simpleStocks.stockExchange;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import simpleStocks.stocks.Stock;
import simpleStocks.trades.Trade;
import simpleStocks.trades.Trade.TradeType;

public class GBCE {
  private static final long FIFTEEN_MIN = 15000000l;

  private final Set<Stock> stocks;

  private final String[] symbolListing;

  private Map<Stock, Vector<Trade>> tradeExchange;

  private volatile boolean isOpen = false;

  public GBCE(Set<Stock> stocks) {
    if (stocks != null && !stocks.isEmpty()) {
      this.stocks = stocks;
      symbolListing = new String[stocks.size()];
      tradeExchange = new HashMap<Stock, Vector<Trade>>();
      int index = 0;
      for (Stock stock : stocks) {
        symbolListing[index] = stock.getSymbol();
        tradeExchange.put(stock, new Vector<Trade>());
        index++;
      }
    }
    else {
      throw new StockExchangeException();
    }
  }

  public String[] listStocks() {
    return symbolListing;
  }

  public Stock getStock(String symbol) {
    for (Stock stock : stocks) {
      if (stock.getSymbol().equals(symbol)) {
        return stock;
      }
    }
    return null;
  }

  public void buy(Stock stock, int quantity, Double price) throws StockExchangeInputException {
    if (isOpen && quantity > 0 && price != null) {
      Trade trade = new Trade(new Timestamp(System.currentTimeMillis()), stock, quantity, TradeType.BUY, price);
      tradeExchange.get(stock).add(trade);
      System.out.println("New Buy saved: " + trade.toString());
    }
    else {
      throw new StockExchangeInputException();
    }
  }

  public void sell(Stock stock, int quantity, Double price) throws StockExchangeInputException {
    if (isOpen && quantity > 0 && price != null) {
      Trade trade = new Trade(new Timestamp(System.currentTimeMillis()), stock, quantity, TradeType.SELL, price);
      tradeExchange.get(stock).add(trade);
      System.out.println("New sell saved: " + trade.toString());
    }
    else {
      throw new StockExchangeInputException();
    }
  }

  public synchronized void open() {
    if (!isOpen) {
      System.out.println("Stock market open");
      isOpen = true;
    }
  }

  public synchronized void close() {
    if (isOpen) {
      System.out.println("All Share Index: " + computeAllShareIndex());
      isOpen = false;
    }
  }

  public Double computeStockPrice(Stock stock) {
    Vector<Trade> trades = tradeExchange.get(stock);
    if (trades.size() > 0) {
      Integer quantity = 0;
      Double temp = 0d;
      long now = System.currentTimeMillis();
      for (Trade trade : trades) {
        if (now - trade.timestamp.getTime() < FIFTEEN_MIN) {
          temp += trade.quantity * trade.price;
          quantity += trade.quantity;
        }

      }
      if (quantity > 0) {
        return ((Double)temp / quantity);
      }
    }
    return Double.NaN;
  }

  public Double computeAllShareIndex() {
    Double temp = null;
    int pricesNum = 0;
    for (Map.Entry<Stock, Vector<Trade>> map : tradeExchange.entrySet()) {
      if (map.getValue() != null && map.getValue().size() > 0) {
        Vector<Trade> trades = map.getValue();
        if (temp == null) {
          temp = trades.get(trades.size() - 1).price;
        }
        else {
          temp *= trades.get(trades.size() - 1).price;
        }
        pricesNum++;
      }
    }
    if (temp != null) {
      Double order = 1d / pricesNum;
      return Math.pow(temp, order);
    }
    return Double.NaN;
  }

}
