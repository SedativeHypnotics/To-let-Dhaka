package com.nsu.to_letdhaka.Domain;

import java.io.Serializable;
import java.util.Objects;

public class Ad implements Serializable {
    private String user;
    private String month;
    private String rent;
    private String date;
    private String category;
    private String contactNo;
    private String address;
    private String roomFor;
    private String number;
    private String image;

    public Ad() {
    }

    public Ad(String user, String month, String rent, String date, String category, String contactNo, String address, String roomFor, String number, String image) {
        this.user = user;
        this.month = month;
        this.rent = rent;
        this.date = date;
        this.category = category;
        this.contactNo = contactNo;
        this.address = address;
        this.roomFor = roomFor;
        this.number = number;
        this.image = image;
    }
    
    public String getImage(){
    	return image;
    }
    
    public void setImage(String image){
    	this.image = image;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoomFor() {
        return roomFor;
    }

    public void setRoomFor(String roomFor) {
        this.roomFor = roomFor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ad)) return false;
        Ad ad = (Ad) o;
        return Objects.equals(getUser(), ad.getUser()) &&
                Objects.equals(getMonth(), ad.getMonth()) &&
                Objects.equals(getRent(), ad.getRent()) &&
                Objects.equals(getDate(), ad.getDate()) &&
                Objects.equals(getCategory(), ad.getCategory()) &&
                Objects.equals(getContactNo(), ad.getContactNo()) &&
                Objects.equals(getAddress(), ad.getAddress()) &&
                Objects.equals(getRoomFor(), ad.getRoomFor()) &&
                Objects.equals(getNumber(), ad.getNumber()) &&
                Objects.equals(getImage(), ad.getImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getMonth(), getRent(), getDate(), getCategory(), getContactNo(), getAddress(), getRoomFor(), getNumber(), getImage());
    }

    @Override
    public String toString() {
        return "Ad{" +
                "user='" + user + '\'' +
                ", month='" + month + '\'' +
                ", rent='" + rent + '\'' +
                ", date='" + date + '\'' +
                ", category='" + category + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", address='" + address + '\'' +
                ", roomFor='" + roomFor + '\'' +
                ", number='" + number + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
