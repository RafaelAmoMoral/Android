package com.example.clothes.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Clothe implements Parcelable {

    protected Integer id;
    protected String name;
    protected Integer price;
    protected String size;
    protected String description;
    protected String purchaseDate;
    protected boolean isFavorite;
    protected String state;
    protected String image;

    public Clothe(Integer id, String name, Integer price, String size, String description, String purchaseDate,
                  boolean isFavorite, String state, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.size = size;
        this.description = description;
        this.purchaseDate = purchaseDate;
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

    public Clothe() {
    }

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
        purchaseDate = in.readString();
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
        dest.writeString(purchaseDate);
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

    public boolean setName(String name) {
        boolean valid = name != null ? !name.isEmpty() : false;
        if (valid) {
            this.name = name;
        }
        return valid;
    }

    public Integer getPrice() {
        return price;
    }

    public boolean setPrice(Integer price) {
        boolean valid = false;
        if(price!=null) {
            valid = price != -1;// -1 es el número que seteo si el campo esta vacío.

            if (valid) {
                try {
                    valid = !(price < 0);
                } catch (NumberFormatException nfe) {
                }
            }

            if (valid) {
                this.price = price;
            }
        }
        return valid;
    }

    public String getSize() {
        return size;
    }

    public boolean setSize(String size) {
        boolean valid = false;
        if(size!=null) {
            String[] tallas = {"S", "XS", "M", "XM", "XL"};

            for (int i = 0; i < tallas.length && !valid; i++) {
                if (tallas[i].equals(size.toUpperCase())) {
                    valid = true;
                }
            }

            if (valid) {
                this.size = size;
            }
        }
        return valid;
    }

    public String getDescription() {
        return description;
    }

    public boolean setDescription(String description) {
        boolean valid = description != null ? !description.isEmpty() : false;
        if (valid) {
            this.description = description;
        }
        return valid;
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

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public boolean setPurchaseDate(String purchaseDate) {
        boolean valid=false;
        if (purchaseDate != null) {
            valid = purchaseDate.matches("^([0-2][0-9]|3[0-1])(\\/|-)(0[1-9]|1[0-2])\\2(\\d{4})$");
            if (valid) {
                this.purchaseDate = purchaseDate;
            }
        }
        return valid;
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
