package com.example.helloworld;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MedOrganization implements Parcelable {
    private String name;
    private Items items[];
    private Cordinates cordinates;

    protected MedOrganization(Parcel in) {
        name = in.readString();
        items = in.createTypedArray(Items.CREATOR);
        cordinates = in.readParcelable(Cordinates.class.getClassLoader());


    }

    public MedOrganization(String name, Items[] items, Cordinates cordinates) {
        this.name = name;
        this.items = items;
        this.cordinates = cordinates;
    }

    public static final Creator<MedOrganization> CREATOR = new Creator<MedOrganization>() {
        @Override
        public MedOrganization createFromParcel(Parcel in) {
            return new MedOrganization(in);
        }

        @Override
        public MedOrganization[] newArray(int size) {
            return new MedOrganization[size];
        }
    };

    public String getName() {
        return name;
    }

    public Items[] getItems() {
        return items;
    }

    public Cordinates getCordinates() {
        return cordinates;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeTypedArray(items,i);

        parcel.writeParcelable(cordinates,i);

    }
}

class Cordinates implements Parcelable{
    private double latitude;
    private double longitude;

    public Cordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected Cordinates(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Cordinates> CREATOR = new Creator<Cordinates>() {
        @Override
        public Cordinates createFromParcel(Parcel in) {
            return new Cordinates(in);
        }

        @Override
        public Cordinates[] newArray(int size) {
            return new Cordinates[size];
        }
    };

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

class Items implements Parcelable{
    @SerializedName("itemName")
    private String name;
    @SerializedName("quantity")
    private int qty;
    @SerializedName("borderLineVal")
    private int borderLineQty;

    public Items(String name, int qty, int borderLineQty) {
        this.name = name;
        this.qty = qty;
        this.borderLineQty = borderLineQty;
    }

    protected Items(Parcel in) {
        name = in.readString();
        qty = in.readInt();
        borderLineQty = in.readInt();
    }

    public static final Creator<Items> CREATOR = new Creator<Items>() {
        @Override
        public Items createFromParcel(Parcel in) {
            return new Items(in);
        }

        @Override
        public Items[] newArray(int size) {
            return new Items[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getQty() {
        return qty;
    }

    public int getBorderLineQty() {
        return borderLineQty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(qty);
        parcel.writeInt(borderLineQty);
    }
}
