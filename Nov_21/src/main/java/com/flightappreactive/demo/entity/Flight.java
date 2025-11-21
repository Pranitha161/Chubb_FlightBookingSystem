package com.flightappreactive.demo.entity;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import jakarta.validation.constraints.Min;
import lombok.Data;


@Table(name="flights")
@Data

public class Flight {
	@Id
	private int id;
	private String fromPlace;
	private String toPlace;
	private LocalDateTime arrivalTime;
	private LocalDateTime departureTime;
	@Min(value=1,message="Availabe seats must be 0 or more")
	private int availableSeats;
	private Integer priceOneWay;
	private Integer priceRoundTrip;
//	@ManyToOne
//	@JsonBackReference
//	private Airline airline;
	private Integer airlineId;
//	@OneToMany(mappedBy = "flight",cascade = CascadeType.ALL)
//	@JsonManagedReference
//	private List<Booking> bookings=new ArrayList<>();
//	@OneToMany(mappedBy="flight",cascade=CascadeType.ALL)
//	@JsonManagedReference
//	private List<Seat> seats=new ArrayList<>();
//	public void initializeSeats(int rows, int cols) { 
//		char[] seatLetters = {'A','B','C','D','E','F'}; 
//		for (int row=1; row<=rows; row++) 
//		{for(int col=0;col<cols;col++) 
//		{ 
//			Seat seat=new Seat(); 
//			seat.setSeatNumber(row+""+seatLetters[col]); 
//		seat.setAvailable(true); 
//		seat.setFlight(this); 
//		seats.add(seat); 
//		} }
//		this.availableSeats = seats.size(); 
//		}
}
