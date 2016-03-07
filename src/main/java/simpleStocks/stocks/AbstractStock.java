package simpleStocks.stocks;

public abstract class AbstractStock implements Stock {

  private final String symbol;

  private final Float parValue;

  private final Float lastDividend;

  public enum StockType {
    COMMON, PREFERRED
  }

  public AbstractStock(String symbol, Float parValue, Float lastDividend) {
    this.symbol = symbol;
    this.parValue = parValue;
    this.lastDividend = lastDividend;
  }

  public abstract StockType getType();

  public abstract Double calculateDividendYield(Double tickerPrice);

  public Float getParValue() {
    return parValue;
  }

  public Float getLastDividend() {
    return lastDividend;
  }

  public String getSymbol() {
    return symbol;
  }

  public Double calculatePERatio(Double tickerPrice) {
    return ((double)tickerPrice / lastDividend);
  }

  @Override
  public final int hashCode() {
    return symbol.hashCode();
  }

  @Override
  public final String toString() {
    return "Stock  " + symbol + " of type " + getType();
  }

  @Override
  public final boolean equals(Object that) {
    if (that != null && that instanceof AbstractStock) {
      return ((AbstractStock)that).getSymbol().equals(this.symbol) &&
          ((AbstractStock)that).getType().equals(this.getType()) &&
          ((AbstractStock)that).getParValue() == this.getParValue();
    }
    else {
      return false;
    }
  }

}
