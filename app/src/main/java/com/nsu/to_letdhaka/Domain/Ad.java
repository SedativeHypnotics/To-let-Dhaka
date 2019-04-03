package com.nsu.to_letdhaka.Domain;

import java.util.Objects;

public class Ad {
    private String user;
    private String month;
    private String rent;
    private String category;
    private String contactNo;
    private String address;
    private String roomFor;

    public Ad() {
    }

    public Ad(String user, String month, String rent, String category, String contactNo, String address, String roomFor) {
        this.user = user;
        this.month = month;
        this.rent = rent;
        this.category = category;
        this.contactNo = contactNo;
        this.address = address;
        this.roomFor = roomFor;
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
                Objects.equals(getCategory(), ad.getCategory()) &&
                Objects.equals(getContactNo(), ad.getContactNo()) &&
                Objects.equals(getAddress(), ad.getAddress()) &&
                Objects.equals(getRoomFor(), ad.getRoomFor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getMonth(), getRent(), getCategory(), getContactNo(), getAddress(), getRoomFor());
    }

    @Override
    public String toString() {
        return "Ad{" +
                "user='" + user + '\'' +
                ", month='" + month + '\'' +
                ", rent='" + rent + '\'' +
                ", category='" + category + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", address='" + address + '\'' +
                ", roomFor='" + roomFor + '\'' +
                '}';
    }
}
