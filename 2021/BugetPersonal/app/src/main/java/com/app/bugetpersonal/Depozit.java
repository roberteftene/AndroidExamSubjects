package com.app.bugetpersonal;

import android.os.Parcel;
import android.os.Parcelable;

public class Depozit implements Parcelable {

    private String numeDepozit;
    private String valuta;
    private boolean isEconomie;
    private String oraCreare;
    private double sumaIntiala;

    public Depozit(String numeDepozit, String valuta, boolean isEconomie, String oraCreare, double sumaIntiala) {
        this.numeDepozit = numeDepozit;
        this.valuta = valuta;
        this.isEconomie = isEconomie;
        this.oraCreare = oraCreare;
        this.sumaIntiala = sumaIntiala;

    }


    protected Depozit(Parcel in) {
        numeDepozit = in.readString();
        valuta = in.readString();
        isEconomie = in.readByte() != 0;
        oraCreare = in.readString();
        sumaIntiala = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(numeDepozit);
        dest.writeString(valuta);
        dest.writeByte((byte) (isEconomie ? 1 : 0));
        dest.writeString(oraCreare);
        dest.writeDouble(sumaIntiala);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Depozit> CREATOR = new Creator<Depozit>() {
        @Override
        public Depozit createFromParcel(Parcel in) {
            return new Depozit(in);
        }

        @Override
        public Depozit[] newArray(int size) {
            return new Depozit[size];
        }
    };

    public String getNumeDepozit() {
        return numeDepozit;
    }

    public void setNumeDepozit(String numeDepozit) {
        this.numeDepozit = numeDepozit;
    }

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public boolean isEconomie() {
        return isEconomie;
    }

    public void setEconomie(boolean economie) {
        isEconomie = economie;
    }

    public String getOraCreare() {
        return oraCreare;
    }

    public void setOraCreare(String oraCreare) {
        this.oraCreare = oraCreare;
    }

    public double getSumaIntiala() {
        return sumaIntiala;
    }

    public void setSumaIntiala(double sumaIntiala) {
        this.sumaIntiala = sumaIntiala;
    }

    @Override
    public String toString() {
        return "Depozit{" +
                "numeDepozit='" + numeDepozit + '\'' +
                ", valuta='" + valuta + '\'' +
                ", isEconomie=" + isEconomie +
                ", oraCreare='" + oraCreare + '\'' +
                ", sumaIntiala=" + sumaIntiala +
                '}';
    }
}
