package com.driver.model;


import org.hibernate.annotations.ValueGenerationType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="admin")
public class Admin{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int adminId;

    private String username;

    private String password;

//    @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL)
//    private List<Customer> customerList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL)
//    private List<Driver> driverList = new ArrayList<>();

    public Admin() {

    }

    public Admin(int adminId, String username, String password) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
    }

//    public List<Customer> getCustomerList() {
//        return customerList;
//    }
//
//    public void setCustomerList(List<Customer> customerList) {
//        this.customerList = customerList;
//    }
//
//    public List<Driver> getDriverList() {
//        return driverList;
//    }
//
//    public void setDriverList(List<Driver> driverList) {
//        this.driverList = driverList;
//    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}