package com.app.agentieimobiliara;

import android.os.Parcel;
import android.os.Parcelable;

public class Imobil implements Parcelable {

    private String adresa;
    private int numar;
    private String categorie;
    private boolean esteDisponibil;
    private String oraAdaugare;


    public Imobil(String adresa, int numar, String categorie, boolean esteDisponibil, String oraAdaugare) {
        this.adresa = adresa;
        this.numar = numar;
        this.categorie = categorie;
        this.esteDisponibil = esteDisponibil;
        this.oraAdaugare = oraAdaugare;
    }


    protected Imobil(Parcel in) {
        adresa = in.readString();
        numar = in.readInt();
        categorie = in.readString();
        esteDisponibil = in.readByte() != 0;
        oraAdaugare = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(adresa);
        dest.writeInt(numar);
        dest.writeString(categorie);
        dest.writeByte((byte) (esteDisponibil ? 1 : 0));
        dest.writeString(oraAdaugare);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Imobil> CREATOR = new Creator<Imobil>() {
        @Override
        public Imobil createFromParcel(Parcel in) {
            return new Imobil(in);
        }

        @Override
        public Imobil[] newArray(int size) {
            return new Imobil[size];
        }
    };

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public boolean isEsteDisponibil() {
        return esteDisponibil;
    }

    public void setEsteDisponibil(boolean esteDisponibil) {
        this.esteDisponibil = esteDisponibil;
    }

    public String getOraAdaugare() {
        return oraAdaugare;
    }

    public void setOraAdaugare(String oraAdaugare) {
        this.oraAdaugare = oraAdaugare;
    }

    @Override
    public String toString() {
        return "Imobil{" +
                "adresa='" + adresa + '\'' +
                ", numar=" + numar +
                ", categorie='" + categorie + '\'' +
                ", esteDisponibil=" + esteDisponibil +
                ", oraAdaugare='" + oraAdaugare + '\'' +
                '}';
    }
}
