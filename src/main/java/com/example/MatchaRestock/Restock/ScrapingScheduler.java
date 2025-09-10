package com.example.MatchaRestock.Restock;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// Refer to: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/annotation/Scheduled.html?utm_source=chatgpt.com#fixedRate()

@Component
public class ScrapingScheduler {
  private final RestockService restockService;
  @Autowired // No longer needed in Java Spring 4.3 since Spring will automatically use a
  // constructor for dependency injections.
  public ScrapingScheduler(RestockService restockService) {
    this.restockService = restockService;
  }

  @Scheduled (fixedRateString = "${ippodo.scraper.time}") // It's in Ms, so convert to seconds - minutes
  public void getIppodoData() {
    restockService.scrapeAndDetectIppodo();
  }
  @Scheduled (fixedRateString = "${marukyu.scraper.time}")
  // Not currently in use since they do not ship to the U.S.A
  public void getMarukyuData() {
    restockService.scrapeAndDetectMarukyu();
  }
  @Scheduled (fixedRateString = "${sazen.scraper.time}")
  public void getSazenData() {
    restockService.scrapeAndDetectSazen();
  }

}
