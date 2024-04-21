package com.stockportfolio.stockservice.Services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.stockportfolio.stockservice.Models.StockOrder;

public interface StockServiceInterface {

    QuoteResponse findStock(String ticker);

    double findStockPrice(QuoteResponse quoteResponse);

    double findStockVolume(QuoteResponse quoteResponse);

    double findStockHigh(QuoteResponse quoteResponse);

    double findStockLow(QuoteResponse quoteResponse);

    double findStockOpen(QuoteResponse quoteResponse);

    double findStockPrevClose(QuoteResponse quoteResponse);

    TimeSeriesResponse findStockIntradayHistory(String ticker);

    TimeSeriesResponse findStockWeeklyHistory(String ticker);

    TimeSeriesResponse findStockDailyHistory(String ticker);

    TimeSeriesResponse findStockMonthlyHistory(String ticker);

    List<Map<String, Object>> searchByKeyword(String keyword);

    StockOrder findBySymbolAndEmailAndOrderType(String ticker, String email, String orderType);

    List<StockOrder> findAllByEmail(String email);

    StockOrder createOrUpdateStockOrder(StockOrder stockOrder);

    void deleteStockOrder(StockOrder stockOrder);
}