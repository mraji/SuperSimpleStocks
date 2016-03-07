package simpleStocks.utils;

import java.util.Random;

import simpleStocks.stockExchange.GBCE;
import simpleStocks.stockExchange.StockExchangeException;
import simpleStocks.stockExchange.StockExchangeInputException;
import simpleStocks.stocks.Stock;

public class WorkerThread implements Runnable {

  private int interval, tradeNumbers = 20;

  private int seed;

  private Random generator;

  private GBCE gbce;

  public WorkerThread(int seed, GBCE gbce, int interval) throws StockExchangeException {
    if (gbce != null) {
      this.seed = seed;
      this.gbce = gbce;
      this.interval = interval;
      generator = new Random(seed);
    }
    else {
      throw new StockExchangeException("No CBGE have been provided. Nothing to work on.");
    }
  }

  public void run() {
    try {
      while (tradeNumbers > 0) {
        Thread.sleep(generator.nextInt(interval));
        tradeNumbers--;
        report("trade number: " + (20 - tradeNumbers));
        String[] stockArr = gbce.listStocks();
        Stock stock = gbce.getStock(stockArr[generator.nextInt(stockArr.length)]);
        Double stockPrice = gbce.computeStockPrice(stock);
        boolean action = generator.nextBoolean();
        Double tradePrice = 0d;
        int quantity = generator.nextInt(100);
        if (stockPrice != Double.NaN) {
          tradePrice = 100d * (1 + (generator.nextBoolean() ? 1 : -1) * generator.nextFloat());
        }
        else {
          tradePrice = stockPrice * (1 + (generator.nextBoolean() ? 1 : -1) * generator.nextFloat());
        }

        if (action) {
          gbce.sell(stock, quantity, tradePrice);
        }
        else {
          gbce.buy(stock, quantity, tradePrice);

        }

      }
    }
    catch (InterruptedException e) {
      System.out.println("Exception raised by Worker number : " + seed + " during operation number :" +
          (20 - tradeNumbers));
      e.printStackTrace();
    }
    catch (StockExchangeInputException e) {
      System.out.println("Exception raised by Global Beverage Stock Exchange, during operation number :" +
          (20 - tradeNumbers) + ", in Worker number : " + seed);
      e.printStackTrace();
    }
  }

  public void report(String text) {
    System.out.println("Thread " + seed + " : " + text);
  }

}
