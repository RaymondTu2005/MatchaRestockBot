package com.example.MatchaRestock.Restock;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;

@Configuration
public class RestockConfigTester {
  @Bean
  public CommandLineRunner testingData(RestockRepository restockRepository) {
    return args -> {
      Restock restock = new Restock();
      restock.setRestockDateAndTime(LocalDateTime.of(2025,7,17,16,30));
      restock.setGramLimit(0); // 0 indicates no gram limit
      restock.addMatcha(new Matcha("Ippodo Tea", "Ummon-No-Mukashi", 40, "Usucha & Koicha", "Can"));
      restock.addMatcha(new Matcha("Ippodo Tea", "Sayaka-no-mukashi", 100, "Usucha / " +
          "Koicha", "Bag"));
      restockRepository.save(restock);
    };
  }
}
