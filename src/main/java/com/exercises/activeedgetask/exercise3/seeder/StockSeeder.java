package com.exercises.activeedgetask.exercise3.seeder;

import com.exercises.activeedgetask.exercise3.model.Stock;
import com.exercises.activeedgetask.exercise3.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockSeeder implements CommandLineRunner {

    private final StockRepository stockRepository;


    @Override
    public void run(String... args) throws Exception {
        seedStockData();
    }

    private void seedStockData() {
        if (stockRepository.count() == 0) {
            Timestamp now = Timestamp.from(Instant.now());
            List<Stock> stockData = Arrays.asList(
                    new Stock(null, "Apple Inc.", BigDecimal.valueOf(150.50), now, now),
                    new Stock(null, "Microsoft Corp.", BigDecimal.valueOf(250.75), now, now),
                    new Stock(null, "Google LLC", BigDecimal.valueOf(2725.00), now, now),
                    new Stock(null, "Amazon.com Inc.", BigDecimal.valueOf(3100.00), now, now)
            );

            stockRepository.saveAll(stockData);
            log.info("Seeded stock data.");
        } else {
            log.info("Skipping seed data (Stocks already present).");
        }
    }
}
