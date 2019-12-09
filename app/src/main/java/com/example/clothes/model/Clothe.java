package com.example.clothes.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Clothe implements Parcelable {

    private Integer id;
    private String name;
    private Integer price;
    private String size;
    private String description;
    private Calendar purchaseDate;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private String purchaseDateFormated;
    private boolean isFavorite;
    private String state;
    private String image;

    public Clothe(Integer id, String name, Integer price, String size, String description, Calendar purchaseDate,
                  boolean isFavorite, String state, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.size = size;
        this.description = description;
        this.purchaseDate = purchaseDate;
        this.purchaseDateFormated = dateFormat.format(purchaseDate.getTime());
        this.isFavorite = isFavorite;
        this.state = state;
        this.image = image;
    }

    public Clothe(Integer id, String name, Integer price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Clothe() {}

    protected Clothe(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readInt();
        }
        size = in.readString();
        description = in.readString();
        purchaseDateFormated = in.readString();
        isFavorite = in.readByte() != 0;
        state = in.readString();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(price);
        }
        dest.writeString(size);
        dest.writeString(description);
        dest.writeString(purchaseDateFormated);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeString(state);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Clothe> CREATOR = new Creator<Clothe>() {
        @Override
        public Clothe createFromParcel(Parcel in) {
            return new Clothe(in);
        }

        @Override
        public Clothe[] newArray(int size) {
            return new Clothe[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Calendar purchaseDate) {
        this.purchaseDate = purchaseDate;
        this.purchaseDateFormated = dateFormat.format(purchaseDate.getTime());
    }

    public void setPurchaseDate(String date){
        String[] fechArray = date.split("/");

        int dia = Integer.valueOf(fechArray[0]);
        int mes = Integer.valueOf(fechArray[1]) - 1;
        int anio = Integer.valueOf(fechArray[2]);

        purchaseDate = new GregorianCalendar(anio, mes, dia);
        purchaseDateFormated=date;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPurchaseDateFormated() {
        return purchaseDateFormated;
    }

    @Override
    public String toString() {
        return "Clothe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", size='" + size + '\'' +
                ", Description='" + this.description + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", isFavorite=" + isFavorite +
                ", state='" + state + '\'' +
                '}';
    }
}
