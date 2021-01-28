package com.app.carmanagement;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName ="car")
public class Car implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int carNo;
    private String registrationDate;
    private int idParkingPosition;
    private boolean  isPayed;

    @Ignore
    public Car(int carNo, String registrationDate, int idParkingPosition, boolean isPayed) {
        this.carNo = carNo;
        this.registrationDate = registrationDate;
        this.idParkingPosition = idParkingPosition;
        this.isPayed = isPayed;
    }

    public Car(int id, int carNo, String registrationDate, int idParkingPosition, boolean isPayed) {
        this.id = id;
        this.carNo = carNo;
        this.registrationDate = registrationDate;
        this.idParkingPosition = idParkingPosition;
        this.isPayed = isPayed;
    }

    protected Car(Parcel in) {
        id = in.readInt();
        carNo = in.readInt();
        registrationDate = in.readString();
        idParkingPosition = in.readInt();
        isPayed = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(carNo);
        dest.writeString(registrationDate);
        dest.writeInt(idParkingPosition);
        dest.writeByte((byte) (isPayed ? 1 : 0));
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarNo() {
        return carNo;
    }

    public void setCarNo(int carNo) {
        this.carNo = carNo;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getIdParkingPosition() {
        return idParkingPosition;
    }

    public void setIdParkingPosition(int idParkingPosition) {
        this.idParkingPosition = idParkingPosition;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carNo=" + carNo +
                ", registrationDate='" + registrationDate + '\'' +
                ", idParkingPosition=" + idParkingPosition +
                ", isPayed=" + isPayed +
                '}';
    }
}
