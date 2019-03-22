package com.apkstudios.krushisangha.models;

public class FutureDemandCards {

    String organisation_name, product, quality, quantity, delivery, created_at;

    public FutureDemandCards(String organisation_name, String product, String quality, String quantity, String delivery, String created_at) {
        this.organisation_name = organisation_name;
        this.product = product;
        this.quality = quality;
        this.quantity = quantity;
        this.delivery = delivery;
        this.created_at = created_at;
    }


    public String getOrganisation_name() {
        return organisation_name;
    }

    public void setOrganisation_name(String organisation_name) {
        this.organisation_name = organisation_name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }



}
