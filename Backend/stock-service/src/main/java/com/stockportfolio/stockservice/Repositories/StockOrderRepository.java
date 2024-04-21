package com.stockportfolio.stockservice.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockportfolio.stockservice.Models.StockOrder;

public interface StockOrderRepository extends JpaRepository<StockOrder, Long> {
    Optional<List<StockOrder>> findAllByEmail(String email);

    Optional<StockOrder> findBySymbolAndEmailAndOrderType(String ticker, String email, String orderType);
}
