package com.example.MatchaRestock.Restock;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;
@Entity
public class Restock {
  @Id
  @SequenceGenerator(
      sequenceName = "ID_GENERATOR",
      name = "ID_GENERATOR",
      allocationSize = 1
  )
  @GeneratedValue(
    generator = "ID_GENERATOR",
      strategy = SEQUENCE
  )
  @Column(name = "ID")
  private Long ID;
  @Column(name = "RESTOCK_DATE_AND_TIME", nullable = false, updatable = false)
  private LocalDateTime restockDateAndTime;
  @Column(name = "GRAM_LIMIT", nullable = false, updatable = false)
  private int gramLimit;  // If 0, then no limit!
  // Learn OneToMany Relationships, OneToOne, etc before modifying and annotating.
  @OneToMany(mappedBy = "restock", cascade = CascadeType.ALL) // The cascade means saving
  // a restocking saves the Matcha's in the list as well
  private List<Matcha> matchaList;

  public Restock(LocalDateTime restockDateAndTime, int gramLimit,
      List<Matcha> matchaList) {
    this.restockDateAndTime = restockDateAndTime;
    this.gramLimit = gramLimit;
    this.matchaList = matchaList;
  }

  public Restock() {
  }

  public Long getID() {
    return ID;
  }

  public LocalDateTime getRestockDateAndTime() {
    return restockDateAndTime;
  }

  public int getGramLimit() {
    return gramLimit;
  }

  public List<Matcha> getMatchaList() {
    return matchaList;
  }

  public void setID(Long ID) {
    this.ID = ID;
  }

  public void setRestockDateAndTime(LocalDateTime restockDateAndTime) {
    this.restockDateAndTime = restockDateAndTime;
  }

  public void setGramLimit(int gramLimit) {
    this.gramLimit = gramLimit;
  }

  public void setMatchaList(List<Matcha> matchaList) {
    this.matchaList = matchaList;
  }
  public void addMatcha(Matcha matcha) {
    if (matchaList == null) {
      matchaList = new ArrayList<Matcha>();
    }
    matchaList.add(matcha); // Add the matcha to the list!
    matcha.setRestock(this); // Set this restock to map to the matcha!
  }
}
