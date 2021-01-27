package com.app.servicecardapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "service")
public class ServiceCard implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int serviceNumber;
    private String serviceDepartment;
    private double serviceCost;
    @Ignore
    private Date serviceDate;

    public ServiceCard(int serviceNumber, String serviceDepartment, double serviceCost) {
        this.serviceNumber = serviceNumber;
        this.serviceDepartment = serviceDepartment;
        this.serviceCost = serviceCost;
    }

    public ServiceCard(int serviceNumber, String serviceDepartment, double serviceCost, Date serviceDate) {
        this.serviceNumber = serviceNumber;
        this.serviceDepartment = serviceDepartment;
        this.serviceCost = serviceCost;
        this.serviceDate = serviceDate;
    }

    protected ServiceCard(Parcel in) {
        serviceNumber = in.readInt();
        serviceDepartment = in.readString();
        serviceCost = in.readDouble();
        if(in.readByte() == 0){
            serviceDate = null;
        }else{
            try {
                serviceDate = new SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.US).parse(in.readString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static final Creator<ServiceCard> CREATOR = new Creator<ServiceCard>() {
        @Override
        public ServiceCard createFromParcel(Parcel in) {
            return new ServiceCard(in);
        }

        @Override
        public ServiceCard[] newArray(int size) {
            return new ServiceCard[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(int serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getServiceDepartment() {
        return serviceDepartment;
    }

    public void setServiceDepartment(String serviceDepartment) {
        this.serviceDepartment = serviceDepartment;
    }

    public double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(double serviceCost) {
        this.serviceCost = serviceCost;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    @Override
    public String toString() {
        return "ServiceCard{" +
                "serviceNumber=" + serviceNumber +
                ", serviceDepartment='" + serviceDepartment + '\'' +
                ", serviceCost=" + serviceCost +
                ", serviceDate=" + serviceDate +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(serviceNumber);
        dest.writeString(serviceDepartment);
        dest.writeDouble(serviceCost);
        if(serviceDate == null){
            dest.writeByte((byte) 0);
        }else{
            dest.writeByte((byte) 1);
            dest.writeString(new SimpleDateFormat("dd-MM-yyyy hh:mm",Locale.US).format(serviceDate));
        }
    }
}
