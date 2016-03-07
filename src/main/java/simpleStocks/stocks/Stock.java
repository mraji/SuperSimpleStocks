package simpleStocks.stocks;

public interface Stock {

  public String getSymbol();

  public Double calculateDividendYield(Double tickerPrice);

  public Double calculatePERatio(Double tickerPrice);

}
