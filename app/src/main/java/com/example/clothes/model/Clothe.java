package com.example.clothes.model;

import java.time.LocalDate;

public class Clothe {

    private Long id;
    private String name;
    private Integer price;
    private String size;
    private String Description;
    private LocalDate purchaseDate;
    private boolean isFavorite;
    private String state;

    public Clothe(Long id, String name, Integer price, String size, String description, LocalDate purchaseDate,
                  boolean isFavorite, String state) {
        this.id=id;
        this.name = name;
        this.price = price;
        this.size = size;
        Description = description;
        this.purchaseDate = purchaseDate;
        this.isFavorite = isFavorite;
        this.state = state;
    }

    public Clothe(String name, String size, String description, LocalDate purchaseDate) {
        this.name = name;
        this.size = size;
        Description = description;
        this.purchaseDate = purchaseDate;
    }

    public Clothe() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Clothe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", size='" + size + '\'' +
                ", Description='" + Description + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", isFavorite=" + isFavorite +
                ", state='" + state + '\'' +
                '}';
    }
}
