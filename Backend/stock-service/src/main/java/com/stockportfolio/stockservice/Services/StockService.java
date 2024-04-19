package com.stockportfolio.stockservice.Services;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.zankowski.iextrading4j.api.stocks.Quote;

@Service
@NoArgsConstructor
public class StockService implements StockServiceInterface {

    @Override
    public double getStockPrice(String ticker) {
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

        // return quote.getAskPrice();

        Config cfg = Config.builder()
                .key("03WG3PRNDHZT964C")
                .timeOut(10)
                .build();
        AlphaVantage.api().init(cfg);
        QuoteResponse response = AlphaVantage.api()
                .timeSeries().quote().forSymbol("IBM").fetchSync();

        return response.getPrice();
    }

}
