package com.stockportfolio.stockservice.Controllers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.stockportfolio.stockservice.Services.StockService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    @PostMapping("/get-stock-price")
    public ResponseEntity<?> getStockPrice(@RequestBody JsonNode request) {
        String ticker = request.get("ticker").asText();
        double price = stockService.findStockPrice(stockService.findStock(ticker));
        Map<String, Object> response = new HashMap<>();
        response.put("price", price);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get-stock-high")
    public ResponseEntity<?> getStockHigh(@RequestBody JsonNode request) {
        String ticker = request.get("ticker").asText();
        double price = stockService.findStockHigh(stockService.findStock(ticker));
        Map<String, Object> response = new HashMap<>();
        response.put("high", price);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get-stock-low")
    public ResponseEntity<?> getStockLow(@RequestBody JsonNode request) {
        String ticker = request.get("ticker").asText();
        double price = stockService.findStockLow(stockService.findStock(ticker));
        Map<String, Object> response = new HashMap<>();
        response.put("low", price);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get-stock-open")
    public ResponseEntity<?> getStockOpen(@RequestBody JsonNode request) {
        String ticker = request.get("ticker").asText();
        double price = stockService.findStockOpen(stockService.findStock(ticker));
        Map<String, Object> response = new HashMap<>();
        response.put("open", price);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get-stock-prev-close")
    public ResponseEntity<?> getStockPrevClose(@RequestBody JsonNode request) {
        String ticker = request.get("ticker").asText();
        double price = stockService.findStockPrevClose(stockService.findStock(ticker));
        Map<String, Object> response = new HashMap<>();
        response.put("prevclose", price);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get-stock-prev-volume")
    public ResponseEntity<?> getStockVolume(@RequestBody JsonNode request) {
        String ticker = request.get("ticker").asText();
        double price = stockService.findStockVolume(stockService.findStock(ticker));
        Map<String, Object> response = new HashMap<>();
        response.put("volume", price);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get-stock-intraday-history")
    public ResponseEntity<?> getStockIntradayHistory(@RequestBody JsonNode request) {
        String ticker = request.get("ticker").asText();
        TimeSeriesResponse timeSeriesResponse = stockService.findStockIntradayHistory(ticker);
        Map<String, Object> response = new HashMap<>();
        response.put("metadata", timeSeriesResponse.getMetaData());
        response.put("stockunits", timeSeriesResponse.getStockUnits());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get-stock-daily-history")
    public ResponseEntity<?> getStockDailyHistory(@RequestBody JsonNode request) {
        String ticker = request.get("ticker").asText();
        TimeSeriesResponse timeSeriesResponse = stockService.findStockDailyHistory(ticker);
        Map<String, Object> response = new HashMap<>();
        response.put("metadata", timeSeriesResponse.getMetaData());
        response.put("stockunits", timeSeriesResponse.getStockUnits());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get-stock-weekly-history")
    public ResponseEntity<?> getStockWeeklyHistory(@RequestBody JsonNode request) {
        String ticker = request.get("ticker").asText();
        TimeSeriesResponse timeSeriesResponse = stockService.findStockWeeklyHistory(ticker);
        Map<String, Object> response = new HashMap<>();
        response.put("metadata", timeSeriesResponse.getMetaData());
        response.put("stockunits", timeSeriesResponse.getStockUnits());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get-stock-monthly-history")
    public ResponseEntity<?> getStockMonthlyHistory(@RequestBody JsonNode request) {
        String ticker = request.get("ticker").asText();
        TimeSeriesResponse timeSeriesResponse = stockService.findStockMonthlyHistory(ticker);
        Map<String, Object> response = new HashMap<>();
        response.put("metadata", timeSeriesResponse.getMetaData());
        response.put("stockunits", timeSeriesResponse.getStockUnits());
        return ResponseEntity.ok(response);
    }
}
