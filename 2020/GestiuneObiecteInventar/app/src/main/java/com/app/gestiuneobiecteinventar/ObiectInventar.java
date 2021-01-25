package com.app.gestiuneobiecteinventar;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ObiectInventar implements Parcelable {
    private String denumire;
    private int numarInventar;
    private Date dataAdaugarii;
    private Double valoare;
    private String uzura;

    public ObiectInventar(String denumire, int numarInventar, Date dataAdaugarii, Double valoare, String uzura) {
        this.denumire = denumire;
        this.numarInventar = numarInventar;
        this.dataAdaugarii = dataAdaugarii;
        this.valoare = valoare;
        this.uzura = uzura;
    }

    protected ObiectInventar(Parcel in) {
        denumire = in.readString();
        numarInventar = in.readInt();
        if(in.readByte() == 0){
            dataAdaugarii = null;
        }else{
            try {
                dataAdaugarii = new SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(in.readString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (in.readByte() == 0) {
            valoare = null;
        } else {
            valoare = in.readDouble();
        }
        uzura = in.readString();
    }

    public static final Creator<ObiectInventar> CREATOR = new Creator<ObiectInventar>() {
        @Override
        public ObiectInventar createFromParcel(Parcel in) {
            return new ObiectInventar(in);
        }

        @Override
        public ObiectInventar[] newArray(int size) {
            return new ObiectInventar[size];
        }
    };

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getNumarInventar() {
        return numarInventar;
    }

    public void setNumarInventar(int numarInventar) {
        this.numarInventar = numarInventar;
    }

    public Date getDataAdaugarii() {
        return dataAdaugarii;
    }

    public void setDataAdaugarii(Date dataAdaugarii) {
        this.dataAdaugarii = dataAdaugarii;
    }

    public Double getValoare() {
        return valoare;
    }

    public void setValoare(Double valoare) {
        this.valoare = valoare;
    }

    public String getUzura() {
        return uzura;
    }

    public void setUzura(String uzura) {
        this.uzura = uzura;
    }

    @Override
    public String toString() {
        return "ObiectInventar{" +
                "denumire='" + denumire + '\'' +
                ", numarInventar='" + numarInventar + '\'' +
                ", dataAdaugarii=" + dataAdaugarii +
                ", valoare=" + valoare +
                ", uzura='" + uzura + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(denumire);
        dest.writeInt(numarInventar);
        if(dataAdaugarii == null){
            dest.writeByte((byte) 0);
        }else{
            dest.writeByte((byte) 1);
            dest.writeString(new SimpleDateFormat("dd-MM-yyyy",Locale.US).format(dataAdaugarii));
        }
        if (valoare == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(valoare);
        }
        dest.writeString(uzura);
    }
}
