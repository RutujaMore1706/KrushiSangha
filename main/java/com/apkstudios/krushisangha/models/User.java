package com.apkstudios.krushisangha.models;

/**
 * Created by Admin on 23-12-2016.
 */
public class User {
    private String name;
    private String email;
    private String mobile_number;
    private String unique_id;
    private String password;
    private String old_password;
    private String new_password;
    private Double user_latitude;
    private Double user_longitude;
    private String taluka;
    private String wh_name;
    private Double lat;
    private Double lng;
    private String lic_num;
    private String newToken;
    private String title;
    private String message;
    private String type;
    private String created_at;
    private String body;
    private String manager_name;
    private String total_farmers;
    private String product;
    private String current_price;
    private String minn;
    private String maxx;
    private String quantity;
    private String organisation_name;
    private String quality;
    private String delivery;
    private String A1;
    private String A2;
    private String A3;
    private String cid;

    public String getWh_name() {
        return wh_name;
    }

    public void setWh_name(String wh_name) {
        this.wh_name = wh_name;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {

        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber(){
        return mobile_number;
    }

    public void setMobileNumber(String mobile_number){
        this.mobile_number = mobile_number;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id){
        this.unique_id = unique_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(Double user_latitude){
        this.user_latitude = user_latitude;
    }

    public void setLongitude(Double user_longitude){
        this.user_longitude = user_longitude;
    }

    public Double getLatitude(){
        return user_latitude;
    }

    public Double getLongitude(){
        return user_longitude;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getLic_num() {
        return lic_num;
    }

    public void setLic_num(String lic_num) {
        this.lic_num = lic_num;
    }

    public String getNewToken() {
        return newToken;
    }

    public void setNewToken(String newToken) {
        this.newToken = newToken;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public String getTotal_farmer() {
        return total_farmers;
    }

    public void setTotal_farmer(String total_farmers) {
        this.total_farmers = total_farmers;
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
        return minn;
    }

    public void setMin(String minn) {
        this.minn = minn;
    }

    public String getMax() {
        return maxx;
    }

    public void setMax(String max) {
        this.maxx = maxx;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOrganisation_name() {
        return organisation_name;
    }

    public void setOrganisation_name(String organisation_name) {
        this.organisation_name = organisation_name;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getA1() {
        return A1;
    }

    public void setA1(String a1) {
        A1 = a1;
    }

    public String getA2() {
        return A2;
    }

    public void setA2(String a2) {
        A2 = a2;
    }

    public String getA3() {
        return A3;
    }

    public void setA3(String a3) {
        A3 = a3;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
