package com.app.gestiuneexcursii;

import android.os.Parcel;
import android.os.Parcelable;

public class Excursie implements Parcelable {

    private String locatie;
    private String dataPlecare;
    private boolean visited;
    private int prioritate;
    private double cheltuiala;
    private boolean isDelete;

    public Excursie(String locatie, String dataPlecare, boolean visited, int prioritate, double cheltuiala) {
        this.locatie = locatie;
        this.dataPlecare = dataPlecare;
        this.visited = visited;
        this.prioritate = prioritate;
        this.cheltuiala = cheltuiala;
    }

    protected Excursie(Parcel in) {
        locatie = in.readString();
        dataPlecare = in.readString();
        visited = in.readByte() != 0;
        prioritate = in.readInt();
        cheltuiala = in.readDouble();
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(locatie);
        dest.writeString(dataPlecare);
        dest.writeByte((byte) (visited ? 1 : 0));
        dest.writeInt(prioritate);
        dest.writeDouble(cheltuiala);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Excursie> CREATOR = new Creator<Excursie>() {
        @Override
        public Excursie createFromParcel(Parcel in) {
            return new Excursie(in);
        }

        @Override
        public Excursie[] newArray(int size) {
            return new Excursie[size];
        }
    };

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getDataPlecare() {
        return dataPlecare;
    }

    public void setDataPlecare(String dataPlecare) {
        this.dataPlecare = dataPlecare;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getPrioritate() {
        return prioritate;
    }

    public void setPrioritate(int prioritate) {
        this.prioritate = prioritate;
    }

    public double getCheltuiala() {
        return cheltuiala;
    }

    public void setCheltuiala(double cheltuiala) {
        this.cheltuiala = cheltuiala;
    }

    @Override
    public String toString() {
        return "Excursie{" +
                "locatie='" + locatie + '\'' +
                ", dataPlecare='" + dataPlecare + '\'' +
                ", visited=" + visited +
                ", prioritate=" + prioritate +
                ", cheltuiala=" + cheltuiala +
                '}';
    }
}
