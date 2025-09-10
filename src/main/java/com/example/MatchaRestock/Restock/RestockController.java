package com.example.MatchaRestock.Restock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// This is the controller that connects to the website. It allows API requests such as Get, Put,
// Insert, Delete, etc.
@RestController
@RequestMapping("/requests")
public class RestockController {
  private final RestockService restockService;

  @Autowired
  public RestockController(RestockService restockService) {
    this.restockService = restockService;
  }

  @GetMapping("/ippodo")
  public void scrapeIppodo() {
    restockService.scrapeAndDetectIppodo();
  }
  @GetMapping("/marukyu")
  public void scrapeMarukyu() {
    restockService.scrapeAndDetectMarukyu();
  }
  @GetMapping("/sazen")
  public void scrapeSazen() {
    restockService.scrapeAndDetectSazen();
  }
}
