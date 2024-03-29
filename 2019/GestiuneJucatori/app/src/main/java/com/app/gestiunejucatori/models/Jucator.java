package com.app.gestiunejucatori.models;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Entity(tableName = "jucator")
public class Jucator implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int numar;
    private String nume;
    private String dataNasterii;
    private String pozitie;

    public Jucator(int id, int numar, String nume, String dataNasterii, String pozitie) {
        this.id = id;
        this.numar = numar;
        this.nume = nume;
        this.dataNasterii = dataNasterii;
        this.pozitie = pozitie;
    }

    @Ignore
    public Jucator(int numar, String nume, String dataNasterii, String pozitie) {
        this.numar = numar;
        this.nume = nume;
        this.dataNasterii = dataNasterii;
        this.pozitie = pozitie;
    }

    protected Jucator(Parcel in) {
        numar = in.readInt();
        nume = in.readString();
        dataNasterii = in.readString();
        pozitie = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static final Creator<Jucator> CREATOR = new Creator<Jucator>() {
        @Override
        public Jucator createFromParcel(Parcel in) {
            return new Jucator(in);
        }

        @Override
        public Jucator[] newArray(int size) {
            return new Jucator[size];
        }
    };

    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(String dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

    public String getPozitie() {
        return pozitie;
    }

    public void setPozitie(String pozitie) {
        this.pozitie = pozitie;
    }

    @Override
    public String toString() {
        return "Jucator{" +
                "numar=" + numar +
                ", nume='" + nume + '\'' +
                ", dataNasterii='" + dataNasterii + '\'' +
                ", pozitie='" + pozitie + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(numar);
        dest.writeString(nume);
        dest.writeString(dataNasterii);
        dest.writeString(pozitie);
    }
}

