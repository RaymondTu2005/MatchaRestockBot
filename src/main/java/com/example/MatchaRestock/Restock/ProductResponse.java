package com.example.MatchaRestock.Restock;

import java.util.List;

public class ProductResponse {
  private List<Product> products;


  public class Product {
    private String title; // The title
    private String vendor;
    private String product_type;
    private List<Variant> variants;

    public String getTitle() {
      return title;
    }

    public String getVendor() {
      return vendor;
    }

    public String getProduct_type() {
      return product_type;
    }

    public List<Variant> getVariants() {
      return variants;
    }
  }


  public class Variant {
    private Boolean available; // Either true or false.
    private String price;
    int grams; // This weight includes the container as well

    public Boolean getAvailable() {
      return available;
    }

    public String getPrice() {
      return price;
    }

    public int getGrams() {
      return grams;
    }
  }

  public List<Product> getProducts() {
    return products;
  }
}
