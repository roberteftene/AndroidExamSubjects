package com.app.gestiuneexamene.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "examen")
public class Examen implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String denumireMaterie;
    private int nrStudenti;
    private String sala;
    private String supraveghetor;

    public Examen(int id, String denumireMaterie, int nrStudenti, String sala, String supraveghetor) {
        this.id = id;
        this.denumireMaterie = denumireMaterie;
        this.nrStudenti = nrStudenti;
        this.sala = sala;
        this.supraveghetor = supraveghetor;
    }

    @Ignore
    public Examen(String denumireMaterie, int nrStudenti, String sala, String supraveghetor) {
        this.denumireMaterie = denumireMaterie;
        this.nrStudenti = nrStudenti;
        this.sala = sala;
        this.supraveghetor = supraveghetor;
    }

    protected Examen(Parcel in) {
        id = in.readInt();
        denumireMaterie = in.readString();
        nrStudenti = in.readInt();
        sala = in.readString();
        supraveghetor = in.readString();
    }

    public static final Creator<Examen> CREATOR = new Creator<Examen>() {
        @Override
        public Examen createFromParcel(Parcel in) {
            return new Examen(in);
        }

        @Override
        public Examen[] newArray(int size) {
            return new Examen[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumireMaterie() {
        return denumireMaterie;
    }

    public void setDenumireMaterie(String denumireMaterie) {
        this.denumireMaterie = denumireMaterie;
    }

    public int getNrStudenti() {
        return nrStudenti;
    }

    public void setNrStudenti(int nrStudenti) {
        this.nrStudenti = nrStudenti;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getSupraveghetor() {
        return supraveghetor;
    }

    public void setSupraveghetor(String supraveghetor) {
        this.supraveghetor = supraveghetor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(denumireMaterie);
        dest.writeInt(nrStudenti);
        dest.writeString(sala);
        dest.writeString(supraveghetor);
    }

    @Override
    public String toString() {
        return "Examen{" +
                "id=" + id +
                ", denumireMaterie='" + denumireMaterie + '\'' +
                ", nrStudenti=" + nrStudenti +
                ", sala='" + sala + '\'' +
                ", supraveghetor='" + supraveghetor + '\'' +
                '}';
    }
}
