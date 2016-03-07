package simpleStocks.stocks;

public class CommonStock extends AbstractStock {

  public CommonStock(String symbol, Float parValue, Float lastDividend) {
    super(symbol, parValue, lastDividend);
  }

  @Override
  public StockType getType() {
    return StockType.COMMON;
  }

  @Override
  public Double calculateDividendYield(Double tickerPrice) {
    return (double)(getLastDividend() / tickerPrice);
  }
}
