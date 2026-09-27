package com.marchelaguet.tradeautomation.service;

import com.marchelaguet.tradeautomation.model.Trade;
import com.marchelaguet.tradeautomation.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
// Recorda importar el teu Trade i TradeRepository!

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    // Guarda una operació a la BD
    public Trade saveTrade(Trade trade) {
        // Més endavant, aquí dins calcularem les mètriques i ho enviarem a Google Sheets
        return tradeRepository.save(trade);
    }

    // Consulta totes les operacions (útil per comprovar-ho)
    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }
}
