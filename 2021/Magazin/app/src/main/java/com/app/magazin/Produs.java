package com.app.magazin;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "produs")
public class Produs implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String numeProdus;
    private boolean isOferta;
    private String categorie;
    private int ratingProdus;
    private double pret;

    public Produs() {
    }

    public Produs(int id, String numeProdus, boolean isOferta, String categorie, int ratingProdus, double pret) {
        this.id = id;
        this.numeProdus = numeProdus;
        this.isOferta = isOferta;
        this.categorie = categorie;
        this.ratingProdus = ratingProdus;
        this.pret = pret;
    }


    @Ignore
    public Produs(String numeProdus, boolean isOferta, String categorie, int ratingProdus, double pret) {
        this.numeProdus = numeProdus;
        this.isOferta = isOferta;
        this.categorie = categorie;
        this.ratingProdus = ratingProdus;
        this.pret = pret;
    }

    protected Produs(Parcel in) {
        numeProdus = in.readString();
        isOferta = in.readByte() != 0;
        categorie = in.readString();
        ratingProdus = in.readInt();
        pret = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(numeProdus);
        dest.writeByte((byte) (isOferta ? 1 : 0));
        dest.writeString(categorie);
        dest.writeInt(ratingProdus);
        dest.writeDouble(pret);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Produs> CREATOR = new Creator<Produs>() {
        @Override
        public Produs createFromParcel(Parcel in) {
            return new Produs(in);
        }

        @Override
        public Produs[] newArray(int size) {
            return new Produs[size];
        }
    };

    public String getNumeProdus() {
        return numeProdus;
    }

    public void setNumeProdus(String numeProdus) {
        this.numeProdus = numeProdus;
    }

    public boolean isOferta() {
        return isOferta;
    }

    public void setOferta(boolean oferta) {
        isOferta = oferta;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getRatingProdus() {
        return ratingProdus;
    }

    public void setRatingProdus(int ratingProdus) {
        this.ratingProdus = ratingProdus;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "numeProdus='" + numeProdus + '\'' +
                ", isOferta=" + isOferta +
                ", categorie='" + categorie + '\'' +
                ", ratingProdus=" + ratingProdus +
                ", pret=" + pret +
                '}';
    }
}
