package com.app.gestiunebonuri;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "bon")
public class Bon implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int numar;
    private String serviciu;
    private String data;
    private String ghiseu;

    public Bon(int id, int numar, String serviciu, String data, String ghiseu) {
        this.id = id;
        this.numar = numar;
        this.serviciu = serviciu;
        this.data = data;
        this.ghiseu = ghiseu;
    }

    @Ignore
    public Bon(int numar, String serviciu, String data, String ghiseu) {
        this.numar = numar;
        this.serviciu = serviciu;
        this.data = data;
        this.ghiseu = ghiseu;
    }

    protected Bon(Parcel in) {
        numar = in.readInt();
        serviciu = in.readString();
        data = in.readString();
        ghiseu = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(numar);
        dest.writeString(serviciu);
        dest.writeString(data);
        dest.writeString(ghiseu);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Bon> CREATOR = new Creator<Bon>() {
        @Override
        public Bon createFromParcel(Parcel in) {
            return new Bon(in);
        }

        @Override
        public Bon[] newArray(int size) {
            return new Bon[size];
        }
    };

    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    public String getServiciu() {
        return serviciu;
    }

    public void setServiciu(String serviciu) {
        this.serviciu = serviciu;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getGhiseu() {
        return ghiseu;
    }

    public void setGhiseu(String ghiseu) {
        this.ghiseu = ghiseu;
    }

    @Override
    public String toString() {
        return "Bon{" +
                "numar=" + numar +
                ", serviciu='" + serviciu + '\'' +
                ", data='" + data + '\'' +
                ", ghiseu='" + ghiseu + '\'' +
                '}';
    }
}
