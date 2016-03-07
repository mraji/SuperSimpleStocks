package simpleStocks;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import simpleStocks.stockExchange.GBCE;
import simpleStocks.stockExchange.StockExchangeException;
import simpleStocks.stocks.CommonStock;
import simpleStocks.stocks.PreferredStock;
import simpleStocks.stocks.Stock;
import simpleStocks.utils.ReporterThread;
import simpleStocks.utils.WorkerThread;

public class Run {
  public static void main(String[] args) {
    int numberOfWorkers = 3;
    int interval = 5000;
    if (args != null && args.length > 0) {
      if (args.length == 2) {
        numberOfWorkers = Integer.parseInt(args[0]);
        interval = Integer.parseInt(args[1]);
      }
      else if (args.length == 1) {
        numberOfWorkers = Integer.parseInt(args[0]);
      }
      else {
        System.out.println("usage: java -jar JARNAME.JAR numberOfWorkers interval");
        System.out.println("numberOfWorkers: number of threads participating in the stock market");
        System.out.println("interval: the max interval between each transaction (per thread).");
        System.out.println("default values are : java -jar JARNAME.JAR 3 5000");
        System.exit(0);
      }
    }

    // ---------------Intitialise Test data --------
    Set<Stock> stocks = new HashSet<Stock>();
    ExecutorService executor = Executors.newFixedThreadPool(numberOfWorkers + 1);
    stocks.add(new CommonStock("TEA", 100f, 0f));
    stocks.add(new CommonStock("POP", 100f, 8f));
    stocks.add(new CommonStock("ALE", 60f, 23f));
    stocks.add(new PreferredStock("GIN", 100f, 8f, 2f));
    stocks.add(new CommonStock("JOE", 250f, 13f));
    // End Of : Intitialise Test data ---------------
    GBCE gbce = new GBCE(stocks);
    gbce.open();

    // creating agents
    try {
      for (int i = 0; i < numberOfWorkers; i++) {
        Runnable worker = new WorkerThread(i, gbce, interval);
        executor.execute(worker);
      }
      Runnable reportWorker = new ReporterThread(gbce, interval);
      executor.execute(reportWorker);
      executor.shutdown();
      if (!executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
      }
    }
    catch (InterruptedException e) {
      System.out.println("Exception raised by threadWorker");
      e.printStackTrace();
    }
    catch (StockExchangeException e) {
      System.out.println("Exception raised by Global Beverage Stock Exchange.");
      e.printStackTrace();
    }
    System.out.println("All trades are terminated. Closing..");
    gbce.close();
  }

}