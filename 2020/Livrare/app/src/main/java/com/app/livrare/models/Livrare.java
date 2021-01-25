package com.app.livrare.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "livrare")
public class Livrare implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String destinatar;
    private String adresa;
    private double valoare;
    private String tip;
    @Ignore
    private Date data;


    public Livrare() {
    }

    public Livrare(String destinatar, String adresa, double valoare, String tip) {
        this.destinatar = destinatar;
        this.adresa = adresa;
        this.valoare = valoare;
        this.tip = tip;
    }

    public Livrare(String destinatar, String adresa, Date data, double valoare, String tip) {
        this.destinatar = destinatar;
        this.adresa = adresa;
        this.data = data;
        this.valoare = valoare;
        this.tip = tip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    protected Livrare(Parcel in) {
        destinatar = in.readString();
        adresa = in.readString();
        valoare = in.readDouble();
        tip = in.readString();
        if(in.readByte() == 0){
            data = null;
        }else{
            try {
                data = new SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(in.readString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static final Creator<Livrare> CREATOR = new Creator<Livrare>() {
        @Override
        public Livrare createFromParcel(Parcel in) {
            return new Livrare(in);
        }

        @Override
        public Livrare[] newArray(int size) {
            return new Livrare[size];
        }
    };

    public String getDestinatar() {
        return destinatar;
    }

    public void setDestinatar(String destinatar) {
        this.destinatar = destinatar;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getValoare() {
        return valoare;
    }

    public void setValoare(double valoare) {
        this.valoare = valoare;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }


    @Override
    public String toString() {
        return "Livrare{" +
                "destinatar='" + destinatar + '\'' +
                ", adresa='" + adresa + '\'' +
                ", data=" + data +
                ", valoare=" + valoare +
                ", tip='" + tip + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(destinatar);
        dest.writeString(adresa);
        dest.writeDouble(valoare);
        dest.writeString(tip);
        if(data == null){
            dest.writeByte((byte) 0);
        }else{
            dest.writeByte((byte) 1);
            dest.writeString(new SimpleDateFormat("dd-MM-yyyy",Locale.US).format(data));
        }
    }
}
