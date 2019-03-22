package com.apkstudios.krushisangha.models;

public class CommunityCards {

    String product;
    String current_price;
    String min;
    String max;
    String quantity;

    public CommunityCards(String product, String current_price, String min, String max, String quantity) {
        this.product = product;
        this.current_price = current_price;
        this.min = min;
        this.max = max;
        this.quantity = quantity;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(String current_price) {
        this.current_price = current_price;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }






}
