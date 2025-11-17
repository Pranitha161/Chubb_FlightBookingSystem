package com.flightapp.entity;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name="flights")
public class Flight {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String fromPlace;
	private String toPlace;
	private LocalDateTime arrivalTime;
	private LocalDateTime departureTime;
	@Min(value=1,message="Availabe seats must be 0 or more")
	private int availableSeats;
	private Price price;
	@ManyToOne
	@JsonBackReference
	private Airline airline;
	@OneToMany(mappedBy = "flight",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Booking> bookings=new ArrayList<>();
	@OneToMany(mappedBy="flight",cascade=CascadeType.ALL)
	@JsonManagedReference
	private List<Seat> seats=new ArrayList<>();
	public String getFromPlace() {
		return fromPlace;
	}
	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}
	public String getToPlace() {
		return toPlace;
	}
	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}
	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public LocalDateTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}
	public Airline getAirline() {
		return airline;
	}
	public void setAirline(Airline airline) {
		this.airline = airline;
	}
	public List<Booking> getBookings() {
		return bookings;
	}
	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
	public Price getPrice() {
		return price;
	}
	public void setPrice(Price price) {
		this.price = price;
	}
	public List<Seat> getSeats() {
		return seats;
	}
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
	public void initializeSeats(int rows, int cols) { 
		char[] seatLetters = {'A','B','C','D','E','F'}; 
		for (int row=1; row<=rows; row++) 
		{for(int col=0;col<cols;col++) 
		{ 
			Seat seat=new Seat(); 
			seat.setSeatNumber(row+""+seatLetters[col]); 
		seat.setAvailable(true); 
		seat.setFlight(this); 
		seats.add(seat); 
		} }
		this.availableSeats = seats.size(); 
		}
	
	
	
	
	
}
