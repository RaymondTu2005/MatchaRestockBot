package com.example.MatchaRestock.Restock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

// This essentially communicates with the controller (GET, PUT, DELETE, INSERT) and gets the
// data from the Data layer.
@Service
public class RestockService {
  private final RestockRepository restockRepository;
  public final MatchaScraperService matchaScraperService;
  @Autowired
  public RestockService(RestockRepository restockRepository, MatchaScraperService ScraperService) {
    this.restockRepository = restockRepository;
    this.matchaScraperService = ScraperService;
  }
  @GetMapping
  public void scrapeAndDetectIppodo() {
    List<Matcha> ippodoData = matchaScraperService.scrapeIppodoWebPage("https://ippodotea" +
        ".com/collections/matcha/products.json");
    detectRestock(ippodoData);
  }
  public void scrapeAndDetectMarukyu() {
    List<Matcha> marukyuData = matchaScraperService.scrapeMarukyuKoyamaen("https://www" +
        ".marukyu-koyamaen" +
        ".co.jp/english/shop/products/catalog/matcha/principal");
    detectRestock(marukyuData);
  }
  public void scrapeAndDetectSazen() {
    List<Matcha> sazenData = matchaScraperService.scrapeSazenTea("https://www.sazentea.com/en/products/c21-matcha");
    detectRestock(sazenData);
  }
  public void detectRestock(List<Matcha> matchaList) {
    // TODO: Finish This! (Should Utilize Restock Repository to check if it is already contained)
    if (matchaList == null || matchaList.isEmpty()) { return; }
    // List not empty, so compare it to the current
    for (Matcha checkingData : List<Matcha> matchaList) {
      if (restockRepository.existsBy)
    }
  }


}
