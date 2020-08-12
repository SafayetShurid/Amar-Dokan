package com.example.bikroy;

public class CartItem {

    private String productName;

    public CartItem(String productName, String productPrice, String imageUrl) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.imageUrl = imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String productPrice;
    private String imageUrl;
}
