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
    public ResponseEntity<?> getStock(@RequestBody JsonNode request) {
        String ticker = request.get("ticker").asText();
        double price = stockService.getStockPrice(ticker);
        Map<String, Object> response = new HashMap<>();
        response.put("price", price);
        return ResponseEntity.ok(response);
    }
}
