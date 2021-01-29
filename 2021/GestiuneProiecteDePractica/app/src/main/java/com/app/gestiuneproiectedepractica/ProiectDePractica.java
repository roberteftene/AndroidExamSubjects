package com.app.gestiuneproiectedepractica;

import android.os.Parcel;
import android.os.Parcelable;

public class ProiectDePractica implements Parcelable {

    private String titlu;
    private String profCoordonator;
    private String dataIncepere;
    private int gradDeCompletare;
    private boolean isAngajat;

    public ProiectDePractica(String titlu, String profCoordonator, String dataPredare, int nrSaptamani, boolean isAngajat) {
        this.titlu = titlu;
        this.profCoordonator = profCoordonator;
        this.dataIncepere = dataPredare;
        this.gradDeCompletare = nrSaptamani;
        this.isAngajat = isAngajat;
    }

    protected ProiectDePractica(Parcel in) {
        titlu = in.readString();
        profCoordonator = in.readString();
        dataIncepere = in.readString();
        gradDeCompletare = in.readInt();
        isAngajat = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titlu);
        dest.writeString(profCoordonator);
        dest.writeString(dataIncepere);
        dest.writeInt(gradDeCompletare);
        dest.writeByte((byte) (isAngajat ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProiectDePractica> CREATOR = new Creator<ProiectDePractica>() {
        @Override
        public ProiectDePractica createFromParcel(Parcel in) {
            return new ProiectDePractica(in);
        }

        @Override
        public ProiectDePractica[] newArray(int size) {
            return new ProiectDePractica[size];
        }
    };

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getProfCoordonator() {
        return profCoordonator;
    }

    public void setProfCoordonator(String profCoordonator) {
        this.profCoordonator = profCoordonator;
    }

    public String getDataPredare() {
        return dataIncepere;
    }

    public void setDataPredare(String dataPredare) {
        this.dataIncepere = dataPredare;
    }

    public int getNrSaptamani() {
        return gradDeCompletare;
    }

    public void setNrSaptamani(int nrSaptamani) {
        this.gradDeCompletare = nrSaptamani;
    }

    public Boolean getIsAngajat() {
        return isAngajat;
    }

    public void setIsAngajat(Boolean isAngajat) {
        this.isAngajat = isAngajat;
    }

    @Override
    public String toString() {
        return "ProiectDePractica{" +
                "titlu='" + titlu + '\'' +
                ", profCoordonator='" + profCoordonator + '\'' +
                ", dataPredare='" + dataIncepere + '\'' +
                ", nrSaptamani=" + gradDeCompletare +
                ", isAngajat='" + isAngajat + '\'' +
                '}';
    }
}
