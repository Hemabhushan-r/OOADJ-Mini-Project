package com.stockportfolio.stockservice.Services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.parameters.Function;
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockportfolio.stockservice.Classes.HttpRequest;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class StockService implements StockServiceInterface {

    @Override
    public QuoteResponse findStock(String ticker) {
        Config cfg = Config.builder()
                .key("03WG3PRNDHZT964C")
                .timeOut(10)
                .build();
        AlphaVantage.api().init(cfg);
        QuoteResponse response = AlphaVantage.api()
                .timeSeries().quote().forSymbol(ticker).fetchSync();
        return response;
    }

    @Override
    public double findStockPrice(QuoteResponse quoteResponse) {
        // IEXCloudClient cloudClient =
        // IEXTradingClient.create(IEXTradingApiVersion.IEX_CLOUD_V1_SANDBOX,
        // new IEXCloudTokenBuilder()
        // .withPublishableToken("pk_f08af885d2e5433e84190311408490b8")
        // .build());
        // IEXCloudClient cloudClient = IEXTradingClient.create(new
        // IEXCloudTokenBuilder()
        // .withPublishableToken("pk_f08af885d2e5433e84190311408490b8")
        // .build());
        // Quote quote = cloudClient.executeRequest(new QuoteRequestBuilder()
        // .withSymbol("AAPL")
        // .build());

        // return quote.findAskPrice();

        return quoteResponse.getPrice();
    }

    @Override
    public double findStockVolume(QuoteResponse quoteResponse) {

        return quoteResponse.getVolume();
    }

    @Override
    public double findStockHigh(QuoteResponse quoteResponse) {

        return quoteResponse.getHigh();
    }

    @Override
    public double findStockLow(QuoteResponse quoteResponse) {

        return quoteResponse.getLow();
    }

    @Override
    public double findStockOpen(QuoteResponse quoteResponse) {

        return quoteResponse.getOpen();
    }

    @Override
    public double findStockPrevClose(QuoteResponse quoteResponse) {
        return quoteResponse.getPreviousClose();
    }

    @Override
    public TimeSeriesResponse findStockIntradayHistory(String ticker) {
        Config cfg = Config.builder()
                .key("03WG3PRNDHZT964C")
                .timeOut(10)
                .build();
        AlphaVantage.api().init(cfg);
        TimeSeriesResponse response = AlphaVantage.api()
                .timeSeries().intraday().forSymbol(ticker).fetchSync();
        return response;
    }

    @Override
    public TimeSeriesResponse findStockWeeklyHistory(String ticker) {
        Config cfg = Config.builder()
                .key("03WG3PRNDHZT964C")
                .timeOut(10)
                .build();
        AlphaVantage.api().init(cfg);
        TimeSeriesResponse response = AlphaVantage.api()
                .timeSeries().weekly().forSymbol(ticker).fetchSync();
        return response;
    }

    @Override
    public TimeSeriesResponse findStockDailyHistory(String ticker) {
        Config cfg = Config.builder()
                .key("03WG3PRNDHZT964C")
                .timeOut(10)
                .build();
        AlphaVantage.api().init(cfg);
        TimeSeriesResponse response = AlphaVantage.api()
                .timeSeries().daily().forSymbol(ticker).fetchSync();
        return response;
    }

    @Override
    public TimeSeriesResponse findStockMonthlyHistory(String ticker) {
        Config cfg = Config.builder()
                .key("03WG3PRNDHZT964C")
                .timeOut(10)
                .build();
        AlphaVantage.api().init(cfg);
        TimeSeriesResponse response = AlphaVantage.api()
                .timeSeries().monthly().forSymbol(ticker).fetchSync();
        return response;
    }

    @Override
    public List<Map<String, Object>> searchByKeyword(String keyword) {
        String apiKey = "03WG3PRNDHZT964C";
        String url = String.format("%sfunction=%s&keywords=%s&apikey=%s",
                Config.BASE_URL,
                "SYMBOL_SEARCH",
                keyword.replace(" ", "%20"),
                apiKey);
        try {
            String jsonResponse = HttpRequest.sendGetRequest(url);
            ObjectMapper objectMapper = new ObjectMapper();

            // Serialize map to JSON string

            HashMap<String, Object> map = objectMapper.readValue(jsonResponse, HashMap.class);
            return (List<Map<String, Object>>) map.get("bestMatches");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
