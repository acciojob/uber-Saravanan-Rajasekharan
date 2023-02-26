package com.driver.services.impl;

import com.driver.model.*;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Customer customer = customerRepository2.findById(customerId).get();
		customerRepository2.delete(customer);

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver> driverList = driverRepository2.findAll();
		Collections.sort(driverList,DriId);

		for(Driver driver : driverList){
			if(driver.getCab().isAvailable()){
				TripBooking tripBooking = new TripBooking();
				tripBooking.setFromLocation(fromLocation);
				tripBooking.setToLocation(toLocation);
				tripBooking.setDistanceInKm(distanceInKm);
				tripBooking.setDriver(driver);
				tripBooking.setBill(driver.getCab().getPerKmRate()*distanceInKm);

				Customer customer = customerRepository2.findById(customerId).get();
				tripBooking.setCustomer(customer);
				tripBooking.setStatus(TripStatus.CONFIRMED);
				driver.getCab().setAvailable(false);

				customer.getTripBookingList().add(tripBooking);
				customerRepository2.save(customer);

				driver.getTripBookingList().add(tripBooking);
				driverRepository2.save(driver);



				return tripBooking;
			}
		}

		throw new Exception("No cab available!");
	}

	public static Comparator<Driver> DriId = new Comparator<Driver>() {
		public int compare(Driver d1, Driver d2){
			return d1.getId()-d2.getId();
		}
	};


	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();

		tripBooking.setStatus(TripStatus.CANCELED);
		tripBooking.setBill(0);
		tripBooking.getDriver().getCab().setAvailable(true);

		tripBookingRepository2.save(tripBooking);
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();

		tripBooking.setStatus(TripStatus.COMPLETED);


		Driver driver = tripBooking.getDriver();
		Cab cab = driver.getCab();

		tripBooking.setBill(tripBooking.getDistanceInKm()* cab.getPerKmRate());
		tripBooking.getDriver().getCab().setAvailable(true);


		tripBookingRepository2.save(tripBooking);

	}
}
