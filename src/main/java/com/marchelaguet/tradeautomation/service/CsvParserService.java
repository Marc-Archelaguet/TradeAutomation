package com.marchelaguet.tradeautomation.service;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStreamReader;
import java.io.Reader;

@Service
public class CsvParserService {

    @Autowired
    private TradeService tradeService;

    // Rep l'arxiu directament des de la petició web
    public int parseAndSaveTrades(MultipartFile file) {
        int savedCount = 0;

        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVReader csvReader = new CSVReader(reader)) {

            // Saltem la primera fila perquè conté les capçaleres de text
            csvReader.skip(1);

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                // TODO: Aquí traduirem el text a un objecte Trade
                // Exemple: String asset = line[1];
                // Exemple: double rr = Double.parseDouble(line[5]);

                // Trade nouTrade = new Trade(...);
                // tradeService.saveTrade(nouTrade);

                savedCount++;
            }
        } catch (Exception e) {
            System.err.println("Error processant el CSV: " + e.getMessage());
            throw new RuntimeException("No s'ha pogut llegir l'arxiu");
        }

        return savedCount; // Retornem el número d'operacions guardades amb èxit
    }
}