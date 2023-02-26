package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String mobile;

    private String password;

    @OneToOne
    @JoinColumn
    private Cab cab;


    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
    private List<TripBooking> tripBookingList = new ArrayList<>();

//    @ManyToOne
//    @JoinColumn
//    private Admin admin;




    //Constructor

    public Driver() {
    }

    public Driver(int id, String mobile, String password) {
        this.id = id;
        this.mobile = mobile;
        this.password = password;
    }



    //Getters and Setters


    public List<TripBooking> getTripBookingList() {
        return tripBookingList;
    }

    public void setTripBookingList(List<TripBooking> tripBookingList) {
        this.tripBookingList = tripBookingList;
    }

    public int getDriverId() {
        return id;
    }

    public void setDriverId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cab getCab() {
        return cab;
    }

    public void setCab(Cab cab) {
        this.cab = cab;
    }
}