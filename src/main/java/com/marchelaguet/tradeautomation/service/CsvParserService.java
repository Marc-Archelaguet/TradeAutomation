package com.marchelaguet.tradeautomation.service;

import com.marchelaguet.tradeautomation.model.OrderExecution;
import com.marchelaguet.tradeautomation.model.Trade;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvParserService {

    @Autowired
    private TradeService tradeService;

    // Formato de fecha alemán que vimos en la imagen: "26.09.2026 22:40:16"
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public int parseAndSaveTrades(MultipartFile file) {
        int savedCount = 0;

        try (Reader reader = new InputStreamReader(file.getInputStream())) {

            // 1. OpenCSV lee el archivo y lo convierte en una lista de OrderExecution automáticamente
            List<OrderExecution> executions = new CsvToBeanBuilder<OrderExecution>(reader)
                    .withType(OrderExecution.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    // Pepperstone separa los CSV en alemán por comas o punto y coma, ajustaremos si falla
                    .withSeparator(',')
                    .build()
                    .parse();

            // 2. Lógica para emparejar Buy y Sell usando el Order-Nr.
            // Usamos un mapa para guardar temporalmente la orden de entrada
            Map<String, OrderExecution> openOrders = new HashMap<>();
            List<Trade> completedTrades = new ArrayList<>();

            for (OrderExecution exec : executions) {
                // Si el Profit es 0.00 o está vacío, asumimos que es la orden de apertura
                if (exec.getProfitText() == null || exec.getProfitText().isEmpty() || exec.getProfitText().equals("0.00")) {
                    openOrders.put(exec.getOrderNr(), exec);
                } else {
                    // Si tiene Profit, es la orden de cierre. Buscamos su apertura
                    OrderExecution openExec = openOrders.get(exec.getOrderNr());

                    if (openExec != null) {
                        // Construimos el Trade final emparejando los datos
                        Trade trade = buildTrade(openExec, exec);
                        completedTrades.add(trade);
                        // Lo quitamos del mapa de pendientes
                        openOrders.remove(exec.getOrderNr());
                    }
                }
            }

            // 3. Guardar todos los trades completados en MySQL
            for (Trade t : completedTrades) {
                tradeService.saveTrade(t);
                savedCount++;
            }

        } catch (Exception e) {
            System.err.println("Error procesando el CSV de Pepperstone: " + e.getMessage());
            throw new RuntimeException("No se ha podido leer el archivo. Comprueba el formato.");
        }

        return savedCount;
    }

    // Métodoo auxiliar para transformar dos ejecuciones en un Trade limpio
    private Trade buildTrade(OrderExecution open, OrderExecution close) {
        Trade trade = new Trade();

        trade.setAsset(open.getSymbol());
        trade.setDirection(open.getSeite().toUpperCase());

        // Convertir el texto de fecha ("26.09.2026 22:40:16") a objeto fecha
        trade.setOpenTime(LocalDateTime.parse(open.getZeitText(), FORMATTER));
        trade.setCloseTime(LocalDateTime.parse(close.getZeitText(), FORMATTER));

        // Convertir los textos a números, cambiando la coma decimal alemana por punto
        trade.setOpenPrice(Double.parseDouble(open.getActualPriceText().replace(".", "").replace(",", ".")));
        trade.setClosePrice(Double.parseDouble(close.getActualPriceText().replace(".", "").replace(",", ".")));
        trade.setNetPnl(Double.parseDouble(close.getProfitText().replace(".", "").replace(",", ".")));

        return trade;
    }
}