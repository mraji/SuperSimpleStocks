package simpleStocks.utils;

import simpleStocks.stockExchange.GBCE;
import simpleStocks.stockExchange.StockExchangeException;
import simpleStocks.stocks.Stock;

public class ReporterThread implements Runnable {

  private int interval, tradeNumbers = 20;

  private GBCE gbce;

  public ReporterThread(GBCE gbce, int interval) throws StockExchangeException {
    if (gbce != null) {
      this.interval = interval;
      this.gbce = gbce;
    }
    else {
      throw new StockExchangeException("No CBGE have been provided. Nothing to report.");
    }
  }

  public void run() {
    try {
      while (tradeNumbers > 0) {
        Thread.sleep(interval);
        tradeNumbers--;
        String[] stockSymbols = gbce.listStocks();
        String[] stockReports = new String[stockSymbols.length];
        for (int i = 0; i < stockSymbols.length; i++) {
          Stock stock = gbce.getStock(stockSymbols[i]);
          Double stockPrice = gbce.computeStockPrice(stock);

          stockReports[i] = stock.toString() + " with stock price " + gbce.computeStockPrice(stock) +
              ", Dividend yield " + stock.calculateDividendYield(stockPrice) + " and P/E ratio " +
              stock.calculatePERatio(stockPrice);
        }

        report(gbce.computeAllShareIndex(), stockReports);
      }
    }
    catch (InterruptedException e) {
      System.out.println("Exception raised by Reporter, during report number :" + (20 - tradeNumbers));
      e.printStackTrace();
    }
  }

  public void report(Double allShareIndex, String[] stockReports) {
    System.out.println("---------------------------------------------------");
    System.out.println("Reporter: All Share Index => " + allShareIndex);
    for (String stockReport : stockReports) {
      System.out.println(stockReport);
    }
    System.out.println("---------------------------------------------------");
  }

}
