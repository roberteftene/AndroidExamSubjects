package com.app.colectionaritimbre;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "timbru")
public class Timbru implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String forma;
    private int gradDegradare;
    private String data;
    private double valoare;
    private boolean cuAdeziv;


    public Timbru() {
    }

    public Timbru(int id, String forma, int gradDegradare, String data, double valoare, boolean cuAdeziv) {
        this.id = id;
        this.forma = forma;
        this.gradDegradare = gradDegradare;
        this.data = data;
        this.valoare = valoare;
        this.cuAdeziv = cuAdeziv;
    }

    @Ignore
    public Timbru(String forma, int gradDegradare, String data, double valoare, boolean cuAdeziv) {
        this.forma = forma;
        this.gradDegradare = gradDegradare;
        this.data = data;
        this.valoare = valoare;
        this.cuAdeziv = cuAdeziv;
    }


    protected Timbru(Parcel in) {
        id = in.readInt();
        forma = in.readString();
        gradDegradare = in.readInt();
        data = in.readString();
        valoare = in.readDouble();
        cuAdeziv = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(forma);
        dest.writeInt(gradDegradare);
        dest.writeString(data);
        dest.writeDouble(valoare);
        dest.writeByte((byte) (cuAdeziv ? 1 : 0));
    }

    public static final Creator<Timbru> CREATOR = new Creator<Timbru>() {
        @Override
        public Timbru createFromParcel(Parcel in) {
            return new Timbru(in);
        }

        @Override
        public Timbru[] newArray(int size) {
            return new Timbru[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public int getGradDegradare() {
        return gradDegradare;
    }

    public void setGradDegradare(int gradDegradare) {
        this.gradDegradare = gradDegradare;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public double getValoare() {
        return valoare;
    }

    public void setValoare(double valoare) {
        this.valoare = valoare;
    }

    public boolean isCuAdeziv() {
        return cuAdeziv;
    }

    @Override
    public String toString() {
        return "Timbru{" +
                "id=" + id +
                ", forma='" + forma + '\'' +
                ", gradDegradare=" + gradDegradare +
                ", data='" + data + '\'' +
                ", valoare=" + valoare +
                ", cuAdeziv=" + cuAdeziv +
                '}';
    }

    public void setCuAdeziv(boolean cuAdeziv) {
        this.cuAdeziv = cuAdeziv;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
