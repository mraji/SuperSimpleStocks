package simpleStocks.stocks;

public class PreferredStock extends AbstractStock {

  private final Float fixedDividend;

  public PreferredStock(String symbol, Float parValue, Float lastDividend, Float fixedDividend) {
    super(symbol, parValue, lastDividend);
    this.fixedDividend = fixedDividend;
  }

  @Override
  public StockType getType() {
    return StockType.PREFERRED;
  }

  @Override
  public Double calculateDividendYield(Double tickerPrice) {
    return (double)(getParValue() * fixedDividend / tickerPrice);
  }

  public Float getFixedDividend() {
    return fixedDividend;
  }
}
