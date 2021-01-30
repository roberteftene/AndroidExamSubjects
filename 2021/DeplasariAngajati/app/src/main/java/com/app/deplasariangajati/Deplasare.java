package com.app.deplasariangajati;

import android.os.Parcel;
import android.os.Parcelable;

public class Deplasare implements Parcelable {

    private String numeAngajat;
    private String oraPlecare;
    private String motiv;
    private int durataDeplasare;
    private String mobilDeplasare;

    public Deplasare(String numeAngajat, String oraPlecare, String motiv, int durataDeplasare, String mobilDeplasare) {
        this.numeAngajat = numeAngajat;
        this.oraPlecare = oraPlecare;
        this.motiv = motiv;
        this.durataDeplasare = durataDeplasare;
        this.mobilDeplasare = mobilDeplasare;
    }

    protected Deplasare(Parcel in) {
        numeAngajat = in.readString();
        oraPlecare = in.readString();
        motiv = in.readString();
        durataDeplasare = in.readInt();
        mobilDeplasare = in.readString();
    }

    public static final Creator<Deplasare> CREATOR = new Creator<Deplasare>() {
        @Override
        public Deplasare createFromParcel(Parcel in) {
            return new Deplasare(in);
        }

        @Override
        public Deplasare[] newArray(int size) {
            return new Deplasare[size];
        }
    };

    public String getNumeAngajat() {
        return numeAngajat;
    }

    public void setNumeAngajat(String numeAngajat) {
        this.numeAngajat = numeAngajat;
    }

    public String getOraPlecare() {
        return oraPlecare;
    }

    public void setOraPlecare(String oraPlecare) {
        this.oraPlecare = oraPlecare;
    }

    public String getMotiv() {
        return motiv;
    }

    public void setMotiv(String motiv) {
        this.motiv = motiv;
    }

    public int getDurataDeplasare() {
        return durataDeplasare;
    }

    public void setDurataDeplasare(int durataDeplasare) {
        this.durataDeplasare = durataDeplasare;
    }

    public String getMobilDeplasare() {
        return mobilDeplasare;
    }

    public void setMobilDeplasare(String mobilDeplasare) {
        this.mobilDeplasare = mobilDeplasare;
    }

    @Override
    public String toString() {
        return "Deplasare{" +
                "numeAngajat='" + numeAngajat + '\'' +
                ", oraPlecare='" + oraPlecare + '\'' +
                ", motiv='" + motiv + '\'' +
                ", durataDeplasare=" + durataDeplasare +
                ", mobilDeplasare='" + mobilDeplasare + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(numeAngajat);
        dest.writeString(oraPlecare);
        dest.writeString(motiv);
        dest.writeInt(durataDeplasare);
        dest.writeString(mobilDeplasare);
    }
}


