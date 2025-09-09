package com.example.MatchaRestock.Restock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

// This essentially communicates with the controller (GET, PUT, DELETE, INSERT) and gets the
// data from the Data layer.
@Service
public class RestockService {
  private final RestockRepository restockRepository;
  @Autowired
  public RestockService(RestockRepository restockRepository) {
    this.restockRepository = restockRepository;
  }
  @GetMapping
  public Restock getRestock() {
    return null;
  }
}
