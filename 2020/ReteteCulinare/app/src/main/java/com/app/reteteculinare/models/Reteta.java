package com.app.reteteculinare.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "reteta")
public class Reteta implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String denumire;
    private String descriere;

    @Ignore
    private Date dataAdaugare;

    private double calorii;
    private String categorie;

    public Reteta(String denumire, String descriere, double calorii, String categorie) {
        this.denumire = denumire;
        this.descriere = descriere;
        this.calorii = calorii;
        this.categorie = categorie;
    }

    public Reteta(String denumire, String descriere, Date dataAdaugare, double calorii, String categorie) {
        this.denumire = denumire;
        this.descriere = descriere;
        this.dataAdaugare = dataAdaugare;
        this.calorii = calorii;
        this.categorie = categorie;
    }

    protected Reteta(Parcel in) {
        denumire = in.readString();
        descriere = in.readString();
        calorii = in.readDouble();
        categorie = in.readString();
        try {
            dataAdaugare = new SimpleDateFormat("dd-MM-yyyy").parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static final Creator<Reteta> CREATOR = new Creator<Reteta>() {
        @Override
        public Reteta createFromParcel(Parcel in) {
            return new Reteta(in);
        }

        @Override
        public Reteta[] newArray(int size) {
            return new Reteta[size];
        }
    };


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Date getDataAdaugare() {
        return dataAdaugare;
    }

    public void setDataAdaugare(Date dataAdaugare) {
        this.dataAdaugare = dataAdaugare;
    }

    public double getCalorii() {
        return calorii;
    }

    public void setCalorii(double calorii) {
        this.calorii = calorii;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(denumire);
        dest.writeString(descriere);
        dest.writeDouble(calorii);
        dest.writeString(categorie);
        if(dataAdaugare != null) {
            dest.writeString(new SimpleDateFormat("dd-MM-yyyy").format(dataAdaugare));
        } else {
            dest.writeString("20-12-2010");
        }
    }

    @Override
    public String toString() {
        return "Reteta{" +
                "denumire='" + denumire + '\'' +
                ", descriere='" + descriere + '\'' +
                ", dataAdaugare=" + dataAdaugare +
                ", calorii=" + calorii +
                ", categorie='" + categorie + '\'' +
                '}';
    }
}
