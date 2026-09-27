package com.marchelaguet.tradeautomation.controller;

import com.marchelaguet.tradeautomation.model.Trade;
import com.marchelaguet.tradeautomation.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/trades")
@CrossOrigin(origins = "*") // Permet que qualsevol frontend s'hi connecti més endavant
public class TradeController {

    @Autowired
    private TradeService tradeService;

    // Endpoint per rebre i guardar una operació
    @PostMapping
    public ResponseEntity<Trade> createTrade(@RequestBody Trade trade) {
        Trade savedTrade = tradeService.saveTrade(trade);
        return ResponseEntity.ok(savedTrade);
    }

    // Endpoint per consultar totes les operacions guardades
    @GetMapping
    public ResponseEntity<List<Trade>> getAllTrades() {
        return ResponseEntity.ok(tradeService.getAllTrades());
    }
}
