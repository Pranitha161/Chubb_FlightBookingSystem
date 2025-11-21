package com.flightappreactive.demo.entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="passengers")
public class Passenger {
	@Id
	private int id;
	@NotBlank
	private String name;
	private String gender;
	private int age;
	@Email
	private String email;
//	@ManyToOne
//	@JsonBackReference
//	private Booking booking;
	private Integer bookingId;
	
	
}
