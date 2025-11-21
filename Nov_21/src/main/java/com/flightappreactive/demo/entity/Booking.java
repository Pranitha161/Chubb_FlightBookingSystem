package com.flightappreactive.demo.entity;
import java.util.List;

import com.flightappreactive.demo.enums.MealPreference;
import com.flightappreactive.demo.enums.TripType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Transient;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="bookings")
public class Booking {
	@Id
	private Integer id;
	private String pnr;
	@Email
	private String email;
	private int seatCount;
	private TripType tripType;
	private MealPreference mealPreference;
	private Integer flightId;
	@Transient
    private List<String> seatNumbers;
//	@ManyToMany
//	@JsonBackReference
//	private Flight flight;
//	@OneToMany(cascade = CascadeType.ALL)
//	@JsonManagedReference
//	private List<Passenger> passengers=new ArrayList<>();
//	@ElementCollection
//	@CollectionTable(
//	    name = "booking_seat_numbers",
//	    joinColumns = @JoinColumn(name = "booking_id")
//	)
//	@Column(name = "seat_number")
//	private List<String> seatNumbers;
	private int totalAmount;	
}
