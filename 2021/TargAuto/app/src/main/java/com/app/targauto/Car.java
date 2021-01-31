package com.app.targauto;

import android.os.Parcel;
import android.os.Parcelable;

public class Car implements Parcelable {

    private String marca;
    private String numar;
    private int rating;
    private int stareMasina;
    private String categorieMasina;
    private double pret;
    private boolean pretNegociabil;
    private String valutaAcceptata;
    private String dataAdaugare;
    private String oraAdaugare;

    public Car(String marca, String numar, int rating, int stareMasina, String categorieMasina, double pret, boolean pretNegociabil, String valutaAcceptata, String dataAdaugare, String oraAdaugare) {
        this.marca = marca;
        this.numar = numar;
        this.rating = rating;
        this.stareMasina = stareMasina;
        this.categorieMasina = categorieMasina;
        this.pret = pret;
        this.pretNegociabil = pretNegociabil;
        this.valutaAcceptata = valutaAcceptata;
        this.dataAdaugare = dataAdaugare;
        this.oraAdaugare = oraAdaugare;
    }

    protected Car(Parcel in) {
        marca = in.readString();
        numar = in.readString();
        rating = in.readInt();
        stareMasina = in.readInt();
        categorieMasina = in.readString();
        pret = in.readDouble();
        pretNegociabil = in.readByte() != 0;
        valutaAcceptata = in.readString();
        dataAdaugare = in.readString();
        oraAdaugare = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(marca);
        dest.writeString(numar);
        dest.writeInt(rating);
        dest.writeInt(stareMasina);
        dest.writeString(categorieMasina);
        dest.writeDouble(pret);
        dest.writeByte((byte) (pretNegociabil ? 1 : 0));
        dest.writeString(valutaAcceptata);
        dest.writeString(dataAdaugare);
        dest.writeString(oraAdaugare);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNumar() {
        return numar;
    }

    public void setNumar(String numar) {
        this.numar = numar;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getStareMasina() {
        return stareMasina;
    }

    public void setStareMasina(int stareMasina) {
        this.stareMasina = stareMasina;
    }

    public String getCategorieMasina() {
        return categorieMasina;
    }

    public void setCategorieMasina(String categorieMasina) {
        this.categorieMasina = categorieMasina;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public boolean isPretNegociabil() {
        return pretNegociabil;
    }

    public void setPretNegociabil(boolean pretNegociabil) {
        this.pretNegociabil = pretNegociabil;
    }

    public String getValutaAcceptata() {
        return valutaAcceptata;
    }

    public void setValutaAcceptata(String valutaAcceptata) {
        this.valutaAcceptata = valutaAcceptata;
    }

    public String getDataAdaugare() {
        return dataAdaugare;
    }

    public void setDataAdaugare(String dataAdaugare) {
        this.dataAdaugare = dataAdaugare;
    }

    public String getOraAdaugare() {
        return oraAdaugare;
    }

    public void setOraAdaugare(String oraAdaugare) {
        this.oraAdaugare = oraAdaugare;
    }

    @Override
    public String toString() {
        return "Car{" +
                "marca='" + marca + '\'' +
                ", numar='" + numar + '\'' +
                ", rating=" + rating +
                ", stareMasina=" + stareMasina +
                ", categorieMasina='" + categorieMasina + '\'' +
                ", pret=" + pret +
                ", pretNegociabil=" + pretNegociabil +
                ", valutaAcceptata='" + valutaAcceptata + '\'' +
                ", dataAdaugare='" + dataAdaugare + '\'' +
                ", oraAdaugare='" + oraAdaugare + '\'' +
                '}';
    }
}
