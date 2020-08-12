package com.example.bikroy.Users;

public class Products {


    public Products(String address, String image, String price, String product, String productKey,String userID,String userName) {
        this.address = address;
        this.image = image;
        this.price = price;
        this.product = product;
        this.productKey = productKey;
        this.userID = userID;
        this.userName = userName;
    }

    private String address;
    private String image;
    private String price;
    private String product;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    private String userID;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    private String productKey;


   public Products() {

   }







}
