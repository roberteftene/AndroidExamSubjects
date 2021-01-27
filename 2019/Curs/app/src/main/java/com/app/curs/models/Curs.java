package com.app.curs.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "curs")
public class Curs implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int idCurs;
    private String denumire;
    private int nrParticipanti;
    private String sala;
    private String profesorTitular;

    public Curs(int idCurs, String denumire, int nrParticipanti, String sala, String profesorTitular) {
        this.idCurs = idCurs;
        this.denumire = denumire;
        this.nrParticipanti = nrParticipanti;
        this.sala = sala;
        this.profesorTitular = profesorTitular;
    }


    @Ignore
    public Curs(String denumire, int nrParticipanti, String sala, String profesorTitular) {
        this.denumire = denumire;
        this.nrParticipanti = nrParticipanti;
        this.sala = sala;
        this.profesorTitular = profesorTitular;
    }

    protected Curs(Parcel in) {
        idCurs = in.readInt();
        denumire = in.readString();
        nrParticipanti = in.readInt();
        sala = in.readString();
        profesorTitular = in.readString();
    }

    public static final Creator<Curs> CREATOR = new Creator<Curs>() {
        @Override
        public Curs createFromParcel(Parcel in) {
            return new Curs(in);
        }

        @Override
        public Curs[] newArray(int size) {
            return new Curs[size];
        }
    };

    public int getIdCurs() {
        return idCurs;
    }

    public void setIdCurs(int idCurs) {
        this.idCurs = idCurs;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getNrParticipanti() {
        return nrParticipanti;
    }

    public void setNrParticipanti(int nrParticipanti) {
        this.nrParticipanti = nrParticipanti;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getProfesorTitular() {
        return profesorTitular;
    }

    public void setProfesorTitular(String profesorTitular) {
        this.profesorTitular = profesorTitular;
    }

    @Override
    public String toString() {
        return "Curs{" +
                "idCurs=" + idCurs +
                ", denumire='" + denumire + '\'' +
                ", nrParticipanti=" + nrParticipanti +
                ", sala='" + sala + '\'' +
                ", profesorTitular='" + profesorTitular + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idCurs);
        dest.writeString(denumire);
        dest.writeInt(nrParticipanti);
        dest.writeString(sala);
        dest.writeString(profesorTitular);
    }
}
