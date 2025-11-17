package com.flightapp.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.flightapp.entity.enums.MealPreference;
import com.flightapp.entity.enums.TripType;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;


@Entity
@Table(name="bookings")
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String pnr;
	@Email
	private String email;
	private int seatCount;
	@Enumerated(EnumType.STRING)
	private TripType tripType;
	@Enumerated(EnumType.STRING)
	private MealPreference mealPreference;
	@ManyToOne
	@JsonBackReference
	private Flight flight;
	@OneToMany(cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Passenger> passengers=new ArrayList<>();
	@ElementCollection
	@CollectionTable(
	    name = "booking_seat_numbers",
	    joinColumns = @JoinColumn(name = "booking_id")
	)
	@Column(name = "seat_number")
	private List<String> seatNumbers;
	private int totalAmount;
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getSeatCount() {
		return seatCount;
	}
	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	public List<Passenger> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
	public int getId() {
		return id;
	}
	public TripType getTripType() {
		return tripType;
	}
	public MealPreference getMealPreference() {
		return mealPreference;
	}
	public void setMealPreference(MealPreference mealPreference) {
		this.mealPreference = mealPreference;
	}
	public void setTripType(TripType tripType) {
		this.tripType = tripType;
	}
	public int getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<String> getSeatNumbers() {
		return seatNumbers;
	}
	public void setSeatNumbers(List<String> seatNumbers) {
		this.seatNumbers = seatNumbers;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
}
