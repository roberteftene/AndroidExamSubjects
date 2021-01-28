package com.app.gestiunerezervari;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "rezervare")
public class Rezervare implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int idRezervare;
    private String numeClient;
    private String tipCamera;
    private int durataSejur;
    private double sumaPlata;
    private String dataCazare;

    public Rezervare(int idRezervare, String numeClient, String tipCamera, int durataSejur, double sumaPlata, String dataCazare) {
        this.idRezervare = idRezervare;
        this.numeClient = numeClient;
        this.tipCamera = tipCamera;
        this.durataSejur = durataSejur;
        this.sumaPlata = sumaPlata;
        this.dataCazare = dataCazare;
    }

    @Ignore
    public Rezervare(String numeClient, String tipCamera, int durataSejur, double sumaPlata, String dataCazare) {
        this.numeClient = numeClient;
        this.tipCamera = tipCamera;
        this.durataSejur = durataSejur;
        this.sumaPlata = sumaPlata;
        this.dataCazare = dataCazare;
    }

    protected Rezervare(Parcel in) {
        idRezervare = in.readInt();
        numeClient = in.readString();
        tipCamera = in.readString();
        durataSejur = in.readInt();
        sumaPlata = in.readDouble();
        dataCazare = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idRezervare);
        dest.writeString(numeClient);
        dest.writeString(tipCamera);
        dest.writeInt(durataSejur);
        dest.writeDouble(sumaPlata);
        dest.writeString(dataCazare);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Rezervare> CREATOR = new Creator<Rezervare>() {
        @Override
        public Rezervare createFromParcel(Parcel in) {
            return new Rezervare(in);
        }

        @Override
        public Rezervare[] newArray(int size) {
            return new Rezervare[size];
        }
    };

    public int getIdRezervare() {
        return idRezervare;
    }

    public void setIdRezervare(int idRezervare) {
        this.idRezervare = idRezervare;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public String getTipCamera() {
        return tipCamera;
    }

    public void setTipCamera(String tipCamera) {
        this.tipCamera = tipCamera;
    }

    public int getDurataSejur() {
        return durataSejur;
    }

    public void setDurataSejur(int durataSejur) {
        this.durataSejur = durataSejur;
    }

    public double getSumaPlata() {
        return sumaPlata;
    }

    public void setSumaPlata(double sumaPlata) {
        this.sumaPlata = sumaPlata;
    }

    public String getDataCazare() {
        return dataCazare;
    }

    public void setDataCazare(String dataCazare) {
        this.dataCazare = dataCazare;
    }

    @Override
    public String toString() {
        return "Rezervare{" +
                "idRezervare=" + idRezervare +
                ", numeClient='" + numeClient + '\'' +
                ", tipCamera='" + tipCamera + '\'' +
                ", durataSejur=" + durataSejur +
                ", sumaPlata=" + sumaPlata +
                ", dataCazare='" + dataCazare + '\'' +
                '}';
    }
}
