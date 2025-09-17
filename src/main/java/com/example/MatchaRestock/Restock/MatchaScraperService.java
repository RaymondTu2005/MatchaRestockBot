package com.example.MatchaRestock.Restock;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MatchaScraperService {

  // Ippodo uses shopify for their website. Thus, we are going to use the Shopify API to extract
  // product details and turn them into java classes. To do this, we have to use a JSON to java
  // class parser, which in this case we are going to use GSON Created by Google.
  public List<Matcha> scrapeIppodoWebPage(String url) {
    try {
      Document pageData = Jsoup.connect(url).get();
      Element stockStatus = pageData.selectFirst(".product-stock-status");
      if (stockStatus != null && stockStatus.text().contains("Add to bag")) {
        // Return the relevant matcha data by calling the JSON Scrapper!
        // Note that some Matcha's are unavailable. Since there are so few, we can manually check
        // which ones are no longer available forever (Such as Seasonal Matcha's, etc)
        List<Matcha> returnVal = ippodoJSONConverter();
        return returnVal;
      } else if (stockStatus != null && stockStatus.text().contains("Sold out")) {
        return Collections.emptyList();
      } else {
        return Collections.emptyList(); // Null indicates that the status is UNKNOWN. Either stockStatus
        // is null (could mean that the CSS query has changed)
      }
    } catch (IOException e) {
      e.printStackTrace(); // Show error, usually occurs from error while fetching URL.
      return Collections.emptyList();
    }
  }

  // This is a helper method to convert JSON to Product, which can be turned into a matcha object!
  private List<Matcha> ippodoJSONConverter() {
    String websiteLink = "https://ippodotea.com/collections/matcha/products.json";
    try {
      URL websiteLinkURL = new URL(websiteLink);
      HttpURLConnection connection = (HttpURLConnection) websiteLinkURL.openConnection();
      connection.setRequestMethod("GET"); // Default method is GET, just making sure
      // First, get the response code and check if it is valid.
      // Used: https://www.digitalocean.com/community/tutorials/java-httpurlconnection-example-java-http-request-get-post
      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        // Get the data from the website!
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder(); // Can cause error possibly, maybe use
        // StringBuffer?
        while ((inputLine = in.readLine())!= null) {
          response.append(inputLine);
        }
        // After our StringBuilder should contain all the JSON Data in the StringBuilder
        // Object. Then we should close the connection to prevent error, and then use GSON to
        // parse it
        in.close();
        // Now, we parse with Gson to convert it to a ProductResponse. Then the method should
        // return that, and the callee method should convert it to matcha objects!
        Gson jsonParser = new Gson();
        ProductResponse ippodoJSON = jsonParser.fromJson(response.toString(), ProductResponse.class);
        List<ProductResponse.Product> productList = ippodoJSON.getProducts();
        // Now go through the list of products and find if they are available and create them
        // into matcha objects.
        List<Matcha> matchaList = new ArrayList<>(); // Could be null if there is no object in stock
        for (ProductResponse.Product product : productList) {
          // Check availibility first. If false, then skip
          for (ProductResponse.Variant variant : product.getVariants()) {
            // Check if any variant has availibility
            if (Boolean.TRUE.equals(variant.getAvailable())) {
              // Create the corresponding object, making sure to safely handle null stuff.
              Matcha currentVariant = new Matcha();
              // All of these could be null.
              currentVariant.setBrandName(product.getVendor());
              currentVariant.setMatchaName(product.getTitle());
              currentVariant.setGramWeight(variant.getGrams()); // Weight includes the container
              // as well
              currentVariant.setContainer("Unknown");
              currentVariant.setType("Unknown");
              matchaList.add(currentVariant);
            }
          }
        }
        return matchaList;
      }
    } catch (IOException e) {
      // Can either be MalformedURLException, IOexception, or Protocol Exception
      e.printStackTrace();
    }
    return Collections.emptyList();
    // Null indicates error
  }

  // TODO: Finish this later!
  public List<Matcha> scrapeMarukyuKoyamaen(String url) {
    try {
      Document pageData = Jsoup.connect(url).get();
      Element stockStatus = pageData.selectFirst(".stock single-stock-status out-of-stock");
      if (stockStatus == null || stockStatus.text().isEmpty() || stockStatus.text()
          .contains("This product is currently out of stock and unavailable.")) {
        return null;
      }
      Element productName = pageData.selectFirst("[itemprop=name");
      return null; // Change

    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public List<Matcha> scrapeSazenTea(String url) {
    String baseLink = "https://www.sazentea.com";
    try {
      Document pageData = Jsoup.connect(url).get();
      Element stockStatus = pageData.selectFirst(".product-name");
      if (stockStatus != null && !stockStatus.text().isEmpty()) {
        List<Matcha> matchaList = new ArrayList<>();
        // Call the MatchaExtractor on the href links in product-list
        Elements productLinks = pageData.select("#product-list .product");
        Restock restock = new Restock(LocalDateTime.now(), 0, matchaList);
        // Sazen does not have gram limits


        for (Element productLink : productLinks) {
          Element link = productLink.selectFirst(".product-name a");
          if (link != null) {
            String href = link.attr("href");
            String passUrl = baseLink + href;
            Matcha modifyRestock = sazenMatchaExtrator(passUrl);
            modifyRestock.setRestock(restock); // Map each matcha to a specific restock
            matchaList.add(modifyRestock);
          }
        }
        return matchaList;
      }
      return null; // Stock Status is unknown // No products listed
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private Matcha sazenMatchaExtrator(String url) {
    try {
      Document pageData = Jsoup.connect(url).get();
      Element productName = pageData.selectFirst("div.product-container h1[itemprop=name]");
      if (productName == null || productName.text().isEmpty()) {
        return null;
      }
      // Create new matcha objects
      String brandName = sazenProductInfo(pageData, "Maker:");
      String matchaName = productName.text();
      String weight = sazenProductInfo(pageData, "Net weight:");
      String matchaType = "Ceremonial"; // We are only doing ceremonial grade rn.
      Element matchaContain = pageData.selectFirst("span.unit-price");
      String matchaContainer = "";
      if (matchaContain != null) {
        String text = matchaContain.text();
        String[] splitText = text.split("/");
        if (splitText.length > 1) {
          matchaContainer = splitText[1].trim().toUpperCase(); // Actual text is $ / BRANDED CAN
        }
      }
      if (brandName == null || weight == null)
        return null;
      Matcha extractedMatcha =
          new Matcha(brandName, matchaName, Integer.parseInt(weight.replaceAll("\\D", "")),
              matchaType, matchaContainer);
      return extractedMatcha;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private String sazenProductInfo(Document doc, String label) {
    Elements productInfoList = doc.select("#product-info p");
    for (Element productInfo : productInfoList) {
      if (productInfo.text().startsWith(label)) {
        return productInfo.text().replace(label, "").trim();
      }
    }
    return null;
  }
}
