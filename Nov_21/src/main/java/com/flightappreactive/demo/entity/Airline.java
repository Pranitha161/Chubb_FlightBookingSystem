package com.flightappreactive.demo.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table("airlines")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Airline {
	@Id
	private Integer id;
	@NotBlank(message="AirLine name cannot be blank")
	private String name;
	private String logoUrl;
//	@OneToMany(mappedBy = "airline",cascade = CascadeType.ALL )
//	@JsonManagedReference
//	private List<Flight> flights=new ArrayList<>();
}
