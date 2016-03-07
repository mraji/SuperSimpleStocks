# SuperSimpleStocks

## Introduction
This is a simple implementation of Global Beverage Corporation Exchange.
For a given stock,
* calculate the dividend yield
* calculate the P/E Ratio
* record a trade, with timestamp, quantity of shares, buy or sell indicator and price
* Calculate Stock Price based on trades recorded in past 15 minutes
Calculate the GBCE All Share Index using the geometric mean of prices for all stocks

The Stocks data are set to :

| Stock Symbol | Type    | Last Dividend | Fixed Dividend | Par Value |
|--------------|---------|---------------|----------------|-----------|
| TEA          |Common   |     0         |                | 100       |
| POP          |Common   |     8         |                | 100       |   
| ALE          |Common   |     23        |                | 60        |
| GIN          |Preferred|     8         |      2%        | 100       |
| JOE          |Common   |     13        |                | 250       |

## Build
Go to root folder and run :
mvn clean install

## Usage
<b> java -jar ./target/JARNAME.jar numberOfWorkers interval </b> where :

* numberOfWorkers: number of threads participating in the stock market
* interval: the max interval between each transaction (per thread).

<b> java -jar ./target/JARNAME.jar </b>, using default option in this case :

* numberOfWorkers: 3
* interval: 5000

## TODO
* Accept stocks data as input.
