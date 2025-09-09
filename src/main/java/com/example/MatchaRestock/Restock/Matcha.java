package com.example.MatchaRestock.Restock;


import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.*;

// This is just for Matcha. Can expand later to include chasens, hojicha, etc.
@Entity
public class Matcha {
  @Id
  @SequenceGenerator(
      sequenceName = "MATCHA_ID",
      name = "MATCHA_ID",
      allocationSize = 1
  )
  @GeneratedValue(
      generator = "MATCHA_ID",
      strategy = SEQUENCE
  )
  private Long ID;
  @Column(name = "BRAND_NAME", nullable = false, updatable = true)
  private String brandName;
  @Column(name = "MATCHA_NAME", nullable = false, updatable = true)
  private String matchaName; // Things like Isuzu, Ummon, etc.
  @Column(name = "GRAM_WEIGHT", nullable = false, updatable = true)
  private int gramWeight;
  @Column(name = "MATCHA_TYPE", nullable = false, updatable = true)
  private String type; // Non-null. Should be All-Around, Koicha, Usucha, or Cullinary.
  @Column(name = "CONTAINER", nullable = false, updatable = true)
  private String container; // Non-Null, can, bag, etc.
  @Column(name = "PRICE", nullable = false, updatable = true)
  private Integer price;

  @ManyToOne
  @JoinColumn(name = "RESTOCK_ID")
  private Restock restock; // One matcha can only map to ONE restock (Many to One)

  public Matcha(String brandName, String matchaName, int gramWeight, String type,
      String container) {
    this.brandName = brandName;
    this.matchaName = matchaName;
    this.gramWeight = gramWeight;
    this.type = type;
    this.container = container;
  }

  public Matcha() {
  }

  public Restock getRestock() {
    return restock;
  }

  public String getBrandName() {
    return brandName;
  }

  public String getMatchaName() {
    return matchaName;
  }

  public int getGramWeight() {
    return gramWeight;
  }

  public String getType() {
    return type;
  }

  public String getContainer() {
    return container;
  }
  public void setRestock(Restock restock) {
    this.restock = restock;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public void setGramWeight(int gramWeight) {
    this.gramWeight = gramWeight;
  }

  public void setMatchaName(String matchaName) {
    this.matchaName = matchaName;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setContainer(String container) {
    this.container = container;
  }

  @Override
  public String toString() {
    return "Matcha{" + "brandName='" + brandName + '\'' + ", matchaName='" + matchaName + '\'' + ", gramWeight=" + gramWeight + ", type='" + type + '\'' + ", container='" + container + '\'' + '}';
  }
}

