package com.app.datapackageapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "package")
public class DataPackage implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int packageId;
    private String packageType;
    private double latitude;
    private double longitude;
    @Ignore
    private Date timestamp;

    public DataPackage(String packageType, double latitude, double longitude) {
        this.packageType = packageType;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public DataPackage(String packageType, double latitude, double longitude, Date timestamp) {
        this.packageType = packageType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public DataPackage(int packageId, String packageType, double latitude, double longitude, Date timestamp) {
        this.packageId = packageId;
        this.packageType = packageType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    protected DataPackage(Parcel in) {
        packageId = in.readInt();
        packageType = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        if(in.readByte() == 0){
            timestamp = null;
        }else{
            try {
                timestamp = new SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.US).parse(in.readString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static final Creator<DataPackage> CREATOR = new Creator<DataPackage>() {
        @Override
        public DataPackage createFromParcel(Parcel in) {
            return new DataPackage(in);
        }

        @Override
        public DataPackage[] newArray(int size) {
            return new DataPackage[size];
        }
    };

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "DataPackage{" +
                "packageId=" + packageId +
                ", packageType='" + packageType + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(packageId);
        dest.writeString(packageType);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        if(timestamp == null){
            dest.writeByte((byte) 0);
        }else{
            dest.writeByte((byte) 1);
            dest.writeString(new SimpleDateFormat("dd-MM-yyyy hh:mm",Locale.US).format(timestamp));
        }

    }
}
