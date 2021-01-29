package com.app.casadelicitatii;

import android.os.Parcel;
import android.os.Parcelable;

public class Licitatie implements Parcelable {
    
    private String descriere;
    private double valoare;
    private String categorie;
    private String data;
    private String metodaPlata;

    public Licitatie(String descriere, double valoare, String categorie, String data, String metodaPlata) {
        this.descriere = descriere;
        this.valoare = valoare;
        this.categorie = categorie;
        this.data = data;
        this.metodaPlata = metodaPlata;
    }

    protected Licitatie(Parcel in) {
        descriere = in.readString();
        valoare = in.readDouble();
        categorie = in.readString();
        data = in.readString();
        metodaPlata = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(descriere);
        dest.writeDouble(valoare);
        dest.writeString(categorie);
        dest.writeString(data);
        dest.writeString(metodaPlata);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Licitatie> CREATOR = new Creator<Licitatie>() {
        @Override
        public Licitatie createFromParcel(Parcel in) {
            return new Licitatie(in);
        }

        @Override
        public Licitatie[] newArray(int size) {
            return new Licitatie[size];
        }
    };

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public double getValoare() {
        return valoare;
    }

    public void setValoare(double valoare) {
        this.valoare = valoare;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMetodaPlata() {
        return metodaPlata;
    }

    public void setMetodaPlata(String metodaPlata) {
        this.metodaPlata = metodaPlata;
    }

    @Override
    public String toString() {
        return "Licitatie{" +
                "descriere='" + descriere + '\'' +
                ", valoare=" + valoare +
                ", categorie='" + categorie + '\'' +
                ", data='" + data + '\'' +
                ", metodaPlata='" + metodaPlata + '\'' +
                '}';
    }
}
